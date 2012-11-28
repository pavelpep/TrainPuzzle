package com.trainpuzzle.ui.windows;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.model.level.CampaignLevel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LevelSelect extends Window implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private GameController gameController;
	private int levelSelected = 1;
	
	JPanel levelSelectPanel;
	JList levelList;
	
	public LevelSelect(GameController gameController) {
		this.gameController = gameController;
		levelSelectPanel = new JPanel();
		levelSelectPanel.setLayout(new BoxLayout(levelSelectPanel, BoxLayout.Y_AXIS));
		levelSelectPanel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		this.add(levelSelectPanel);
		create();
		reload();
	}
	
	protected void create() {
		
		levelSelectPanel.removeAll();
		
		JLabel titleLabel = new JLabel("Level Select");
		initializeComponent(titleLabel, 28);
		levelSelectPanel.add(titleLabel);
		
		DefaultListModel listModel = new DefaultListModel();
		levelList = new JList(listModel);
		levelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		levelList.addListSelectionListener(this);
		levelList.setVisibleRowCount(8);
		
		for(CampaignLevel campaignLevel: gameController.getLevelManager().getLevels()) {
			String levelName = "Level " + campaignLevel.levelNumber;
			String levelState = (campaignLevel.isLocked) ? "locked" : "unlocked";
			levelState += (campaignLevel.hasUserSave) ? ", saved" : "";
			listModel.addElement(levelName + " (" + levelState +")");
		}
		
		initializeComponent(levelList, 15);
		levelSelectPanel.add(levelList);
		levelList.setSelectedIndex(gameController.getLevelManager().getCurrentLevelNumber());
		
		JButton startLevelButton = initializeButton("Start Level","START_LEVEL");
		initializeComponent(startLevelButton, 20);
		startLevelButton.setBackground(Color.GREEN);
		levelSelectPanel.add(startLevelButton);
		
		JButton backButton = initializeButton("Back","back");
		initializeComponent(backButton, 20);
		backButton.setBackground(Color.LIGHT_GRAY);
		levelSelectPanel.add(backButton);
	
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action == "back") {
			WindowManager.getManager().showPreviousWindow();
		} 
		else if (action == "START_LEVEL") {
			if(gameController.getLevelManager().getLevels().get(levelSelected-1).isLocked){
				JOptionPane.showMessageDialog(this,"Cannot start a level that is locked!",
						"NO!", JOptionPane.ERROR_MESSAGE, null);
			}else{
				gameController.startGame(levelSelected);
				WindowManager.getManager().setActiveWindow(new LoadedLevelScreen(gameController));
			}	
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		levelSelected = 1 + levelList.getSelectedIndex();
	}

	@Override
	protected void reload() {
		levelList.setSelectedIndex(gameController.getLevelManager().getCurrentLevelNumber());
		
		validate();
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}	
}