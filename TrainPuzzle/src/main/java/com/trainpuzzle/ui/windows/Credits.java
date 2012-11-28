package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.trainpuzzle.controller.LevelManager;
import com.trainpuzzle.controller.GameController;

public class Credits extends Window implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Credits() {
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		create();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void create() {	    
		JPanel creditsPanel = new JPanel();
		creditsPanel.setLayout(new BorderLayout());
		creditsPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		this.add(creditsPanel);
		
		JLabel titleLabel = new JLabel("Train Track Puzzle Game - Team Omega");
		initializeComponent(titleLabel, 28);
		creditsPanel.add(titleLabel, BorderLayout.PAGE_START);
		
		JLabel creditLabels = new JLabel("<html>");
		initializeComponent(creditLabels, 20);
		creditLabels.setText(creditLabels.getText() + "<br>Joey Au-Yeung");
		creditLabels.setText(creditLabels.getText() + "<br>Jesse Chahal");
		creditLabels.setText(creditLabels.getText() + "<br>James Deng");
		creditLabels.setText(creditLabels.getText() + "<br>Frank Guo");
		creditLabels.setText(creditLabels.getText() + "<br>Newman Lai");
		creditLabels.setText(creditLabels.getText() + "<br>Pavel Pepeldjiys​ki");
		creditLabels.setText(creditLabels.getText() + "<br>Ronald Bow");
		creditLabels.setText(creditLabels.getText() + "<br>JJ");
		creditLabels.setText(creditLabels.getText() + "<br>Shanna Walters<br>");
		creditsPanel.add(creditLabels, BorderLayout.CENTER);

	}
	
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action == "back") {
			WindowManager.getManager().showPreviousWindow();
		}
	}
}
	
