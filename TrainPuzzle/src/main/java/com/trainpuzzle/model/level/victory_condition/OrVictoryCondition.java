package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;

public class OrVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public OrVictoryCondition() {
		this.setName("OR");
		userObject = new TreeNodeUserObject(this,"Optional objectives");
		DefaultMutableTreeNode displayNode = new DefaultMutableTreeNode(userObject);
		this.setDisplayNode(displayNode);
	}
	
	private void checkChildrenSatisfied() {
		for(VictoryCondition child : this.getChildren()) {
			if(child.isSatisfied()) {
				conditionSatisfied = true;
				break;
			}
		}
		if(conditionSatisfied) {
			userObject.setLabel(userObject.getLabel() + " Clear!");
			getTreeModel().nodeChanged(getDisplayNode());
		}
	}

	@Override
	public boolean isSatisfied() {
		checkChildrenSatisfied();
		return conditionSatisfied;
	}
}