package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;

public class OrVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public OrVictoryCondition() {
		DefaultMutableTreeNode displayNode = new  DefaultMutableTreeNode();
		this.setDisplayNode(displayNode);
		this.setName("OR");
	}
	
	private void checkChildrenSatisfied() {
		for(VictoryCondition child : this.getChildren()) {
			if(child.isSatisfied()) {
				conditionSatisfied = true;
				break;
			}
		}
		this.getDisplayNode().setUserObject(getName());
	}

	@Override
	public boolean isSatisfied() {
		checkChildrenSatisfied();
		return conditionSatisfied;
	}
}