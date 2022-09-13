package com.vzl.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	private JMenu file;
	private JMenuItem open;
	private JMenuItem exit;
	
	private JMenu options;
	private JMenuItem vramViewer;

	public MenuBar() {
		file = new JMenu("File");

		open = new JMenuItem("Open");
		exit = new JMenuItem("Exit");

		file.add(open);
		file.add(exit);
		
		options = new JMenu("Options");
		
		vramViewer = new JMenuItem("VRAM Viewer");
		options.add(vramViewer);
		
		this.add(file);
		this.add(options);
	}
}
