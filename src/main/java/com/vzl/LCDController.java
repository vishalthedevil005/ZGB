package com.vzl;

import java.util.Random;

import com.vzl.ui.Screen;

public class LCDController {
	Random rand = new Random();
	private Screen screen;
	private int[][] frameBuffer = new int[24*8][16*8];
	
	public LCDController(Screen screen) {
		this.screen = screen;
	}
	
	public void updateDisplay(int[] data) {
		int index = 0;
		int x = 0, y = 0;
		int lo = 0, hi = 0;
		for(int i = 0; i < 24; i++) {
			y = i*8;
			for(int j = 0; j < 16; j++) {				
				x = j*8;
				for(int k = 0; k < 16; k+=2) {//For each line
					lo = data[index + k];
					hi = data[index + k + 1];
					for(byte b = 7; b >= 0; b--) {
						frameBuffer[y][x] = (((hi >> b) & 0x1) << 1) | ((lo >> b) & 0x1); // 2bits per pixel
						x++;
					}
					x = j*8;
					y++;
				}
				y = i*8;
				index+=16;
			}
		}
		screen.refreshDisplay(frameBuffer);
	}
}
