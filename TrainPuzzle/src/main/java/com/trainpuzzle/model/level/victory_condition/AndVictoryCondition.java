package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;

public class AndVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public AndVictoryCondition() {
		this.setName("And");
		 DefaultMutableTreeNode displayNode = new  DefaultMutableTreeNode("And");
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
			this.getDisplayNode().setUserObject(this.getName() + " Clear!");
		}
	}

	@Override
	public boolean isSatisfied() {
		checkChildrenSatisfied();
		return conditionSatisfied;
	}	
}