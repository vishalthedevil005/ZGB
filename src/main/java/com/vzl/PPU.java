package com.vzl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.vzl.InterruptController.InterruptType;

public class PPU {
	private Bus bus;
	
	private InterruptController ic;
	private LCDController lcd;
	private int ticks = 0;
	enum Mode {
		H_BLANK, V_BLANK, OAM_SCAN, DRAW_PIXEL
	}
	
	private int[] VRAM = new int[8 * 1024];
	private int[] OAM = new int[4 * 40];
	
	private int LCDC = 0x91;
	private int STAT = 0x2;
	private int SCY = 0;
	private int SCX = 0;
	private int LY = 0;
	private int LYC = 0;
	private int DMA;
	private int BGP = 0xFC;
	private int OBP0 = 0xFF;
	private int OBP1 = 0xFF;
	private int WY = 0;
	private int WX = 0;
	
	public PPU(InterruptController ic, LCDController lcd) {
		this.ic = ic;
		this.lcd = lcd;
	}
	
	void connectBus(Bus b) {
		this.bus = b;
	}
	
	public int read(int addr) {			
		if(addr >= 0x8000 && addr <= 0x9FFF) {
			if(getMode() == Mode.DRAW_PIXEL) {
				return 0xFF;
			}
			return VRAM[addr - 0x8000];
		}
		if(addr >= 0xFE00 && addr <= 0xFE9F) {
			if((getMode() == Mode.OAM_SCAN) || (getMode() == Mode.DRAW_PIXEL)) {
				return 0xFF;				
			}			
			return OAM[addr - 0xFE00];
		}
		if(addr == 0xFF40) return LCDC;
		if(addr == 0xFF41) return STAT;
		if(addr == 0xFF42) return SCY;
		if(addr == 0xFF43) return SCX;
		if(addr == 0xFF44) return LY;
		if(addr == 0xFF45) return LYC;
		if(addr == 0xFF46) return DMA;
		if(addr == 0xFF47) return BGP;
		if(addr == 0xFF48) return OBP0;
		if(addr == 0xFF49) return OBP1;		
		if(addr == 0xFF4A) return WY;
		if(addr == 0xFF4B) return WX;
		return 00;
	}
	
	public void write(int addr,int data) {
		if(addr >= 0x8000 && addr <= 0x9FFF) {
			if(getMode() == Mode.DRAW_PIXEL) {
				return;
			}
			VRAM[addr - 0x8000] = data;
			return;
		}
		if(addr >= 0xFE00 && addr <= 0xFE9F) {
			if((getMode() == Mode.OAM_SCAN) || (getMode() == Mode.DRAW_PIXEL)) {
				return;				
			}
			OAM[addr - 0xFE00] = data;
			return;
		}
		if(addr == 0xFF40) {
			LCDC = data;
			return;
		}
		if(addr == 0xFF41) {
			STAT = data;
			return;
		}
		if(addr == 0xFF42) {
			SCY = data;
			return;
		}
		if(addr == 0xFF43) {
			SCX = data;
			return;
		}
		if(addr == 0xFF44) {
			LY = data;
			return;
		}
		if(addr == 0xFF45) {
			LYC = data;
			return;
		}
		if(addr == 0xFF46) {
			enableDMA = true;
			dmaAddr = (data << 4) & 0xFF00;
			DMA = data;
			return;
		}
		if(addr == 0xFF47) {
			BGP = data;
			return;
		}
		if(addr == 0xFF48) {
			OBP0 = data;
			return;
		}
		if(addr == 0xFF49) {
			OBP1 = data;
			return;
		}
		if(addr == 0xFF4A) {
			WY = data;
			return;
		}
		if(addr == 0xFF4B) {
			WX = data;
			return;
		}
	}
	
	private Mode getMode() {
		switch(STAT & 0b11) {
			case 0b00:
				return Mode.H_BLANK;
			case 0b01:
				return Mode.V_BLANK;
			case 0b10:
				return Mode.OAM_SCAN;
			case 0b11:
				return Mode.DRAW_PIXEL;
		}
		return null;
	}
	
	public void tick() {
		ticks++;
		switch(getMode()) {
			case H_BLANK:
				hBlank();
				break;
			case V_BLANK:
				vBlank();
				break;
			case OAM_SCAN:
				oamScan();
				break;
			case DRAW_PIXEL:
				drawPixel();
				break;
		}
		
		if(enableDMA) {
			OAMDMATransfer();
		}
		
		//Ticks per frame
		if(ticks == 70224) {
			ticks = 0;
			//lcd.updateDisplay(Arrays.copyOfRange(VRAM, 0x0000, 0x1800));
		}
	}
	
	int[] oamBuffer = new int[10];
	int index = 0;
	int xPos = 0, yPos = 0, h = 0, tileIndex = 0, attrs = 0;
	int offset = 0;
	public void oamScan() {
		if((ticks % 2) == 0) {
			offset = 0 + (4 * (((ticks - (456 * LY)) / 2) - 1));
			
			yPos = OAM[offset];
			xPos = OAM[offset + 1];
			tileIndex = OAM[offset + 2];
			attrs = OAM[offset + 3];
			h = (Utils.getBit(LCDC,2) == 0) ? 8 : 16;
			
			if((xPos > 0) && (LY + 16 >= yPos) && (LY + 16 < yPos + h)) {
				if(index < oamBuffer.length) {
					oamBuffer[index] = (ticks/2 - 1);
					index++;
				}
			}
		}
		
		if(((ticks - (456 * LY)) % 80) == 0) {
			STAT = (STAT & 0xFC) | 0b11; //Mode = DRAW_PIXEL (0b11)
		}
	}
	
