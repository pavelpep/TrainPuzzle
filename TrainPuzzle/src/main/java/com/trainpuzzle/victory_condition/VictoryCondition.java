package com.trainpuzzle.victory_condition;

import java.util.LinkedList;
import java.util.List;

public abstract class VictoryCondition {
	protected boolean conditionSatisfied;
	protected boolean conditionCanBeSatisfed;
	protected VictoryCondition leftChild;
	protected VictoryCondition rightChild;
	protected List<String> eventIDsOfLeftSubtree;
	protected List<String> eventIDsOfRightSubtree;
	
	public VictoryCondition() {
		this.conditionSatisfied =false;
		this.conditionCanBeSatisfed = true;
		this.eventIDsOfLeftSubtree = new LinkedList<String>();
		this.eventIDsOfRightSubtree = new LinkedList<String>();
	}
	public boolean isSatisfied() {
		return this.conditionSatisfied;
	}
	private boolean isLeftSatisfied() {
		return leftChild.isSatisfied();
	}
	private boolean isRightSatisified() {
		return rightChild.isSatisfied();
	}
	
	public List<String> getEventIDsOfLeftSubtree() {
		return eventIDsOfLeftSubtree;
	}
	public List<String> getEventIDsOfRightSubtree() {
		return eventIDsOfRightSubtree;
	}
	public void setLeftChild(VictoryCondition leftChild) {
		this.leftChild = leftChild;
		if(leftChild.getClass() ==LeafVictoryCondition.class) {
			appendEventIDFromLeaf(eventIDsOfLeftSubtree,leftChild);
		}
		else {
			appendEventIDFromOther(eventIDsOfLeftSubtree,leftChild);
		}
		
	}
	public void setRightChild(VictoryCondition rightChild) {
		this.rightChild = rightChild;
		if(leftChild.getClass() ==LeafVictoryCondition.class) {
			appendEventIDFromLeaf(eventIDsOfRightSubtree,rightChild);
		}
		else {
			appendEventIDFromOther(eventIDsOfRightSubtree,rightChild);
		}
	}
	public abstract void checkEvent(Event event);
	
	private void appendEventIDFromLeaf(List<String> EventIDs,VictoryCondition victoryCondition) {
		LeafVictoryCondition leaf = (LeafVictoryCondition)victoryCondition;
		EventIDs.add(leaf.getEventID());
	}
	
	private void appendEventIDFromOther(List<String> EventIDs,VictoryCondition victoryCondition) {
		List<String> leftEventIDs = victoryCondition.getEventIDsOfLeftSubtree();
		List<String> rightEventIDs = victoryCondition.getEventIDsOfRightSubtree();
		EventIDs.addAll(leftEventIDs);
		EventIDs.addAll(rightEventIDs);
	}
	
}
