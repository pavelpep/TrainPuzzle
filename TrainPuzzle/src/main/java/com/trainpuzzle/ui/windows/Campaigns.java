package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.FileFilter;

import com.trainpuzzle.controller.CampaignManager;
import com.trainpuzzle.controller.GameController;

import java.util.*;

class Campaigns extends Window implements ActionListener, ListSelectionListener {
	private GameController gameController;
	CampaignManager campaignManager;
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements	
	private File newCampaignDirectory = null;	
	private File campaignsDirectory = new File("Campaigns/");	
	private String campaignName = null;
	private DefaultListModel listModel = new DefaultListModel();
	private JList campaignList = new JList(listModel);
	private JScrollPane listScrollPane = new JScrollPane(campaignList);
	
	
	
	private JLabel titleLabel = new JLabel();
	private JButton newCampaign = new JButton();
	private JButton loadCampaign = new JButton();
	private JButton deleteCampaign = new JButton();
	private JButton backButton = new JButton();
	
	// Constructor
	public Campaigns(GameController gameController) {
		this.gameController = gameController;
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		create();
	}
	
	public Campaigns(GameController gameController, CampaignManager campaignManager) {
		this.gameController = gameController;
		this.campaignManager = campaignManager;
		
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		create();
	}
	
	public void create() {
		// Title
		addComponent(this, this.titleLabel, Font.CENTER_BASELINE, 20, Color.LIGHT_GRAY, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 10), true);
		this.titleLabel.setText("Choose Campaign");
		
		addComponent(this, this.listScrollPane, Font.CENTER_BASELINE, 15, Color.LIGHT_GRAY, 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), true);
		campaignList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		campaignList.addListSelectionListener(this);
		campaignList.setVisibleRowCount(8);		
		
		addComponent(this, this.newCampaign, Font.CENTER_BASELINE, 15, Color.LIGHT_GRAY, 0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), true);
		this.newCampaign.setText("New Campaign");
		newCampaign.setActionCommand("newCampaign");
		newCampaign.addActionListener(this);
		
		addComponent(this, this.loadCampaign, Font.CENTER_BASELINE, 15, Color.LIGHT_GRAY, 0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), true);
		this.loadCampaign.setText("Load Campaign");
		loadCampaign.setActionCommand("loadCampaign");
		loadCampaign.addActionListener(this);
		
		addComponent(this, this.deleteCampaign, Font.CENTER_BASELINE, 15, Color.LIGHT_GRAY, 0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), true);
		this.deleteCampaign.setText("Delete Campaign");
		deleteCampaign.setActionCommand("deleteCampaign");
		deleteCampaign.addActionListener(this);
		
		addComponent(this, this.backButton, Font.CENTER_BASELINE, 15, Color.LIGHT_GRAY, 0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(30, 0, 0, 0), true);
		this.backButton.setText("Back");
		backButton.setActionCommand("back");
		backButton.addActionListener(this);
		
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