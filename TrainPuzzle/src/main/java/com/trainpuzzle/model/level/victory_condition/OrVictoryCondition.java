package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;

public class OrVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public OrVictoryCondition() {
		this.setName("OR");
		DefaultMutableTreeNode displayNode = new  DefaultMutableTreeNode("Or");
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
			this.getDisplayNode().setUserObject(this.getName() + " Clear!");
		}
	}

	@Override
	public boolean isSatisfied() {
		checkChildrenSatisfied();
		return conditionSatisfied;
	}
}