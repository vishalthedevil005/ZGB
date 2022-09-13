package com.vzl.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VRAMViewerScreen extends JPanel {
	private static final int WIDTH = 16 * 8;
	private static final int HEIGHT = 24 * 8;
	private static final int SCALE = 3;
	
	private int[][] data = new int[HEIGHT][WIDTH];
	private static final Map<Integer, Color> COLOR_PALETTE_MAP = new HashMap<Integer, Color>();
	static {
		COLOR_PALETTE_MAP.put(3, Color.black);
		COLOR_PALETTE_MAP.put(2, Color.darkGray);
		COLOR_PALETTE_MAP.put(1, Color.lightGray);
		COLOR_PALETTE_MAP.put(0, Color.white);
	};
	
	private Graphics g;

	VRAMViewerScreen() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setBackground(Color.black);
		showScreen();
	}

	public void showScreen() {
		this.setVisible(true);
	}

	public void hideScreen() {
		this.setVisible(false);
	}
	
	public void refreshDisplay(int[][] data) {
		this.data = data;
		repaint();
	}
	
	private void paintFullScreen() {
        for (int y = 0; y < HEIGHT; y++) {
//        	if((y % 8) == 0) {
//        		drawHGridLine(0, y);
//        	}
            for (int x = 0; x < WIDTH; x++) {
//            	if((y == 0) && ((x % 8) == 0)) {
//            		drawVGridLine(x, 0);
//            	}
                paintPixel(data[y][x], x, y);
            }
        }
    }
	
	void drawHGridLine(int x, int y) {
		g.drawLine(x * SCALE, y * SCALE, WIDTH * SCALE, y * SCALE);
	}
	
	void drawVGridLine(int x, int y) {
		g.drawLine(x * SCALE, y * SCALE, x * SCALE, HEIGHT * SCALE);
	}
	
	public void paintPixel(int color, int x, int y) {
		g.setColor(COLOR_PALETTE_MAP.get(color));
		g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		this.g = g;
		super.paintComponent(g);
		paintFullScreen();
	}
}
