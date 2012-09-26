package com.trainpuzzle.ui.windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;

class Profiles extends Window implements ActionListener, ListSelectionListener {
	// Layout Manager
	private GridBagConstraints c;
	
	// Window elements	
	private JList profileList;
	private DefaultListModel listModel;
	
	private JLabel title;
	private JButton newProfile;
	private JButton loadProfile;
	private JButton back;
		
	// Constructor
	public Profiles() {
		profileList = null;
		listModel = null;
		title = null;
		newProfile = null;
		loadProfile = null;
		back = null;
		
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(new Dimension(640,480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		listModel = new DefaultListModel();
		listModel.addElement("Player1");
		listModel.addElement("Player2");
	}

	public void Create() {
		// Title
		title = new JLabel("Choose Profile");
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
		
		loadProfile = new JButton("Load Profile");
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(loadProfile, c);
		
		newProfile = new JButton("New Profile");
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(newProfile, c);
		
		back = new JButton("Back");
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(30, 0, 0, 0);
		this.add(back, c);		
		
				// ActionListeners for window elements
		back.setActionCommand("back");
		back.addActionListener(this);
		
		this.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "back") {
			WindowManager.getManager().setActiveWindow(new MainMenu()); 
			WindowManager.getManager().updateWindows();
		}
		
	}

	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
	
	
}