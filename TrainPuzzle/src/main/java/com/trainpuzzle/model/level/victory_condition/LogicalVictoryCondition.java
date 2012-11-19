package com.trainpuzzle.model.level.victory_condition;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class LogicalVictoryCondition implements VictoryCondition {
	
	private List<VictoryCondition> childConditions = new ArrayList<VictoryCondition>();
	protected boolean conditionSatisfied = false;
	private String name;
	private DefaultMutableTreeNode displayNode;
	@Override
	public boolean isSatisfied() {
		return conditionSatisfied;
	}
	
	@Override
	public void processEvent(Event event) {
		for(VictoryCondition child : childConditions) {
			child.processEvent(event);
		}
	}

	@Override
	public void resetEvents() {
		 conditionSatisfied = false;
		 this.displayNode.setUserObject(name);
		 resetChildrenEvents();
	}
	
	private void resetChildrenEvents() {
		for(VictoryCondition child : childConditions) {
			child.resetEvents();
		}
	}
	
	public List<VictoryCondition> getChildren() {
		return childConditions;
	}
	
	public void addChild(VictoryCondition child) {
		childConditions.add(child);
		this.displayNode.add(child.getDisplayNode());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DefaultMutableTreeNode getDisplayNode() {
		return displayNode;
	}

	public void setDisplayNode(DefaultMutableTreeNode displayNode) {
		this.displayNode = displayNode;
	}
	
}