	private int xCounter = 0;
	private int tileNumber = 0;
	private int windowLineCounter = 0;
	private int low = 0;
	private int high = 0;
	private int tileDataLow = 0, tileDataHigh = 0;
	private Queue<Integer> bgFifo = new LinkedList<Integer>();
	private enum Step {
		FETCH_TILE_NUM, FETCH_TILE_DATA_LOW, FETCH_TILE_DATA_HIGH, PUSH_TO_FIFO
	}
	private Step drawPixelStep = Step.FETCH_TILE_NUM;
	private int tileX=0, tileY=0;
	public void drawPixel() {
//		byte windowEnable = Utils.getBit(LCDC, 5);
//		byte mapArea = (windowEnable == 1) ? Utils.getBit(LCDC, 6) : Utils.getBit(LCDC, 3);
//		int dataAddr = (Utils.getBit(LCDC, 5) == 0) ? 0x8800 : 0x8000;
//		int mapAddr = (mapArea == 0) ? 0x9800 : 0x9C00;
//		
//		//Fetching tile number
//		if((drawPixelStep == Step.FETCH_TILE_NUM) && ((ticks % 2) == 0)) {
//			int addr = 0;
//			if(windowEnable == 1) {
//				addr = mapAddr + xCounter + 32 * (windowLineCounter / 8);
//			} else {
//				addr = mapAddr + (xCounter + ((SCX / 8) & 0x1F) & 0x3FF) + ((32 * (((LY + SCY) & 0xFF) / 8)) & 0x3FF);
//			}
//			tileNumber = VRAM[addr];
//			xCounter++;
//			if(xCounter > 8) {
//				xCounter=0;
//				drawPixelStep = Step.FETCH_TILE_DATA_LOW;
//			}
//		}
//		
//		//Fetch tiledata low
//		if((drawPixelStep == Step.FETCH_TILE_DATA_LOW) && ((ticks % 2) == 0)) {			
//			if(windowEnable == 1) {
//				low = tileNumber + (windowLineCounter % 8);
//			} else {
//				low = tileNumber + (2 * ((LY + SCY) % 8));
//			}			
//			tileDataLow = VRAM[dataAddr + low];
//			drawPixelStep = Step.FETCH_TILE_DATA_HIGH;
//		}
//		
//		//Fetch tiledata high
//		if((drawPixelStep == Step.FETCH_TILE_DATA_HIGH) && ((ticks % 2) == 0)) {
//			tileDataHigh = VRAM[dataAddr + low + 1];
//			drawPixelStep = Step.PUSH_TO_FIFO;
//		}
//		
//		//Push to BG FIFO
//		if((drawPixelStep == Step.PUSH_TO_FIFO) && ((ticks % 2) == 0)) {
//			if(bgFifo.isEmpty()) {
//				//Push
//				for(byte bit = 7; bit > 0; bit--) {
//					bgFifo.add((((tileDataHigh >> bit) & 0x1) << 1) | ((tileDataLow >> bit) & 0x1));
//				}
//			}
//		}
//		
//		//Push pixels to LCD
		
		if(((ticks - (456 * LY)) % 369) == 0) { //80 OAM Scan and 289 pixel draw(stub)
			STAT = (STAT & 0xFC); //Mode = H_BLANK (0b00)
			ic.requestInterrupt(InterruptType.LCD_STAT);
		}
	}
	
	public void hBlank() {
		byte windowEnable = Utils.getBit(LCDC, 5);
		//Ticks per scanline
		if(((ticks - (456 * LY)) % 456) == 0) {
			Arrays.fill(oamBuffer, 0);
			index = 0;
			offset = 0;
			LY++;
			if(LY > 143) {
				windowLineCounter = 0;
				STAT = (STAT & 0xFC) | 0b01; //Mode = V_BLANK (0b01)
				ic.requestInterrupt(InterruptType.VBLANK);
			} else {
				if(windowEnable == 1) {
					windowLineCounter++;
				}
				STAT = (STAT & 0xFC) | 0b10; //Mode = OAM_SCAN (0b10)
			}
		}
	}
	
	public void vBlank() {
		//Ticks per scanline
		if(((ticks - (456 * LY)) % 456) == 0) {
			LY++;
			if(LY > 153) {				
				LY = 0;
				STAT = (STAT & 0xFC) | 0b10; //Mode = OAM_SCAN (0b10)
				ic.requestInterrupt(InterruptType.LCD_STAT);
			}
		}		
	}
	
	private boolean enableDMA = false;
	private int dmaAddr = 0,oamIndex = 0;
	private void OAMDMATransfer() {
		OAM[oamIndex] = bus.read(dmaAddr);
		dmaAddr++;
		oamIndex++;
		if((dmaAddr & 0xFF) > 0x9F) {
			enableDMA = false;
			oamIndex = 0;
			dmaAddr = 0;
		}
	}
	
	public boolean isDMAEnabled() {
		return enableDMA;
	}
}
