package com.vzl;

public class PPU {
	private InterruptController ic;
	private int[] VRAM = new int[8 * 1024];
	private int[] OAM = new int[4 * 40];
	
	private int LCDC;
	private int STAT;
	private int SCY;
	private int SCX;
	private int LY = 0x90;
	private int LYC;
	private int DMA;
	private int BGP;
	private int OBP0;
	private int OBP1;
	private int WY;
	private int WX;
	
	public PPU(InterruptController ic) {
		this.ic = ic;
	}
	
	public int read(int addr) {			
		if(addr >= 0x8000 && addr <= 0x9FFF) return VRAM[addr - 0x8000];
		if(addr >= 0xFE00 && addr <= 0xFE9F) return OAM[addr - 0xFE00];
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
		if(addr >= 0x8000 && addr <= 0x9FFF) VRAM[addr - 0x8000] = data;
		if(addr >= 0xFE00 && addr <= 0xFE9F) OAM[addr - 0xFE00] = data;
		if(addr == 0xFF40) LCDC = data;
		if(addr == 0xFF41) STAT = data;
		if(addr == 0xFF42) SCY = data;
		if(addr == 0xFF43) SCX = data;
		if(addr == 0xFF44) LY = data;
		if(addr == 0xFF45) LYC = data;
		if(addr == 0xFF46) DMA = data;
		if(addr == 0xFF47) BGP = data;
		if(addr == 0xFF48) OBP0 = data;
		if(addr == 0xFF49) OBP1 = data;
		if(addr == 0xFF4A) WY = data;
		if(addr == 0xFF4B) WX = data;
	}
}
