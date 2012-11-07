package com.trainpuzzle.ui.windows;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.model.level.CampaignLevel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;


// Level selection for the campaign
public class LevelSelect extends Window implements ActionListener, ListSelectionListener {
	private GameController gameController;
	
	
	int levelSelected = 0;
	
	public LevelSelect(GameController gameController) {
		this.gameController = gameController;
		gbConstraints = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	
		create();
	}
	
	public void create() {	    
		
		// Title
		JLabel titleLabel = new JLabel();
		addComponent(this, titleLabel, Font.CENTER_BASELINE, 20, Color.LIGHT_GRAY, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		titleLabel.setText("Choose Campaign");
		
		DefaultListModel listModel = new DefaultListModel();
		JList campaignList = new JList(listModel);
		JScrollPane listScrollPane = new JScrollPane(campaignList);
		
		addComponent(this, listScrollPane, Font.CENTER_BASELINE, 15, Color.LIGHT_GRAY, 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), true);
		campaignList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		campaignList.addListSelectionListener(this);
		campaignList.setVisibleRowCount(8);
		for(CampaignLevel campaignLevel: gameController.getCampaignManager().getCampaign().getCampaignLevels()){
			listModel.addElement("Level " + campaignLevel.levelNumber);
		}
		
		
		// Start Level Button
	    JButton startLevelButton = new JButton();
		addComponent(this, startLevelButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		startLevelButton.setText("Start Level");
		startLevelButton.setActionCommand("START_LEVEL");
		startLevelButton.addActionListener(this);
		
		
		// Load Button
		JButton loadButton = new JButton();
		addComponent(this, loadButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.GREEN, 0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 10, 0), true);
		loadButton.setText("Load Level");
		loadButton.setActionCommand("LOAD");
		loadButton.addActionListener(this);
		
		// Back button
		JButton backButton = new JButton();
		addComponent(this, backButton, Font.LAYOUT_LEFT_TO_RIGHT, 20, Color.LIGHT_GRAY, 0, 6, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(50, 0, 10, 0), true);
		backButton.setText("Back");
		backButton.setActionCommand("back");
		backButton.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action == "back") {
				WindowManager.getManager(gameController).showPreviousWindow();
		} else if (action == "START_LEVEL") {
			gameController.startGame(levelSelected);
			WindowManager.getManager(gameController).setActiveWindow(new LoadedLevelScreen(gameController));
		}		

		 else if (action == "LOAD") {
			File levelFile = openFile();
			if(levelFile != null){	
				gameController.startGame(levelFile);
				LoadedLevelScreen loadedLevelScreen = new LoadedLevelScreen(gameController);
				WindowManager.getManager(gameController).setActiveWindow(loadedLevelScreen);
			}
		}	
	}
	
	
	private File openFile(){
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "XML Encoded Level", "xml");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile();
	    }
		return null;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		levelSelected = 1 + arg0.getFirstIndex();
		
	}
}