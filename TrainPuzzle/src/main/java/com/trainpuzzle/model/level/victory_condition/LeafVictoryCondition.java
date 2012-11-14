package com.trainpuzzle.model.level.victory_condition;

public class LeafVictoryCondition implements VictoryCondition, java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final boolean HASTIMELIMIT = true;
	private Event condition;
	private boolean conditionSatisfied = false;
	private boolean hasTimeLimit = false;
	private int timeLimit;
	
	public LeafVictoryCondition(Event condition) {
		this.condition = condition;
	}
	
	public LeafVictoryCondition(boolean hasTimeLimit, int timeLimit,Event condition) {
		this.condition = condition;
		this.hasTimeLimit = hasTimeLimit;
		this.timeLimit = timeLimit;
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
		if (!conditionSatisfied) {
			if(!hasTimeLimit) {
				conditionSatisfied = condition.equals(event);
			}
			else {
				conditionSatisfied = (event.getTime() <= timeLimit) && condition.equals(event);
			}
		}
	}
}