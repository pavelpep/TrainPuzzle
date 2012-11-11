package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import com.trainpuzzle.controller.GameController;

/* The purpose of this abstract class (i.e. superclass) is to put the common method name Create() 
 * into one class, which is used in common by other subclasses that create Windows.
 */

abstract class Window extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	protected static final int DEFAULT_WIDTH = 1028;
	protected static final int DEFAULT_HEIGHT = 768;
	
	
	Window() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				WindowManager.getManager().showPreviousWindow();
        	}
		});
		setVisible(true);
	}
	
	protected void initializeComponent(JComponent jComponent, int fontSize) {
		Font defaultFont = new Font("Arial", Font.CENTER_BASELINE, fontSize);
		jComponent.setFont(defaultFont);
		jComponent.setBackground(this.getBackground());
		jComponent.setAlignmentX(CENTER_ALIGNMENT);
		//jComponent.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	protected JButton initializeButton(String label, String actionCommand) {
		JButton button = new JButton(label);
		button.setActionCommand(actionCommand);
		button.addActionListener(this);
		return button;
	}
	
	protected GridBagConstraints gbConstraints(Point location, Dimension size, float weightX, float weightY) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = (int) location.getX();
		gridBagConstraints.gridy = (int) location.getY();
		gridBagConstraints.gridwidth = (int) size.getWidth();
		gridBagConstraints.gridheight = (int) size.getHeight();
		gridBagConstraints.weightx = weightX;
		gridBagConstraints.weighty = weightY;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		return gridBagConstraints;
	}
	
}
