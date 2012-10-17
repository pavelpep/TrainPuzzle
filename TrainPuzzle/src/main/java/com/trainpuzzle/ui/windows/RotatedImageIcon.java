package com.trainpuzzle.ui.windows;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public class RotatedImageIcon extends ImageIcon {
	
	int multiplesOf45Degrees;
	
	public RotatedImageIcon(String filename, int multiplesOf45Degrees) {
		super(filename);
		this.multiplesOf45Degrees = multiplesOf45Degrees;
	}
	
	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D)g.create();
		g2.rotate(Math.toRadians(multiplesOf45Degrees * 45),getIconWidth() / 2, getIconHeight() / 2);
		super.paintIcon(c, g2, x, y);
	}
}
