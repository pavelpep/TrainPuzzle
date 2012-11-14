package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.trainpuzzle.controller.LevelManager;
import com.trainpuzzle.controller.GameController;

public class MainMenu extends Window implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	GameController gameController;
	LevelManager campaignManager;
	
	public MainMenu(GameController gameController) {	
		this.gameController = gameController;
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		create();
		pack();
		setLocationRelativeTo(null);
	}
	
	public void create() {
		JPanel mainMenuPanel = new JPanel();
		mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));
		mainMenuPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		this.add(mainMenuPanel);
		
		JLabel menuLabel = new JLabel("Train Track Puzzle Game");
		initializeComponent(menuLabel, 28);
		mainMenuPanel.add(menuLabel);

		JButton continueButton = initializeButton("Continue Campaign","continue");
		initializeComponent(continueButton, 20);
		continueButton.setBackground(Color.ORANGE);
		mainMenuPanel.add(continueButton);

		JButton campaignsButton = initializeButton("Select Campaign","campaigns");
		initializeComponent(campaignsButton, 20);
		campaignsButton.setBackground(Color.ORANGE);
		mainMenuPanel.add(campaignsButton);

		JButton loadButton = initializeButton("Load Level","load");
		initializeComponent(loadButton, 20);
		loadButton.setBackground(Color.GREEN);
		mainMenuPanel.add(loadButton);

		JButton creditsButton = initializeButton("Credits","credits");
		initializeComponent(creditsButton, 20);
		creditsButton.setBackground(Color.ORANGE);
		mainMenuPanel.add(creditsButton);

		JButton exitButton = initializeButton("Exit","exit");
		initializeComponent(exitButton, 20);
		exitButton.setBackground(Color.ORANGE);
		mainMenuPanel.add(exitButton);		
	}
	
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		
		if (action == "continue") {
			WindowManager.getManager().setActiveWindow(new LevelSelect(gameController)); 
		} 
		else if (action == "campaigns") {
			WindowManager.getManager().setActiveWindow(new Campaigns(gameController)); 
		} 
		else if (action == "load") {
			File levelFile = openFile();
			if(levelFile != null) {	
				gameController.startGame(levelFile);
				LoadedLevelScreen loadedLevelScreen = new LoadedLevelScreen(gameController);
				WindowManager.getManager().setActiveWindow(loadedLevelScreen);
			}
	    } 
		else if (action == "exit") {
			System.exit(0);
		}
	}
	
	private File openFile() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Encoded Level", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
}