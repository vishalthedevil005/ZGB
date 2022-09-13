package com.vzl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.vzl.InterruptController.InterruptType;

public class PPU {
	private static final int SCANLINES_PER_FRAME = 154;
	private static final int TICKS_PER_SCANLINE = 456;
	private static final int X_RES = 160;
	private static final int Y_RES = 144;
	private int[][] frameBuffer = new int[Y_RES][X_RES];
	
	private Bus bus;
	
	private InterruptController ic;
	private LCDController lcd;
	private int ticks = 0;
	private enum ModeType {
		H_BLANK, V_BLANK, OAM_SCAN, DRAW_PIXEL
	}
	
	private int[] VRAM = new int[8 * 1024];
	private int[] OAM = new int[4 * 40];
	
	private int LCDC = 0x91;
	//LCDC helper methods
	private int getBgEnableFlag() {return Utils.getBit(LCDC, 0);}
	private int getObjEnableFlag() {return Utils.getBit(LCDC, 1);}
	private int getObjHeight() {return (Utils.getBit(LCDC,2) == 0) ? 8 : 16;}
	private int getBgMapArea() {return (Utils.getBit(LCDC,3) == 0) ? 0x9800 : 0x9C00;}
	private int getBgDataArea() {return (Utils.getBit(LCDC,4) == 0) ? 0x8800 : 0x8000;}
	private int getWinEnableFlag() {return Utils.getBit(LCDC, 5);}
	private int getWinMapArea() {return (Utils.getBit(LCDC, 6) == 0) ? 0x9800 : 0x9C00;}
	private int getLcdEnableFlag() {return Utils.getBit(LCDC, 7);}
	
