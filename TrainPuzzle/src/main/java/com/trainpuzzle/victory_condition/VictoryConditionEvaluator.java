package com.trainpuzzle.victory_condition;

import java.util.LinkedList;
import java.util.List;

public class VictoryConditionEvaluator {
	private VictoryCondition root;
	private boolean isSatisfied = false;
	private List<LeafVictoryCondition> leaves;
	public VictoryConditionEvaluator(VictoryCondition root) {
		root.setParent(root);
		this.root = root;
		leaves = new LinkedList<LeafVictoryCondition>();
		checkOutLeaves(this.root);
	}
	
	public boolean isSatisfied() {
		return isSatisfied;
	}
	
	public void eventHappened(Event event) {
		for(LeafVictoryCondition leaf : leaves) {
				if(leaf.checkEvent(event)) {
					leaf.eventClear();
				}
		}
		isSatisfied = root.isSatisfied();
	}
	private void checkOutLeaves(VictoryCondition victoryCondition) {
		VictoryCondition leftChild = victoryCondition.getLeftChild();
		VictoryCondition rightChild = victoryCondition.getRightChild();
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
	
}
