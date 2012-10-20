package com.trainpuzzle.victory_condition;

public class OrVictoryCondition extends VictoryCondition {

	@Override
	public void checkCoditionSatisfaction() {
		if (leftChild == null) {
			conditionSatisfied = rightChild.isSatisfied();
		}
		else if (rightChild == null) {
			conditionSatisfied = leftChild.isSatisfied();
		}
		if(parent != this) {
			parent.checkCoditionSatisfaction();
		}

	}

}
