package com.vzl.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import com.vzl.Emulator;
import com.vzl.LCDController;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private LCDController lcd;
	private Screen screen;
	private MenuBar menuBar;
	private VRAMViewer vramViewer;
	private VRAMViewerScreen vramViewerScreen;
	
	private Emulator emu;
	
	public Window() {
		screen = new Screen();
		//vramViewerScreen = new VRAMViewerScreen();
		menuBar = new MenuBar();
		lcd = new LCDController(screen);
		emu = new Emulator(lcd);
		
		menuBar.getMenu(0).getItem(0).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					System.out.println("Selected file: " + fileChooser.getSelectedFile().getAbsolutePath());
					emu.insertCartridge(fileChooser.getSelectedFile().getAbsoluteFile());
					emu.start();
				} else {
					System.out.println("File selection cancelled");
				}
			}			
		});
		
		menuBar.getMenu(0).getItem(1).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}			
		});
		
		menuBar.getMenu(1).getItem(0).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vramViewerScreen = new VRAMViewerScreen();
				vramViewer = new VRAMViewer(vramViewerScreen);
				lcd.enableVRAMViewer(vramViewerScreen);
			}			
		});
		
		this.setTitle("ZGB");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(screen);
		this.setJMenuBar(menuBar);
		this.pack();
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
}
