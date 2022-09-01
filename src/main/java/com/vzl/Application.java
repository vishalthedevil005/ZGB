package com.vzl;

import javax.swing.SwingUtilities;

import com.vzl.ui.Window;

public class Application {	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Window();
			}
		});
	}
}
