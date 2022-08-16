package com.vzl.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import com.vzl.Bus;
import com.vzl.CPU;
import com.vzl.Cartridge;

@SuppressWarnings("serial")
public class Window extends JFrame {
	CPU cpu;
	Bus bus;
	Cartridge cart;
	
	Screen screen;
	MenuBar menuBar;
	
	public Window(CPU cpu, Bus bus) {
		this.cpu = cpu;
		this.bus = bus;
		
		screen = new Screen();
		menuBar = new MenuBar();
		
		menuBar.open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					System.out.println("Selected file: " + fileChooser.getSelectedFile().getAbsolutePath());
					cart = new Cartridge();
					cart.loadCart(fileChooser.getSelectedFile().getAbsoluteFile());
					bus.connect(cart);
				} else {
					System.out.println("File selection cancelled");
				}
			}			
		});
		
		menuBar.exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}			
		});
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(screen);
		this.setJMenuBar(menuBar);
		this.pack();
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
}
