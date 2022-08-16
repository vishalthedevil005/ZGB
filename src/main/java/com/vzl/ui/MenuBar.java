package com.vzl.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.vzl.Cartridge;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	JMenu file;
	JMenuItem open;
	JMenuItem exit;
	
	Cartridge cart = new Cartridge();

	public MenuBar() {
		file = new JMenu("File");

		open = new JMenuItem("Open");
		exit = new JMenuItem("Exit");

		file.add(open);
		file.add(exit);

		this.add(file);
	}
}
