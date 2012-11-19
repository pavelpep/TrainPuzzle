package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.net.URL;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import com.trainpuzzle.controller.GameController;

public class VictoryConditions extends JPanel implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private GameController gameController;
	private JTree tree;
	
	
	public VictoryConditions(GameController gameController) {
		super(new GridLayout(1,0));
		
		this.gameController = gameController;
        //Create the nodes.
        DefaultMutableTreeNode top =
            gameController.getLevel().getVictoryConditions().getDisplayNode();
 
        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
 
        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

 
        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);
        Dimension minimumSize = new Dimension(100, 50);
        treeView.setMinimumSize(minimumSize);
        add(treeView);
    }
 
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();
 
        if (node == null) return;
 
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            ObjectiveInfo objective = (ObjectiveInfo)nodeInfo;
            System.out.println(objective.objectiveDescription);

        } else {
            System.out.println("Not a leaf");
        }

    }
 
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