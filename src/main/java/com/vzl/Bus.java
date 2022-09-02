package com.vzl;

public class Bus {	
	private CPU cpu;
	private Cartridge cart;
	private PPU ppu;
	private Memory memory;
	private Timer timer;
	private InterruptController ic;
	
	private int[] ioReg;
	private int[] hram;

	public Bus() {
		ioReg = new int[128];
		hram = new int[127];
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
	
	public void connect(InterruptController ic) {
		this.ic = ic;
	}
	
	public void connect(Timer timer) {
		this.timer = timer;
	}

	//address size - 16 bit(2 byte)
	//data size - 8 bit(1 byte)
	
	public int read(int addr) {
		if(addr>=0x0000 && addr<=0xFFFF) {
			if(addr>=0x0000 && addr<=0x7FFF) {
				return cart.read(addr);
			}
			
			if(addr>=0x8000 && addr<=0x9FFF) {
				return ppu.read(addr);
			}
			
			if(addr>=0xA000 && addr<=0xBFFF) {
				return cart.read(addr);
			}
			
			if(addr>=0xC000 && addr<=0xFDFF) {
				return memory.read(addr);
			}
			
			
			if(addr>=0xFE00 && addr<=0xFE9F) {
				return ppu.read(addr);
			}
			
			//FEA0-FEFF	Not Usable
			
			if(addr>=0xFF00 && addr<=0xFF7F) {
				if(addr >= 0xFF04 && addr <= 0xFF07) {
					return timer.read(addr);					
				}
				
				if(addr==0xFF0F) {
					return ic.read(addr);
				}
				
				if(addr >= 0xFF40 && addr <= 0xFF4B) {
					return ppu.read(addr);
				}
				return ioReg[addr - 0xFF00];
			}
			
			if(addr>=0xFF80 && addr<=0xFFFE) {
				return hram[addr - 0xFF80];
			}
			
			if(addr==0xFFFF) {
				return ic.read(addr);
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
				ppu.write(addr,data);
				return;
			}
			
			if(addr>=0xA000 && addr<=0xBFFF) {
				cart.write(addr, data);				
			}
			
			if(addr>=0xC000 && addr<=0xFDFF) {
				memory.write(addr, data);
			}
			
			if(addr>=0xFE00 && addr<=0xFE9F) {
				ppu.write(addr,data);
				return;
			}
			
			//FEA0-FEFF	Not Usable
			
			if(addr>=0xFF00 && addr<=0xFF7F) {
				if(addr == 0xFF01) {
					System.out.print((char) data);
					return;
				}
				
				if(addr >= 0xFF04 && addr <= 0xFF07) {
					timer.write(addr,data);
					return;
				}
				 
				if(addr==0xFF0F) {
					ic.write(addr,data);
					return;
				}
				
				if(addr >= 0xFF40 && addr <= 0xFF4B) {
					ppu.write(addr,data);
					return;
				}
				ioReg[addr - 0xFF00] = data;			
			}
			
			if(addr>=0xFF80 && addr<=0xFFFE) {
				hram[addr - 0xFF80] = data;
				return;
			}
			
			if(addr==0xFFFF) {
				ic.write(addr,data);
				return;
			}
		}
	}
}
