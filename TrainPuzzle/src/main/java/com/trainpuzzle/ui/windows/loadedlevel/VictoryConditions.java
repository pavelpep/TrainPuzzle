package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeModel;

import com.trainpuzzle.controller.GameController;


public class VictoryConditions extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTree tree;
	
	
	public VictoryConditions(GameController gameController) {
		super(new GridLayout(1,0));
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder victoryConditionsTitle;
		victoryConditionsTitle = BorderFactory.createTitledBorder(loweredetched, "Victory Conditions");
		victoryConditionsTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(victoryConditionsTitle);
		
		DefaultTreeModel treeModel = gameController.getLevel().getVictoryConditions().getTreeModel();
		tree = new JTree(treeModel);
		
		CustomIconRenderer renderer = new CustomIconRenderer();
		tree.setCellRenderer(renderer);
		
		JScrollPane treeView = new JScrollPane(tree);
		this.add(treeView);
	}
   
}