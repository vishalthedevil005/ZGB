package com.vzl;

import java.io.File;

public class Emulator {
	private LCDController lcd;
	private CPU cpu;
	private Bus bus;
	private PPU ppu;
	private Memory mem;
	private Cartridge cart;
	private InterruptController ic;
	private Timer timer;
	private DMA dma;
	
	private Thread cpuThread;
	
	public Emulator(LCDController lcd) {
		this.lcd = lcd;
		bus = new Bus();
		ic = new InterruptController();
		cpu = new CPU(ic);		
		ppu = new PPU(ic,lcd);
		mem = new Memory();
		timer = new Timer(ic);
		dma = new DMA();
		
		cpu.connectBus(bus);
		bus.connectCPU(cpu);
		
		ppu.connectBus(bus);
		bus.connect(ppu);
		
		bus.connect(ic);		
		bus.connect(mem);
		bus.connect(timer);
		
		dma.connectBus(bus);
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
						for(int i = 0; i < cycles; i++) {
							for(int j = 0; j < 4; j++) {
								timer.tick();
								ppu.tick();
								dma.tick();
							}
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
