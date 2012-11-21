package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Component;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.level.victory_condition.TreeNodeUserObject;
import com.trainpuzzle.model.level.victory_condition.VictoryCondition;
	
class CustomIconRenderer extends DefaultTreeCellRenderer {
	ImageIcon unsatisfisedIcon;
	ImageIcon satisfisedIcon;
	public CustomIconRenderer() {
		unsatisfisedIcon = Images.VC_UNSATISFIED_IMAGE;
		satisfisedIcon = Images.VC_SATISFIED_IMAGE;
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode nodeObj = ((DefaultMutableTreeNode)value);
		TreeNodeUserObject userObject = (TreeNodeUserObject)(nodeObj.getUserObject());
		if (userObject.getVictoryCondition().isSatisfied()) {
			setIcon(satisfisedIcon);
		} else {
			setIcon(unsatisfisedIcon);
		} 
		
		setText(userObject.getLabel());
		return this;
	}
	
}