package com.trainpuzzle.ui.windows;

import java.awt.*;

import javax.swing.*;

/* The purpose of this abstract class (i.e. superclass) is to put the common method name Create() 
 * into one class, which is used in common by other subclasses that create Windows.
 */

abstract class Window extends JFrame {
	// Layout Manager
	protected GridBagConstraints gbConstraints = new GridBagConstraints();
			
	Window() {}
	
	// Creates the JFrame with elements
	public abstract void Create();
	
	// Initializes a specific jComponent
	protected void initializeComponent(JComponent jComponent, int fontLayout, int fontSize, Color bgColor,
			int gridX, int gridY, int gridWidth, int gridHeight, int anchor, int fill, Insets inset, boolean isEnabled) {
		jComponent.setFont(new Font("Arial", fontLayout, fontSize));
		jComponent.setBackground(bgColor);
		this.gbConstraints.gridx = gridX;
		this.gbConstraints.gridy = gridY;
		this.gbConstraints.gridwidth = gridWidth;
		this.gbConstraints.anchor = anchor;
		this.gbConstraints.fill = fill;
		this.gbConstraints.insets = inset;
		jComponent.setEnabled(isEnabled);
	}
}
