package com.trainpuzzle.ui.windows.loadedlevel;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import com.trainpuzzle.controller.GameController;

public class VictoryConditions extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private GameController gameController;
	private JTree tree;
	
	public VictoryConditions(GameController gameController) {
		this.gameController = gameController;
		
		this.setPreferredSize(new Dimension(200, 150));
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder gameControlBoxTitle;
		gameControlBoxTitle = BorderFactory.createTitledBorder(loweredetched, "Game Controls");
		gameControlBoxTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(gameControlBoxTitle);
		
		JPanel victoryConditionPanel = new JPanel();
		
		
	    DefaultMutableTreeNode top =
	        new DefaultMutableTreeNode("The Java Series");
	    //createNodes(top);
	    tree = new JTree(top);
	    JScrollPane treeView = new JScrollPane(tree);
	    
	    victoryConditionPanel.add(treeView);
		
	}
	

	public void actionPerformed(ActionEvent event) {
		
	}
	
}
