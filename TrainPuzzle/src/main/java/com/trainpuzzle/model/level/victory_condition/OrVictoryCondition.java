package com.trainpuzzle.model.level.victory_condition;

import java.util.ArrayList;
import java.util.List;

public class OrVictoryCondition implements VictoryCondition {
	private List<VictoryCondition> childConditions = new ArrayList<VictoryCondition>();
	private boolean conditionSatisfied = false;
	
	
	private void checkChildrenSatisfied() {
		for(VictoryCondition child : childConditions) {
			if(child.isSatisfied()) {
				conditionSatisfied = true;
				break;
			}
		}
	}

	@Override
	public boolean isSatisfied() {
		checkChildrenSatisfied();
		return conditionSatisfied;
	}
	
	public void addChild(VictoryCondition child) {
		childConditions.add(child);
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

}
