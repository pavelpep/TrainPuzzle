package com.trainpuzzle.ui.windows;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.ui.windows.loadedlevel.VictoryConditions;


class VictoryConditionsWindow extends Window implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private GameController gameController;

	public VictoryConditionsWindow(GameController gameController) {
		this.gameController = gameController;
		create();
		pack();
		setLocationRelativeTo(null);
	}	
		
	public void create() {
		JPanel campaignsPanel = new JPanel();
		campaignsPanel.setLayout(new BoxLayout(campaignsPanel, BoxLayout.Y_AXIS));
		campaignsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(campaignsPanel);
		
		
		JPanel victoryConditionsPanel = new VictoryConditions(gameController);
		campaignsPanel.add(victoryConditionsPanel);
		
		
		JButton backButton = initializeButton("Back","back");
		initializeComponent(backButton, 15);
		campaignsPanel.add(backButton);
		
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();

		if (action == "back") {
			WindowManager.getManager().showPreviousWindow();
		}
	}
	
}