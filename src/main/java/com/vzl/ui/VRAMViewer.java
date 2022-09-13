package com.vzl.ui;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class VRAMViewer extends JDialog {
	private VRAMViewerScreen screen;
	public VRAMViewer(VRAMViewerScreen screen) {
		this.screen = screen;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(screen);
		this.setTitle("VRAM Viewer");
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void dispose() {
		screen = null;
		super.dispose();
	}
}
