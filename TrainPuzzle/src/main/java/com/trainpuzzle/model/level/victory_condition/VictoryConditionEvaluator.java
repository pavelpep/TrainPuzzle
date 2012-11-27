package com.trainpuzzle.model.level.victory_condition;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import com.trainpuzzle.model.level.Level;
import com.trainpuzzle.observe.*;

public class VictoryConditionEvaluator implements Observable {
	
	private transient Set<Observer> observerList = new HashSet<Observer>();	
	private VictoryCondition root;
	
	public VictoryConditionEvaluator(Level level) {
		this.root = level.getVictoryConditions();
	}
	
	public boolean isSatisfied() {
		Boolean isSatisfied = root.isSatisfied();
		if(isSatisfied){notifyAllObservers();}
		return isSatisfied;
	}

	public void processEvent(Event event) {
		root.processEvent(event);
	}

	public void resetEvents() {
		root.resetEvents();
	}

	@Override
	public void register(Observer observer) {
		if(observerList == null) {
			observerList = new HashSet<Observer>();
		}
		observerList.add(observer);
	}

	@Override
	public void notifyAllObservers() {
		for(Observer observer : observerList) {
			observer.notifyChange(this);
		}
	}
}

// For future reference 
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
