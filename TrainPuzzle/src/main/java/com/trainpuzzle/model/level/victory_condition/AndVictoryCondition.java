package com.trainpuzzle.model.level.victory_condition;

public class AndVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private void checkChildrenSatisfied() {
		conditionSatisfied = true;
		for(VictoryCondition child : this.getChildren()) {
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
	
}
