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
	Campaign campaign;
	private int campaignSelected = 1;
	private JList campaignList;
	private int resetOptionPane = JOptionPane.YES_OPTION;
	
	public Campaigns(GameController gameController) {
		this.gameController = gameController;
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

		JLabel titleLabel = new JLabel("Choose Campaign");
		initializeComponent(titleLabel, 20);
		campaignsPanel.add(titleLabel);
		
		DefaultListModel listModel = new DefaultListModel();
		campaignList = new JList(listModel);
		campaignList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		campaignList.addListSelectionListener(this);
		campaignList.setVisibleRowCount(5);
		
		for(Campaign campaign: gameController.getCampaignManager().getCampaigns()){
			listModel.addElement(campaign.getName());
		}
		initializeComponent(campaignList, 15);
		campaignsPanel.add(campaignList);
		
		JScrollPane listScrollPane = new JScrollPane(campaignList);
		initializeComponent(listScrollPane, 15);
		campaignsPanel.add(listScrollPane);
		
		JButton selectCampaign = initializeButton("Select Campaign","selectCampaign");
		initializeComponent(selectCampaign, 15);
		campaignsPanel.add(selectCampaign);
		
		JButton resetCampaign = initializeButton("Reset Campaign","resetCampaign");
		initializeComponent(resetCampaign, 15);
		campaignsPanel.add(resetCampaign);
		
		
		JButton backButton = initializeButton("Back","back");
		initializeComponent(backButton, 15);
		campaignsPanel.add(backButton);
		
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action == "selectCampaign") {
			gameController.changeCampaign(campaignSelected);
			WindowManager.getManager().setActiveWindow(new LevelSelect(gameController)); 
		} else if (action == "resetCampaign") {
			resetOptionPane = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset Campaign" + campaignSelected + "?", "Reset Campaign", JOptionPane.YES_NO_OPTION);
			if (resetOptionPane == JOptionPane.YES_OPTION) {
				// NEED TO CHANGE THE CAMPAIGN XML FILE ITSELF IN ORDER TO RESET!
				gameController.resetCampaign(campaignList.getSelectedIndex());
				JOptionPane.showMessageDialog(null, "Campaign reset successful");
			} else if (resetOptionPane == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(null, "Campaign not reseted");
			}
		} else if (action == "back") {
			WindowManager.getManager().showPreviousWindow();
		}
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
		campaignSelected = 1 + campaignList.getSelectedIndex();		
		
	}	
}