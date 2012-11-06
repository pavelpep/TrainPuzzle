package com.trainpuzzle.model.level.victory_condition;

import java.util.ArrayList;
import java.util.List;

public class IfThenVictoryCondition implements VictoryCondition, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private List<VictoryCondition> childConditions = new ArrayList<VictoryCondition>();
	private boolean conditionSatisfied = false;
	
	
	private void checkChildrenSatisfied() {
		conditionSatisfied = true;
		for(VictoryCondition child : childConditions) {
			if(!child.isSatisfied()) {
				conditionSatisfied = false;
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
			if(!child.isSatisfied()) {
				child.processEvent(event);
				break;
			}
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
