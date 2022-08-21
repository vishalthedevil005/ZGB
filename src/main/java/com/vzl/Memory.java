package com.vzl;

public class Memory {
	private int[] ram1;
	private int[] ram2;
	
	public Memory() {
		// 8 KB WorkRAM
		ram1 = new int [4 * 1024]; // 4KB Internal RAM.
		ram2 = new int [4 * 1024]; // 4KB Internal RAM. In CGB mode, switchable bank 1~7
	}
	
	public int read(int addr) {
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
		return 0xFF;
	} 
	
	public void write(int addr,int data) {
		if(addr>=0xC000 && addr<=0xCFFF) {
			ram1[addr - 0xC000] = data;							
			return;
		}		
		if(addr>=0xD000 && addr<=0xDFFF) {
			if(addr == 0xDD02) {
				ram2[addr - 0xD000] = data;
			} else {
				ram2[addr - 0xD000] = data;
			}			
			return;
		}
		
		//Address range E000-FDFF is same as C000-DDFF (ECHO RAM)
		if(addr>=0xE000 && addr<=0xEFFF) {
			ram1[addr - 0xE000] = data;
			return;
		}			
		if(addr>=0xF000 && addr<=0xFDFF) {
			if(addr == 0xFD02) {
				ram2[addr - 0xF000] = data;
			} else {
				ram2[addr - 0xF000] = data;
			}
			return;
		}
	}
}
