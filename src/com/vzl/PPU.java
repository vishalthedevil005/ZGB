package com.vzl;

public class PPU {
	int LCDC;
	int STAT;
	
	//Layers
	int[][] background = new int[256][256];
	int[][] window = new int[256][256];
	int[][] tile = new int[160][144];
	
	int[][] viewport = new int[160][144];
}
