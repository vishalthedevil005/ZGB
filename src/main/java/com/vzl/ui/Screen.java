package com.vzl.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Screen extends JPanel {
	private final static int WIDTH = 160;
	private final static int HEIGHT = 144;
	private final static int SCALE = 4;
	
	private static final Map<Integer, Color> COLOR_PALETTE_MAP = new HashMap<Integer, Color>();
	static {
		COLOR_PALETTE_MAP.put(0x11, Color.black);
		COLOR_PALETTE_MAP.put(0x10, Color.darkGray);
		COLOR_PALETTE_MAP.put(0x01, Color.lightGray);
		COLOR_PALETTE_MAP.put(0x00, Color.white);
	};

	Screen() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setBackground(Color.black);
		showScreen();
	}

	void showScreen() {
		this.setVisible(true);
	}

	void hideScreen() {
		this.setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
