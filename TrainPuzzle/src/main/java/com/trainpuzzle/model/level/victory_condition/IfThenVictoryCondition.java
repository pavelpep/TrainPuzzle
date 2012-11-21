package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;

public class IfThenVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public IfThenVictoryCondition() {
		 DefaultMutableTreeNode displayNode = new  DefaultMutableTreeNode();
		 this.setDisplayNode(displayNode);
		 this.setName("In order");
	}
	
	private void checkChildrenSatisfied() {
		conditionSatisfied = true;
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				conditionSatisfied = false;
			}
			this.getDisplayNode().setUserObject(getName());
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
	
	public void addChild(VictoryCondition child) {
		this.getChildren().add(child);
		this.getDisplayNode().add(child.getDisplayNode());
		this.checkChildrenSatisfied();
	}
	
}