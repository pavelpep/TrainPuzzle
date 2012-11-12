package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


abstract class Window extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	protected static final int DEFAULT_WIDTH = 1028;
	protected static final int DEFAULT_HEIGHT = 768;
	
	public Window() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				String exit = "Exit";
				String previousWindow = "Previous Window";
				String cancel = "Cancel";
				
				String[] options;
				if(WindowManager.getManager().hasPreviousWindow()) {
					options= new String[]{exit, previousWindow, cancel};
				}
				else {
					options= new String[]{exit, cancel};
				}
				
				int choice = JOptionPane.showOptionDialog(
					null, 
					"", 
					"Exit Confirmation", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.PLAIN_MESSAGE, 
					null, 
					options, 
					options[0]);
				
				if(options[choice].equals(exit)){
					WindowManager.getManager().exit();
				}
				if(options[choice].equals(previousWindow)){
					WindowManager.getManager().showPreviousWindow();
				}
				if(options[choice].equals(cancel)){
					
				}
				
				
				
			}
		});
		setVisible(true);
	}
	
	protected void initializeComponent(JComponent jComponent, int fontSize) {
		Font defaultFont = new Font("Arial", Font.CENTER_BASELINE, fontSize);
		jComponent.setFont(defaultFont);
		jComponent.setBackground(this.getBackground());
		jComponent.setAlignmentX(CENTER_ALIGNMENT);
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
