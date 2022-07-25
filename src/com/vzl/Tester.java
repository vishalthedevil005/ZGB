package com.vzl;

import java.io.File;
import java.util.Scanner;

public class Tester {
	public static void main(String[] args) {
		CPU cpu = new CPU();
		Bus bus = new Bus();
		Cartridge cart = new Cartridge();
		
		cpu.connectBus(bus);
		
		bus.connectCPU(cpu);
		bus.connect(cart);
		
		Scanner s = new Scanner(System.in);
		System.out.println("Enter filepath");
	    String path = s.nextLine();
	    System.out.println("Loading file: " + path);
	    
	    cart.loadCart(new File(path));
		
		int i=0;
		while(true) {
			cpu.printRegisters();cpu.printFlags();
			cpu.fetch();
			cpu.execute();
			i++;
		}
	}
}
