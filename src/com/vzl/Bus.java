package com.vzl;

public class Bus {
	int cycles = 0;
	
	private CPU cpu;
	private Cartridge cart;
	private PPU ppu;
	
	private int[] romBank00;
	private int[] romBank01NN;
	private int[] vram;
	private int[] ramExt;
	private int[] ram1;
	private int[] ram2;
	private int[] oam;
	private int[] ioReg;
	private int[] hram;
	private int ieReg;

	public Bus() {		
		romBank00 = new int[16 * 1024]; // 16 KiB ROM bank 00. From cartridge, usually a fixed bank
		romBank01NN = new int[16 * 1024]; //16 KiB ROM Bank 01~NN. From cartridge, switchable bank via mapper (if any)		
		vram = new int[8 * 1024]; // 8 KB VRAM. In CGB mode, switchable bank 0/1
		ramExt = new int[8 * 1024]; // 8 KB External RAM. From cartridge, switchable bank if any
		
		// 8 KB WorkRAM -> 4KB + 4KB
		ram1 = new int [4 * 1024]; // Internal RAM.
		ram2 = new int [4 * 1024]; // Internal RAM. In CGB mode, switchable bank 1~7
		
		oam = new int[4 * 40];
		ioReg = new int[96];
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

	//address size - 16 bit(2 byte)
	//data size - 8 bit(1 byte)
	
	public int read(int addr) {
		cycles++;
		if(addr>=0x0000 && addr<=0xFFFF) {
			if(addr>=0x0000 && addr<=0x3FFF) {
				//return romBank00[addr];
				return cart.read(addr);
			}
			
			if(addr>=0x4000 && addr<=0x7FFF) {
				//return romBank01NN[addr - 0x4000];
				return cart.read(addr);
			}
			
			if(addr>=0x8000 && addr<=0x9FFF) {
				return vram[addr - 0x8000];
			}
			
			if(addr>=0xA000 && addr<=0xBFFF) {
				return ramExt[addr - 0xA000];
			}
			
			if(addr>=0xC000 && addr<=0xCFFF) {
				return ram1[addr - 0xC000];
			}
			
			if(addr>=0xD000 && addr<=0xDFFF) {
				return ram2[addr - 0xD000];
			}
			
			//Address range E000-FDFF is same as C000-DDFF (ECHO RAM)
			if(addr>=0xE000 && addr<=0xEFFF) {
				return ram1[addr - 0xE000];
			}
			if(addr>=0xF000 && addr<=0xFDFF) {
				return ram2[addr - 0xF000];
			}
			
			
			if(addr>=0xFE00 && addr<=0xFE9F) {
				return oam[addr - 0xFE00];
			}
			
			//FEA0-FEFF	Not Usable
			
			if(addr>=0xFF00 && addr<=0xFF7F) {
				if(addr == 0xFF40) {
					return ppu.LCDC;
				} else if(addr == 0xFF40) {
					return ppu.STAT;
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
		cycles++;
		if(addr>=0x0000 && addr<=0xFFFF) {
			if(addr>=0x0000 && addr<=0x3FFF) {
				//romBank00[addr] = data;
				cart.write(addr, data);
			}
			
			if(addr>=0x4000 && addr<=0x7FFF) {
				//romBank01NN[addr - 0x4000] = data;
				cart.write(addr, data);
			}
			
			if(addr>=0x8000 && addr<=0x9FFF) {
				vram[addr - 0x8000] = data;
			}
			
			if(addr>=0xA000 && addr<=0xBFFF) {
				ramExt[addr - 0xA000] = data;
			}
			
			if(addr>=0xC000 && addr<=0xCFFF) {
				ram1[addr - 0xC000] = data;
			}
			
			if(addr>=0xD000 && addr<=0xDFFF) {
				ram2[addr - 0xD000] = data;
			}
			
			//Address range E000-FDFF is same as C000-DDFF (ECHO RAM)
			if(addr>=0xE000 && addr<=0xFDFF) {
				ram1[addr - 0xE000] = data;
			}
			
			if(addr>=0xFE00 && addr<=0xFE9F) {
				oam[addr - 0xFE00] = data;
			}
			
			//FEA0-FEFF	Not Usable
			
			if(addr>=0xFF00 && addr<=0xFF7F) {
				if(addr == 0xFF40) {
					ppu.LCDC = data;
				} else if(addr == 0xFF40) {
					ppu.STAT = data;
				} else {
					ioReg[addr - 0xFF00] = data;
				}				
			}
			
			if(addr>=0xFF80 && addr<=0xFFFE) {
				hram[addr - 0xFF80] = data;
			}
			
			if(addr==0xFFFF) {
				ieReg = data;
			}
		}
	}
}
