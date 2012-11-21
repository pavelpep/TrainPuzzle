package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;

public class AndVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public AndVictoryCondition() {
		this.setName("And");
		userObject = new TreeNodeUserObject(this,"Complete all objectives");
		DefaultMutableTreeNode displayNode = new DefaultMutableTreeNode(userObject);
		this.setDisplayNode(displayNode);
	}
	
	private void checkChildrenSatisfied() {
		conditionSatisfied = true;
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				conditionSatisfied = false;
			}
		}
		if(conditionSatisfied) {
			userObject.setLabel(userObject.getLabel() + " Clear!");
			//this.getDisplayNode().setUserObject(this.getName() + " Clear!");
			getTreeModel().nodeChanged(getDisplayNode());
		}
	}

	@Override
	public boolean isSatisfied() {
		checkChildrenSatisfied();
		return conditionSatisfied;
	}	
}