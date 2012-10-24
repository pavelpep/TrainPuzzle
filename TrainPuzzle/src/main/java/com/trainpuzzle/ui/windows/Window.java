package com.trainpuzzle.ui.windows;

import java.awt.*;

import javax.swing.*;

/* The purpose of this abstract class (i.e. superclass) is to put the common method name Create() 
 * into one class, which is used in common by other subclasses that create Windows.
 */

abstract class Window extends JFrame {
	// Layout Manager
	protected GridBagConstraints gbConstraints = new GridBagConstraints();
	protected static final int DEFAULT_WIDTH = 1028;
	protected static final int DEFAULT_HEIGHT = 768;
	
	Window() {}
	
	// Creates the JFrame with elements
	public abstract void create();
	
	// Initializes a specific jComponent
	protected void addComponent(Container container, JComponent jComponent, int fontLayout, int fontSize,
			Color bgColor, int gridX, int gridY, int gridWidth, int gridHeight, int anchor, int fill, Insets inset, boolean isEnabled) {
		jComponent.setFont(new Font("Arial", fontLayout, fontSize));
		jComponent.setBackground(bgColor);
		this.gbConstraints.gridx = gridX;
		this.gbConstraints.gridy = gridY;
		this.gbConstraints.gridwidth = gridWidth;
		this.gbConstraints.gridheight = gridHeight;
		this.gbConstraints.anchor = anchor;
		this.gbConstraints.fill = fill;
		this.gbConstraints.insets = inset;
		jComponent.setEnabled(isEnabled);
		//jComponent.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red));
		container.add(jComponent, gbConstraints);
	}
}
