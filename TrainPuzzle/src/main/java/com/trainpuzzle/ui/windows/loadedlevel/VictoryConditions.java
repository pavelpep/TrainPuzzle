package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.trainpuzzle.controller.GameController;


public class VictoryConditions extends JPanel implements TreeSelectionListener{
	private static final long serialVersionUID = 1L;
	
	private GameController gameController;
	private JTree tree;
	
	
	public VictoryConditions(GameController gameController) {
		super(new GridLayout(1,0));
		
		this.gameController = gameController;
        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("All Objectives");
        createNodes(top);
 
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

 
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode objective = null;
       
        //Category 1
        category = new DefaultMutableTreeNode("ALL OF THESE (AND)");
        top.add(category);
 
        //Objective 1
        objective = new DefaultMutableTreeNode(new ObjectiveInfo
            ("Pass Green station",
            "Pass the Green station at x,y"));
        category.add(objective);
        //Objective 2
        objective = new DefaultMutableTreeNode(new ObjectiveInfo
             ("Pass Red station",
              "Pass the Red station at x,y"));
        category.add(objective);
        
        //Category 2
        category = new DefaultMutableTreeNode("ANY OF THESE (OR)");
        top.add(category);
 
        //Objective 1
        objective = new DefaultMutableTreeNode(new ObjectiveInfo
            ("Use all available tracks",
             "Use all available tracks"));
        category.add(objective);
        
        //Objective 2
        objective = new DefaultMutableTreeNode(new ObjectiveInfo
                ("Do not crash the train",
                 "Do not crash the train"));
        category.add(objective);
 
    }



}
