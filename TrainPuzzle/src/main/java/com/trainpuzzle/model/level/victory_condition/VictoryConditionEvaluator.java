package com.trainpuzzle.model.level.victory_condition;


public class VictoryConditionEvaluator implements VictoryCondition {
	private VictoryCondition root;
	
	
	public VictoryConditionEvaluator(VictoryCondition root) {
		this.root = root;
//		checkOutLeaves(this.root);
	}
	
	public boolean isSatisfied() {
		return root.isSatisfied();
	}
/*		
	public void eventHappened(Event event) {
		for(LeafVictoryCondition leaf : leaves) {
				if(leaf.checkEvent(event)) {
					leaf.eventClear();
				}
		}
		conditionSatisified = root.isSatisfied();
	}

	private void checkOutLeaves(VictoryConditionOld victoryCondition) {
		VictoryConditionOld leftChild = victoryCondition.getLeftChild();
		VictoryConditionOld rightChild = victoryCondition.getRightChild();
		if( leftChild != null) {
			checkOutLeaves(leftChild);
		}
		if(victoryCondition.getClass() == LeafVictoryCondition.class) {
			LeafVictoryCondition leaf = (LeafVictoryCondition) victoryCondition;
			leaves.add(leaf);
		}
		if( rightChild != null) {
			checkOutLeaves(rightChild);
		}
	}
*/
	@Override
	public void processEvent(Event event) {
		root.processEvent(event);
	}

	@Override
	public void resetEvents() {
		root.resetEvents();
	}
	
}
