package com.trainpuzzle.ui.windows;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class RotatedImageIcon extends ImageIcon {
	private static final long serialVersionUID = 1L;
	
	int multiplesOf45Degrees = 0;
	
	public RotatedImageIcon(String filename) {
		this(filename, 0);
	}
	
	public RotatedImageIcon(Image image) {
		this(image, 0);
	}
	
	public RotatedImageIcon(Image image, int multiplesOf45Degrees) {
		super.setImage(image);
		this.multiplesOf45Degrees = multiplesOf45Degrees;
	}
	
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
	
	public void rotate90DegreesClockwise(){
		this.multiplesOf45Degrees = this.multiplesOf45Degrees + 2;
	}
	
	public void rotate90DegreesCounterClockwise(){
		this.multiplesOf45Degrees = this.multiplesOf45Degrees + 6;
	}
}
