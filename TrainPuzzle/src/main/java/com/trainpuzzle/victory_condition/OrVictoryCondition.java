package com.trainpuzzle.victory_condition;

public class OrVictoryCondition extends VictoryCondition {

	@Override
	public void checkCoditionSatisfaction() {
		if (leftChild == null && rightChild.isSatisfied()) {
			conditionSatisfied = rightChild.isSatisfied();
		}
		else if (rightChild == null && leftChild.isSatisfied()) {
			conditionSatisfied = leftChild.isSatisfied();
		}
		else {
			conditionSatisfied = false;
		}
		if(parent != this) {
			parent.checkCoditionSatisfaction();
		}

	}

}
