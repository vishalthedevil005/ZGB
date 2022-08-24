package com.vzl;

import java.util.LinkedList;
import java.util.Queue;

public class PPU {
	int LCDC;
	int STAT;
	
	//Layers
	int[][] background = new int[256][256];
	int[][] window = new int[256][256];
	int[][] tile = new int[160][144];
	
	int[][] viewport = new int[160][144];
	
	//Pixel FIFO	
	Queue<Integer> bgFIFO = new LinkedList<Integer>();
	Queue<Integer> sprFIFO = new LinkedList<Integer>();
	
	public int read(int addr) {
		return 00;
	}
	
	public void write(int addr,int data) {
		
	}
}
