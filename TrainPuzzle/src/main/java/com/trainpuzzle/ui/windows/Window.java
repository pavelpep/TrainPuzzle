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
	
	// Initializes a specific jComponent
	protected void addComponent(Container container, JComponent jComponent, int fontLayout, int fontSize,
		Color bgColor, int gridX, int gridY, int gridWidth, int gridHeight, float weightX, float weightY, int anchor, int fill, Insets inset, boolean isEnabled) {
		
		gbConstraints = new GridBagConstraints();
		Font defaultFont = new Font("Arial", fontLayout, fontSize);
		jComponent.setFont(defaultFont);
		jComponent.setBackground(bgColor);
		this.gbConstraints.gridx = gridX;
		this.gbConstraints.gridy = gridY;
		this.gbConstraints.gridwidth = gridWidth;
		this.gbConstraints.gridheight = gridHeight;
		this.gbConstraints.anchor = anchor;
		this.gbConstraints.fill = fill;
		this.gbConstraints.insets = inset;
		this.gbConstraints.weightx = weightX;
		this.gbConstraints.weighty = weightY;
		jComponent.setEnabled(isEnabled);
		//jComponent.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red));
		container.add(jComponent, gbConstraints);
	}
}
