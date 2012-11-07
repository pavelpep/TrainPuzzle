package com.trainpuzzle.ui.windows;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.model.level.CampaignLevel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;


// Level selection for the campaign
public class LevelSelect extends Window implements ActionListener, ListSelectionListener {
	private GameController gameController;
	
	
	private int levelSelected = 1;
	DefaultListModel listModel;
	JList levelList;
	
	
	public LevelSelect(GameController gameController) {
		this.gameController = gameController;
		//setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		create();
		pack();
		setLocationRelativeTo(null);
	}
	
	public void create() {
		JPanel levelSelectPanel = new JPanel();
		levelSelectPanel.setLayout(new BoxLayout(levelSelectPanel, BoxLayout.Y_AXIS));
		levelSelectPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		this.add(levelSelectPanel);
		
		JLabel titleLabel = new JLabel("Level Select");
		initializeComponent(titleLabel, 28);
		levelSelectPanel.add(titleLabel);
		
		listModel = new DefaultListModel();
		levelList = new JList(listModel);
		
		
		levelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		levelList.addListSelectionListener(this);
		levelList.setVisibleRowCount(8);
		for(CampaignLevel campaignLevel: gameController.getLevelManager().getCampaign().getCampaignLevels()){
			listModel.addElement("Level " + campaignLevel.levelNumber);
		}
		initializeComponent(levelList, 15);
		levelSelectPanel.add(levelList);
		
		
		// Start Level Button
		
		JButton startLevelButton = initializeButton("Start Level","START_LEVEL");
		initializeComponent(startLevelButton, 20);
		startLevelButton.setBackground(Color.GREEN);
		levelSelectPanel.add(startLevelButton);
		
		
		// Load Button
		JButton loadButton = initializeButton("Load Level","LOAD");
		initializeComponent(loadButton, 20);
		loadButton.setBackground(Color.GREEN);
		levelSelectPanel.add(loadButton);
		
		JButton backButton = initializeButton("Back","back");
		initializeComponent(backButton, 20);
		backButton.setBackground(Color.LIGHT_GRAY);
		levelSelectPanel.add(backButton);

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
		levelSelected = 1 + levelList.getSelectedIndex();
		System.out.println("level selected: " + levelSelected);
		
	}
}