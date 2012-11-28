package com.trainpuzzle.ui.windows;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Credits extends Window implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Credits() {
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		create();
		reload();
	}
	
	protected void create() {	    
		JPanel creditsPanel = new JPanel();
		creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.Y_AXIS));
		
		JLabel titleLabel = new JLabel("Train Track Puzzle Game - Team Omega");
		initializeComponent(titleLabel, 28);
		creditsPanel.add(titleLabel);
		
		List<String> list = Arrays.asList("Joey Au-Yeung", "Jesse Chahal", "James Deng", "Frank Guo", "Newman Lai", "Pavel Pepeldjiys​ki", "Ronald Bow", "JJ", "Shanna Walters");    

		for(String name : list){
			JLabel nameLabel = new JLabel(name);
			initializeComponent(nameLabel, 16);
			creditsPanel.add(nameLabel);
		}
		
		JButton backButton = initializeButton("Back","back");
		initializeComponent(backButton, 20);
		backButton.setBackground(Color.LIGHT_GRAY);
		creditsPanel.add(backButton);
		
		
		this.add(creditsPanel);
	}
	
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action == "back") {
			WindowManager.getManager().showPreviousWindow();
		}
	}

	@Override
	protected void reload() {
		validate();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}	
}
	
