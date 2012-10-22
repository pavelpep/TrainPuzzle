package com.trainpuzzle.model.level.victory_condition;

public class LeafVictoryCondition implements VictoryCondition {
	private Event condition;
	private boolean conditionSatisfied = false;
	
	public LeafVictoryCondition(Event condition) {
		this.condition = condition;
	}
	
	public Event getCondition() {
		return condition;
	}
	
	@Override
	public boolean isSatisfied() {
		return conditionSatisfied;
	}

	@Override
	public void resetEvents() {
		conditionSatisfied = false;
	}

	@Override
	public void processEvent(Event event) {
		if(condition.equals(event)) {
			conditionSatisfied = true;
		}
	}
	
	
	

}
