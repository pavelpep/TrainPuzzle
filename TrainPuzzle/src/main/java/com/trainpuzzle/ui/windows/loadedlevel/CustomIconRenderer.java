package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Component;


import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.level.victory_condition.TreeNodeUserObject;

	
@SuppressWarnings("serial")
class CustomIconRenderer extends DefaultTreeCellRenderer {
	ImageIcon unsatisfisedIcon;
	ImageIcon satisfisedIcon;
	public CustomIconRenderer() {
		unsatisfisedIcon = Images.VC_UNSATISFIED_IMAGE;
		satisfisedIcon = Images.VC_SATISFIED_IMAGE;
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		DefaultMutableTreeNode node = ((DefaultMutableTreeNode)value);
		TreeNodeUserObject userObject = (TreeNodeUserObject)(node.getUserObject());
		
		if (userObject.getVictoryCondition().isSatisfied()) {
			setIcon(satisfisedIcon);
		} else {
			setIcon(unsatisfisedIcon);
		}
		
		setText(userObject.getLabel()); 
		//setText((String) node.getUserObject());
		return this;
	}
	
}