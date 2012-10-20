package com.trainpuzzle.victory_condition;

public class VictoryConditionEvaluator {
	private VictoryCondition root;
	private boolean isSatisfied = false;
	public VictoryConditionEvaluator(VictoryCondition root) {
		this.root = root;
	}
	
	public boolean isSatisfied() {
		return isSatisfied;
	}
	
	public void eventHappened(Event event) {
		
	}
}
