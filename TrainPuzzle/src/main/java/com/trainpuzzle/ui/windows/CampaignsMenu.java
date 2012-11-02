package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.trainpuzzle.controller.CampaignManager;
import com.trainpuzzle.controller.GameController;

import java.util.*;

class CampaignsMenu extends Window implements ActionListener, ListSelectionListener {
	private GameController gameController;
	CampaignManager campaignManager;
	
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements	
	private JList profileList = null;
	private DefaultListModel listModel = null;
	private String profileName = null;
	
	private JLabel title = null;
	private JButton newProfile = null;
	private JButton loadProfile = null;
	private JButton backButton = null;
	private JButton deleteProfile = null;
		
	// Constructor
	public CampaignsMenu(GameController gameController) {
		this.gameController = gameController;
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		listModel = new DefaultListModel();
	}
	
	public CampaignsMenu(GameController gameController, CampaignManager campaignManager) {
		this.gameController = gameController;
		this.campaignManager = campaignManager;
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		listModel = new DefaultListModel();
	}

	public void create() {
		// Title
		title = new JLabel("Choose Campaign");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 10, 0, 10);
		this.add(title, c);
		
		profileList = new JList(listModel);
		profileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		profileList.addListSelectionListener(this);
		profileList.setVisibleRowCount(5);		
		JScrollPane listScrollPane = new JScrollPane(profileList);
		
		c.gridx = 0;
		c.gridy = 1;
		c.ipadx = 50;
		c.fill = GridBagConstraints.HORIZONTAL;
		//this.add(profileList, c);
		this.add(listScrollPane, c);
		
		loadProfile = new JButton("Load Campaign");
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(loadProfile, c);
		
		newProfile = new JButton("New Campaign");
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(newProfile, c);
		
		deleteProfile = new JButton("Delete Campaign");
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(deleteProfile, c);
		
		backButton = new JButton("Back");
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(30, 0, 0, 0);
		this.add(backButton, c);		
		
		// ActionListeners for window elements
		loadProfile.setActionCommand("loadProfile");
		loadProfile.addActionListener(this);
		
		deleteProfile.setActionCommand("deleteProfile");
		deleteProfile.addActionListener(this);
		
		newProfile.setActionCommand("newProfile");
		newProfile.addActionListener(this);
		
		backButton.setActionCommand("back");
		backButton.addActionListener(this);
		
		this.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "newProfile") {
			profileName = JOptionPane.showInputDialog("Please enter your name:");
			if (profileName == null || profileName.isEmpty() || !profileName.matches(".*\\w.*")) {
			JOptionPane.showMessageDialog(null, "You must enter a name");
			} 
			else {
			listModel.addElement(profileName);
			}
		}
	
		if (e.getActionCommand() == "loadProfile") {
			profileName = (String) profileList.getSelectedValue();	
		}
		
		if (e.getActionCommand() == "deleteProfile") {
			profileName = (String) profileList.getSelectedValue();
			listModel.removeElement(profileName);
			
		}
		
		if (e.getActionCommand() == "back") {
			WindowManager.getManager(gameController).showPreviousWindow(); 
		}
		
	}

	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
	
	
}