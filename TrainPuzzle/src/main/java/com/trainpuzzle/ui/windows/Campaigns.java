package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.io.File;
import java.io.FileFilter;

import com.trainpuzzle.controller.LevelManager;
import com.trainpuzzle.controller.GameController;

import java.util.*;

class Campaigns extends Window implements ActionListener, ListSelectionListener {
	private GameController gameController;
	LevelManager campaignManager;
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements	
	private File newCampaignDirectory = null;	
	private File campaignsDirectory = new File("Campaigns/");	
	private String campaignName = null;
	private DefaultListModel listModel = new DefaultListModel();
	private JList campaignList = new JList(listModel);
	
	
	
	public Campaigns(GameController gameController) {
		this.gameController = gameController;
		c = new GridBagConstraints();
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		create();
	}
	
	
	public Campaigns(GameController gameController, LevelManager campaignManager) {
		this.gameController = gameController;
		this.campaignManager = campaignManager;
		c = new GridBagConstraints();
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
		
		campaignList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		campaignList.addListSelectionListener(this);
		campaignList.setVisibleRowCount(8);
		
		JScrollPane listScrollPane = new JScrollPane(campaignList);
		initializeComponent(listScrollPane, 15);
		campaignsPanel.add(listScrollPane);

		JButton newCampaign = initializeButton("New Campaign","newCampaign");
		initializeComponent(newCampaign, 15);
		campaignsPanel.add(newCampaign);
		
		JButton loadCampaign = initializeButton("Load Campaign","loadCampaign");
		initializeComponent(loadCampaign, 15);
		campaignsPanel.add(loadCampaign);
		
		JButton deleteCampaign = initializeButton("Delete Campaign","deleteCampaign");
		initializeComponent(deleteCampaign, 15);
		campaignsPanel.add(deleteCampaign);
		
		JButton backButton = initializeButton("Back","back");
		initializeComponent(backButton, 15);
		campaignsPanel.add(backButton);
		
		// The following enumerates the previously stored campaigns on disk
		campaignsDirectory.mkdirs();
		File[] listCampaignDirectory = campaignsDirectory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
		
		for (File campaignFolder : listCampaignDirectory) {
			listModel.addElement(campaignFolder.getName());
		}
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		
		if (action == "newCampaign") {			
			campaignName = JOptionPane.showInputDialog("Please enter your name:");	
			newCampaignDirectory = new File("Campaigns/" + campaignName);
			if (campaignName == null || campaignName.isEmpty() || !campaignName.matches(".*\\w.*")) {
				JOptionPane.showMessageDialog(null, "You must enter a name");
			} else {
				if(newCampaignDirectory.mkdirs()) {					
					JOptionPane.showMessageDialog(null, "Campaign creation successful");
					listModel.addElement(campaignName);
				} else {
					JOptionPane.showMessageDialog(null, "Campaign creation unsuccessful. Campaign already exists");
				}
			}
			
		} else if (action == "loadCampaign") {
			campaignName = (String) campaignList.getSelectedValue();	
		} else if (action == "deleteCampaign") {
			campaignName = (String) campaignList.getSelectedValue();
			listModel.removeElement(campaignName);
		} else if (action == "back") {
			WindowManager.getManager(gameController).showPreviousWindow();
		}
	}

	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}