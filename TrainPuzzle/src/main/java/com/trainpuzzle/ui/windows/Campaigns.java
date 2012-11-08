package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import com.trainpuzzle.controller.LevelManager;
import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.model.level.Campaign;


class Campaigns extends Window implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	
	private GameController gameController;
	LevelManager campaignManager;
	// Layout Manager

	
	// Window elements	
	private int campaignSelected = 1;
	private JList<String> campaignList;
	
	
	
	public Campaigns(GameController gameController) {
		this.gameController = gameController;
		//setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		create();
		pack();
		setLocationRelativeTo(null);
	}
	
	
	public Campaigns(GameController gameController, LevelManager campaignManager) {
		this.gameController = gameController;
		this.campaignManager = campaignManager;
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		create();
	}
	
	public void create() {
		JPanel campaignsPanel = new JPanel();
		campaignsPanel.setLayout(new BoxLayout(campaignsPanel, BoxLayout.Y_AXIS));
		campaignsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(campaignsPanel);
		
		//Title Label
		JLabel titleLabel = new JLabel("Choose Campaign");
		initializeComponent(titleLabel, 20);
		campaignsPanel.add(titleLabel);
		
		//Campaigns List
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		campaignList = new JList<String>(listModel);
		campaignList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		campaignList.addListSelectionListener(this);
		campaignList.setVisibleRowCount(8);
		for(Campaign campaign: gameController.getCampaignManager().getCampaigns()){
			listModel.addElement(campaign.getCampaignName());
		}
		initializeComponent(campaignList, 15);
		campaignsPanel.add(campaignList);

		//Select Campaign Button
		JButton selectCampaign = initializeButton("Select Campaign","selectCampaign");
		initializeComponent(selectCampaign, 15);
		campaignsPanel.add(selectCampaign);
		
		//Back Button
		JButton backButton = initializeButton("Back","back");
		initializeComponent(backButton, 15);
		campaignsPanel.add(backButton);
		
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		

	    if (action == "selectCampaign") {
			gameController.changeCampaign(campaignSelected);
		} else if (action == "back") {
			WindowManager.getManager(gameController).showPreviousWindow();
		}
	}

	public void valueChanged(ListSelectionEvent arg0) {
		campaignSelected = 1 + campaignList.getSelectedIndex();
		System.out.println("campaign selected: " + campaignSelected);
		
	}	
}