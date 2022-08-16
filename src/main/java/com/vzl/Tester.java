package com.vzl;

import java.io.File;

import javax.swing.SwingUtilities;

import com.vzl.ui.Window;

public class Tester {
	private static Thread cpuThread;
	
	public static void main(String[] args) throws InterruptedException {
		CPU cpu = new CPU();
		Bus bus = new Bus();
		PPU ppu = new PPU();

		cpu.connectBus(bus);
		bus.connectCPU(cpu);
		
		Cartridge cart = new Cartridge();
		cart.loadCart(new File("E://gb-test-roms-master//cpu_instrs//individual//01-special.gb"));
		bus.connect(cart);
		
		bus.connect(ppu);

//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				new Window(cpu, bus);
//			}
//		});
		
		while(true) {
			cpu.printRegisters();
			cpu.printFlags();
			cpu.fetch();
			cpu.execute();
			//Thread.sleep(1000);
		}
	}
}
