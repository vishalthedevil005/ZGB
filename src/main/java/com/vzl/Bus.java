package com.vzl;

public class Bus {	
	private CPU cpu;
	private Cartridge cart;
	private PPU ppu;
	private Memory memory;
	
	private int[] vram;
	private int[] oam;
	private int[] ioReg;
	private int[] hram;
	private int ieReg;

	public Bus() {	
		vram = new int[8 * 1024]; // 8 KB VRAM. In CGB mode, switchable bank 0/1		
		oam = new int[4 * 40];
		ioReg = new int[128];
		hram = new int[127];
		ieReg = 0;
	}
	
	public void connectCPU(CPU cpu) {
		this.cpu = cpu;
	}
	
	public void connect(Cartridge cart) {
		this.cart = cart;
	}
	
	public void connect(PPU ppu) {
		this.ppu = ppu;
	}
	
	public void connect(Memory memory) {
		this.memory = memory;
	}

	//address size - 16 bit(2 byte)
	//data size - 8 bit(1 byte)
	
	public int read(int addr) {
		if(addr>=0x0000 && addr<=0xFFFF) {
			if(addr>=0x0000 && addr<=0x7FFF) {
				return cart.read(addr);
			}
			
			if(addr>=0x8000 && addr<=0x9FFF) {
				return vram[addr - 0x8000];
			}
			
			if(addr>=0xA000 && addr<=0xBFFF) {
				//return cart.read(addr);
				System.out.printf("UNSUPPORTED CART RAM READ at address $%04X", addr).println();
				System.exit(4);
			}
			
			if(addr>=0xC000 && addr<=0xFDFF) {
				return memory.read(addr);
			}
			
			
			if(addr>=0xFE00 && addr<=0xFE9F) {
				return oam[addr - 0xFE00];
			}
			
			//FEA0-FEFF	Not Usable
			
			if(addr>=0xFF00 && addr<=0xFF7F) {
//				if(addr == 0xFF40) {
//					return ppu.LCDC;
//				} else if(addr == 0xFF41) {
//					return ppu.STAT;
//				} else {
//					return ioReg[addr - 0xFF00];
//				}
				if(addr == 0xFF44) {
					int data = ioReg[addr - 0xFF00];
					return (data == 0x00) ? 0x90 : data;
				} else {
					return ioReg[addr - 0xFF00];
				}
				
			}
			
			if(addr>=0xFF80 && addr<=0xFFFE) {
				return hram[addr - 0xFF80];
			}
			
			if(addr==0xFFFF) {
				return ieReg;
			}
		}
			
		return 0;
	}
	
	public void write(int addr, int data) {
		if(addr>=0x0000 && addr<=0xFFFF) {
			if(addr>=0x0000 && addr<=0x7FFF) {
				cart.write(addr, data);
				return;
			}
			
			if(addr>=0x8000 && addr<=0x9FFF) {
				vram[addr - 0x8000] = data;
				return;
			}
			
			if(addr>=0xA000 && addr<=0xBFFF) {
				//cart.write(addr, data);
				System.out.printf("UNSUPPORTED CART RAM WRITE at address $%04X with data $%02X", addr, data).println();
				System.exit(4);
			}
			
			if(addr>=0xC000 && addr<=0xFDFF) {
				memory.write(addr, data);
			}
			
			if(addr>=0xFE00 && addr<=0xFE9F) {
				oam[addr - 0xFE00] = data;
				return;
			}
			
			//FEA0-FEFF	Not Usable
			
			if(addr>=0xFF00 && addr<=0xFF7F) {
				if(addr == 0xFF01) {
					System.out.print((char) data);
				}
				ioReg[addr - 0xFF00] = data;
//				if(addr == 0xFF40) {
//					ppu.LCDC = data;
//					return;
//				} else if(addr == 0xFF41) {
//					ppu.STAT = data;
//					return;
//				} else {
//					ioReg[addr - 0xFF00] = data;
//					return;
//				}				
			}
			
			if(addr>=0xFF80 && addr<=0xFFFE) {
				hram[addr - 0xFF80] = data;
				return;
			}
			
			if(addr==0xFFFF) {
				ieReg = data;
				return;
			}
		}
	}
}
