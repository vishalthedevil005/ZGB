package com.vzl.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import com.vzl.Emulator;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private Screen screen;
	private MenuBar menuBar;
	
	private Emulator emu = new Emulator();
	
	public Window() {
		screen = new Screen();
		menuBar = new MenuBar();
		
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
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(screen);
		this.setJMenuBar(menuBar);
		this.pack();
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
}
