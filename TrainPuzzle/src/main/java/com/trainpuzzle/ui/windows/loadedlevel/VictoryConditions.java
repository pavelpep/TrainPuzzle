package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.net.URL;
import java.util.Enumeration;


import javax.jws.WebParam.Mode;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.trainpuzzle.controller.GameController;
import com.trainpuzzle.infrastructure.Images;

public class VictoryConditions extends JPanel {// implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private GameController gameController;
	private JTree tree;
	
	
	public VictoryConditions(GameController gameController) {
		super(new GridLayout(1,0));
		
		this.gameController = gameController;
		
        DefaultMutableTreeNode top = gameController.getLevel().getVictoryConditions().getDisplayNode();
 
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        
        gameController.getLevel().getVictoryConditions().setTreeModel(model);
        
 
        //Listen for when the selection changes.
        //tree.addTreeSelectionListener(this);
        
        JScrollPane treeView = new JScrollPane(tree);
        Dimension minimumSize = new Dimension(100, 50);
        treeView.setMinimumSize(minimumSize);
        add(treeView);
        

        CustomIconRenderer renderer = new CustomIconRenderer();
        tree.setCellRenderer(renderer);
        
        
        
        
    }
	
	public void update(){
		tree.updateUI();
		
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
 
    private class ObjectiveInfo {
        public String objectiveName;
        public String objectiveDescription;
 
        public ObjectiveInfo(String objective, String objectiveDescription) {
        	this.objectiveName = objective;
        	this.objectiveDescription = objectiveDescription;
            if (objectiveDescription == null) {
                System.err.println("Couldn't find description.");
            }
        }
 
        public String toString() {
            return objectiveName;
        }
    }


 
   
}