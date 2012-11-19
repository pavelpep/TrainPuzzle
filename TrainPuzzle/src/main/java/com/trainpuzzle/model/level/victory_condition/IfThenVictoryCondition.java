package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;

public class IfThenVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public IfThenVictoryCondition() {
		this.setName("In order");
		 DefaultMutableTreeNode displayNode = new  DefaultMutableTreeNode("In order");
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
	
	@Override
	public void processEvent(Event event) {
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				child.processEvent(event);
				break;
			}
		}
	}
}