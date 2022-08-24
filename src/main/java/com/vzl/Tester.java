package com.vzl;

import java.io.File;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vzl.ui.Window;

public class Tester {
	private static final Logger logger = LogManager.getLogger();
	private static Thread cpuThread;
	private static StringBuilder s = new StringBuilder();
	
	public static void main(String[] args) throws InterruptedException {
		CPU cpu = new CPU();
		Bus bus = new Bus();
		PPU ppu = new PPU();
		Memory memory = new Memory();

		cpu.connectBus(bus);
		bus.connectCPU(cpu);
		
		Cartridge cart = new Cartridge();
		cart.loadCart(new File("E:\\gb-test-roms-master\\cpu_instrs\\cpu_instrs.gb"));
		bus.connect(cart);
		
		bus.connect(ppu);
		bus.connect(memory);

//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				new Window(cpu, bus);
//			}
//		});
		long i = 0;
		while(true) {
		//while(i < 1068422) {
//			cpu.printRegisters();
//			cpu.printFlags();
			//logger.info(cpu.getRegistersStatus() + " " + cpu.getFlagsStatus());
			cpu.fetch();
			
//			s.append(cpu.getRegistersStatus());
//			//.append(" ")
//			//.append(cpu.getFlagsStatus()).append(" ").append(cpu.currentInstruction.toString());
//			logger.info(s);
//			s.setLength(0);
			
			cpu.execute();
			//i++;
			//Thread.sleep(2000);
		}
	}
}
