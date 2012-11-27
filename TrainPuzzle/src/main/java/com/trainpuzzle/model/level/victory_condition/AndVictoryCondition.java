package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class AndVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public AndVictoryCondition() {
		userObject = new TreeNodeUserObject(this,"Complete all objectives");
		DefaultMutableTreeNode displayNode = new DefaultMutableTreeNode(userObject);
		this.setDisplayNode(displayNode);
		setTreeModel(new DefaultTreeModel(displayNode));
	}
	
	protected boolean checkChildrenSatisfied() {
		boolean satisfied =true;
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				satisfied = false;
				break;
			}
		}
		return satisfied;
	}
	
}