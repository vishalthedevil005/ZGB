package com.vzl;

public class InterruptController {
	private int IE = 0x00;
	private int IF = 0x00;
	
	enum InterruptType {
		VBLANK, LCD_STAT, TIMER, SERIAL, JOYPAD
	}
	
	public int read(int addr) {
		if(addr==0xFF0F) {
			return IF;
		}
		
		if(addr==0xFFFF) {
			return IE;
		}
		
		return 0x00;
	}
	
	public void write(int addr, int data) {
		if(addr==0xFF0F) {
			IF = data;
			return;
		}
		
		if(addr==0xFFFF) {
			IE = data;
			return;
		}
	}
	
	public void requestInterrupt(InterruptType it) {
		switch(it) {
			case VBLANK:
				IF = IF | 0x1;
				break;
			case LCD_STAT:
				IF = IF | 0x2;
				break;
			case TIMER:
				IF = IF | 0x4;
				break;
			case SERIAL:
				IF = IF | 0x8;
				break;
			case JOYPAD:
				IF = IF | 0x10;
				break;
			default:
				break;
		}
	}
	
	public boolean pending() {
		return (((IF & IE) & 0x1F) != 0);
	}
}
