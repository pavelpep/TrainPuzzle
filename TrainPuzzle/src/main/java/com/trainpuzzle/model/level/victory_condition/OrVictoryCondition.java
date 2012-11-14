package com.trainpuzzle.model.level.victory_condition;

public class OrVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private void checkChildrenSatisfied() {
		for(VictoryCondition child : this.getChildren()) {
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

}
