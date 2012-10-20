package com.trainpuzzle.victory_condition;

public class AndVictoryCondition extends VictoryCondition{

	@Override
	public void checkCoditionSatisfaction() {
		if(this.leftChild != null && this.rightChild !=null) {
			this.conditionSatisfied = (leftChild.isSatisfied() && rightChild.isSatisfied());
		}
		else if (leftChild != null) {
			conditionSatisfied = leftChild.isSatisfied();
		}
		else if (rightChild != null) {
			conditionSatisfied = rightChild.isSatisfied();
		}
		else {
			conditionSatisfied = false;
		}
		if(parent != this) {
			parent.checkCoditionSatisfaction();
		}
	}
	
}
