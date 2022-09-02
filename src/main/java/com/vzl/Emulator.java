package com.vzl;

import java.io.File;

public class Emulator {
	private CPU cpu;
	private Bus bus;
	private PPU ppu;
	private Memory mem;
	private Cartridge cart;
	private InterruptController ic;
	private Timer timer;
	
	private Thread cpuThread;
	
	public Emulator() {
		bus = new Bus();
		ic = new InterruptController();
		cpu = new CPU(ic);		
		PPU ppu = new PPU(ic);
		mem = new Memory();		
		timer = new Timer(ic);
		
		cpu.connectBus(bus);
		bus.connectCPU(cpu);
		
		bus.connect(ic);
		bus.connect(ppu);
		bus.connect(mem);
		bus.connect(timer);
	}
	
	public void insertCartridge(File f) {
		cart = new Cartridge();
		cart.loadCart(f);
		bus.connect(cart);
	}
	
	public void start() {
		cpuThread = new Thread() {
			@Override
			public void run() {
				int cycles = 0;
				while(!interrupted()) {
					cycles = cpu.run();
					if(cycles > 0) {						
						for(int i = 0; i < (cycles * 4); i++) {
							timer.tick();
						}
						cycles = 0;
					} else {
						timer.tick();
					}					
					cpu.handleInterrupts();
				}
			}
		};
		cpuThread.start();
	}
	
	public void stop() {
		cpuThread.interrupt();
	}
}
