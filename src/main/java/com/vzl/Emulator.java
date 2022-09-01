package com.vzl;

import java.io.File;

public class Emulator {
	private CPU cpu;
	private Bus bus;
	private PPU ppu;
	private Memory mem = new Memory();
	private Cartridge cart;
	
	private Thread cpuThread;
	
	public Emulator() {
		cpu = new CPU();
		bus = new Bus();
		PPU ppu = new PPU();
		
		cpu.connectBus(bus);
		bus.connectCPU(cpu);
		
		bus.connect(ppu);
		bus.connect(mem);
	}
	
	public void insertCartridge(File f) {
		cart = new Cartridge();
		cart.loadCart(f);
		bus.connect(cart);
	}
	
	public void start() {
		Thread cpuThread = new Thread() {
			@Override
			public void run() {
				while(!interrupted()) {
					cpu.run();
				}
			}
		};
		cpuThread.start();
	}
	
	public void stop() {
		cpuThread.interrupt();
	}
}