	private int STAT = 0;
	//STAT helper methods
	private ModeType getModeType() {
		switch(STAT & 0b11) {
			case 0b00: return ModeType.H_BLANK;
			case 0b01: return ModeType.V_BLANK;
			case 0b10: return ModeType.OAM_SCAN;
			case 0b11: return ModeType.DRAW_PIXEL;
		}
		return null;
	}
	private void setModeType(ModeType m) {
		int value = 0;
		switch(m) {
			case H_BLANK: value = 0; break;
			case V_BLANK: value = 1; break;
			case OAM_SCAN: value = 2; break;
			case DRAW_PIXEL: value = 3; break;
		}
		STAT = (STAT & ~0b11) & 0xFF;
		STAT = (STAT | value) & 0xFF;
	}
	private int getLYLYCCompareFlag() {
		return Utils.getBit(STAT, 2);
	}
	private void setLYLYCCompareFlag(int s) {
		s = (s << 2) & 0xFF;
		STAT = (STAT | s) & 0xFF;
	} 
	
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
		setModeType(ModeType.OAM_SCAN);
	}
	
	void connectBus(Bus b) {
		this.bus = b;
	}
	
	public int read(int addr) {			
		if(addr >= 0x8000 && addr <= 0x9FFF) {
//			if(getModeType() == ModeType.DRAW_PIXEL) {
//				return 0xFF;
//			}
			return VRAM[addr - 0x8000];
		}
		if(addr >= 0xFE00 && addr <= 0xFE9F) {
//			if((getModeType() == ModeType.OAM_SCAN) || (getModeType() == ModeType.DRAW_PIXEL)) {
//				return 0xFF;
//			}
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
//			if(getModeType() == ModeType.DRAW_PIXEL) {
//				return;
//			}
			VRAM[addr - 0x8000] = data;
			return;
		}
		if(addr >= 0xFE00 && addr <= 0xFE9F) {
//			if((getModeType() == ModeType.OAM_SCAN) || (getModeType() == ModeType.DRAW_PIXEL)) {
//				return;				
//			}
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
	
	int frameTicks;
	public void tick() {
		frameTicks++;
		ticks++;
		switch(getModeType()) {
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
		if(frameTicks == 70224) {
			frameTicks = 0;
			lcd.display(frameBuffer);
			if(lcd.VRAMViewerFlag) {
				lcd.updateVRAMViewerScreen(VRAM);
			}
		}
	}
	
	private enum StepType {
		FETCH_TILE_NUM, FETCH_TILE_DATA_LOW, FETCH_TILE_DATA_HIGH, PUSH_TO_FIFO
	}
	private StepType currentStep = StepType.FETCH_TILE_NUM;	
	
	private void incrementLY() {
		LY++;
		if(LY == LYC) {
			setLYLYCCompareFlag(1);
			if(Utils.getBit(STAT, 6) == 1) {
				ic.requestInterrupt(InterruptType.LCD_STAT);
			}
		} else {
			setLYLYCCompareFlag(0);
		}
	}
	
	private int[] oamBuffer = new int[40];
	private int objectsStored = 0;
	private int index = 0, bufferIndex = 0;
	private int yPos = 0, xPos = 0, objTileIndex = 0, objTileAttrs = 0;
	public void oamScan() {
		if((ticks % 2) == 0) {
			yPos = OAM[index];
			xPos = OAM[index + 1];
			objTileIndex = OAM[index + 2];
			objTileAttrs = OAM[index + 3];
			
			if((xPos > 0) && ((LY+16) >= yPos) && ((LY+16) < (yPos + getObjHeight())) && (objectsStored < 10)) {
				oamBuffer[bufferIndex] = yPos;
				oamBuffer[bufferIndex + 1] = xPos;
				oamBuffer[bufferIndex + 2] = objTileIndex;
				oamBuffer[bufferIndex + 3] = objTileAttrs;
				bufferIndex+=4;
				objectsStored++;
			}
			index+=4;
		}
		
		
		if(ticks >= 80) {
			index = 0;
			bufferIndex = 0;
			setModeType(ModeType.DRAW_PIXEL);
			currentStep = StepType.FETCH_TILE_NUM;
			fetcherX = 0;
			tileNumber = 0;
			tileX = 0;
			tileY = 0;
			xPixelsPushed = 0;
			firstFetch = true;
		}
	}
	
	private int fetcherX = 0;
	private int mapAddr = 0, dataAddr = 0;
	private int tileNumber = 0;
	private int tileDataLow = 0, tileDataHigh = 0;
	private boolean firstFetch = false;
	private int tileX = 0, tileY = 0;
	private Queue<Integer> bgFifo = new LinkedList<Integer>();
	private Queue<Integer> objFifo = new LinkedList<Integer>();
	private int xPixelsPushed = 0;
	public void drawPixel() {		
		if((ticks % 2) == 0) {
			switch(currentStep) {
				case FETCH_TILE_NUM:
					if(getBgEnableFlag() == 1) {
						tileX = ((fetcherX + ((SCX / 8) & 0x1F)) & 0x3FF);
						tileY = ((32 * (((LY + SCY) & 0xFF) / 8)) & 0x3FF);
						mapAddr = getBgMapArea() + tileX + tileY;
						tileNumber = bus.read(mapAddr);
					}
					currentStep = StepType.FETCH_TILE_DATA_LOW;
					fetcherX += 1;
					break;
				case FETCH_TILE_DATA_LOW:
					dataAddr = getBgDataArea() + (tileNumber * 16) + (2 * ((LY + SCY) % 8));
					tileDataLow = bus.read(dataAddr);
					currentStep = StepType.FETCH_TILE_DATA_HIGH;
					break;
				case FETCH_TILE_DATA_HIGH:
					tileDataHigh = bus.read(dataAddr + 1);
					if(firstFetch) {
						fetcherX = 0;
						firstFetch = false;
						currentStep = StepType.FETCH_TILE_NUM;
					} else {
						currentStep = StepType.PUSH_TO_FIFO;
					}
					break;
				case PUSH_TO_FIFO:
					if(bgFifoAdd()) {
						currentStep = StepType.FETCH_TILE_NUM;
					}
					break;
			}
		}
		
		//Pushing to frame buffer
		pushPixel();
		
		if(xPixelsPushed >= X_RES) {
			bgFifo.clear();
			objFifo.clear();
			fetcherX = 0;
			xPixelsPushed = 0;
			objectsStored = 0;
			Arrays.fill(oamBuffer, 0);
			setModeType(ModeType.H_BLANK);
			if(Utils.getBit(STAT, 3) == 1) {
				ic.requestInterrupt(InterruptType.LCD_STAT);
			}
		}
	}
	
	private boolean bgFifoAdd() {
		if(bgFifo.size() >= 8) {
			//bgFifo filled
			return false;
		}
		for(byte bit = 7; bit >= 0; bit--) {
			int color = (((tileDataHigh >> bit) & 0x1) << 1) | ((tileDataLow >> bit) & 0x1);
			bgFifo.add(color);
		}
		return true;
	}
	
	private void pushPixel() {
		if(!bgFifo.isEmpty()) {
			lcd.updatePixel(xPixelsPushed, LY, bgFifo.poll());
			xPixelsPushed++;
		}
	}
	
	private void hBlank() {
		if(ticks>=TICKS_PER_SCANLINE) {
			incrementLY();
			if(LY >= Y_RES) {
				setModeType(ModeType.V_BLANK);
				ic.requestInterrupt(InterruptType.VBLANK);
				if(Utils.getBit(STAT, 4) == 1) {
					ic.requestInterrupt(InterruptType.LCD_STAT);
				}
			} else {
				setModeType(ModeType.OAM_SCAN);
			}
			ticks = 0;
		}
	}
	
	
	private void vBlank() {
		if(ticks>= TICKS_PER_SCANLINE) {
			incrementLY();
			if(LY >= SCANLINES_PER_FRAME) {
				setModeType(ModeType.OAM_SCAN);
				LY = 0;
			}
			ticks = 0;
		}
	}
	
	private boolean enableDMA = false;
	private int dmaAddr = 0, oamIndex = 0;
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
