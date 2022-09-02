package com.vzl;

import com.vzl.InterruptController.InterruptType;

public class Timer {
	private InterruptController ic;
	
	private int DIV;
	private int TIMA;
	private int TMA;
	private int TAC;
	
	private int position[] = {9,3,5,7};
	int divBit = 0;
	int timerEnable = 0;
	int currentBit = 0;
	int previousBit = 0;
	boolean timaOverflow = false;
	int wait = 0;
	
	public Timer(InterruptController ic) {
		this.ic = ic;
	}
	
	public int read(int addr) {
		if(addr == 0xFF04) return DIV/256;
		if(addr == 0xFF05) return TIMA;
		if(addr == 0xFF06) return TMA;
		if(addr == 0xFF07) return TAC | 0b11111000;
		return 0;
	}
	
	public void write(int addr, int data) {
		if(addr == 0xFF04) {
			DIV = 0;
			return;
		}
		if(addr == 0xFF05) {
			if(wait < 5) {
				TIMA = data;
				timaOverflow = false;
				wait = 0;
			}
			return;
		}
		if(addr == 0xFF06) {
			TMA = data;
			return;
		}
		if(addr == 0xFF07) {
			TAC = data;
			return;
		}
	}
	
	public void tick() {
		DIV = (DIV + 1) & 0xFFFF;
		
		divBit = (DIV >> position[TAC & 0b11]) & 1;
		timerEnable = (TAC >> 2) & 1;
		
		currentBit = (divBit & timerEnable);
		
		if(previousBit == 0 && currentBit == 1) {
			TIMA++;
			if(TIMA > 0xFF) {
				TIMA = 0;
				timaOverflow = true;
				wait = 0;
			}
		}
		
		previousBit = currentBit;
		
		if(timaOverflow) {
			wait++;
			if(wait == 4) {
				//timer interrupt
				ic.requestInterrupt(InterruptType.TIMER);
			}
			if(wait == 5) {
				TIMA = TMA;				
			}
			if(wait == 6) {
				TIMA = TMA;	
				timaOverflow = false;
				wait = 0;
			}
		}
	}
}
