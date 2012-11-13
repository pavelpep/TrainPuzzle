package com.trainpuzzle.model.level.victory_condition;

import java.util.ArrayList;
import java.util.List;

public class LogicalVictoryCondition implements VictoryCondition{
	private List<VictoryCondition> childConditions = new ArrayList<VictoryCondition>();
	protected boolean conditionSatisfied = false;
	
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
	}
	
}
