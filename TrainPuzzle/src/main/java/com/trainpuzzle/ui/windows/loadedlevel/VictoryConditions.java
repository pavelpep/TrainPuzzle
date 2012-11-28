package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Dimension;

import java.awt.GridLayout;



import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.trainpuzzle.controller.GameController;


public class VictoryConditions extends JPanel {// implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private GameController gameController;
	private JTree tree;
	
	
	public VictoryConditions(GameController gameController) {
		super(new GridLayout(1,0));
		
		this.gameController = gameController;
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder victoryConditionsTitle;
		victoryConditionsTitle = BorderFactory.createTitledBorder(loweredetched, "Victory Conditions");
		victoryConditionsTitle.setTitlePosition(TitledBorder.ABOVE_TOP);
		this.setBorder(victoryConditionsTitle);
		
        DefaultTreeModel treeModel = gameController.getLevel().getVictoryConditions().getTreeModel();
        tree = new JTree(treeModel);
        
        
        CustomIconRenderer renderer = new CustomIconRenderer();
        tree.setCellRenderer(renderer);

        //tree.addTreeSelectionListener(this);
        
        JScrollPane treeView = new JScrollPane(tree);
        Dimension minimumSize = new Dimension(100, 50);
        treeView.setMinimumSize(minimumSize);
        add(treeView);

        
	}
	
 
    /** Required by TreeSelectionListener interface. */
	/*
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
 
        if (node == null) return;
 
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            ObjectiveInfo objective = (ObjectiveInfo)nodeInfo;
            System.out.println(objective.objectiveDescription);

        } else {
            System.out.println("Not a leaf");
        }

    }
    */
   
}