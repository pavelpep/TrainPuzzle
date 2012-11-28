package com.trainpuzzle.ui.windows.loadedlevel;

import java.awt.Component;


import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.trainpuzzle.infrastructure.Images;
import com.trainpuzzle.model.level.victory_condition.TreeNodeUserObject;

	
@SuppressWarnings("serial")
class CustomIconRenderer extends DefaultTreeCellRenderer {
	private static final ImageIcon UNSATISFIED = Images.VC_UNSATISFIED_IMAGE;
	private static final ImageIcon SATISFIED = Images.VC_SATISFIED_IMAGE;
	public CustomIconRenderer() {
	}
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		DefaultMutableTreeNode node = ((DefaultMutableTreeNode)value);
		TreeNodeUserObject userObject = (TreeNodeUserObject)(node.getUserObject());
		
		if (userObject.getVictoryCondition().isSatisfied()) {
			setIcon(SATISFIED);
		} else {
			setIcon(UNSATISFIED);
		}
		
		setText(userObject.getLabel()); 
		return this;
	}
	
}