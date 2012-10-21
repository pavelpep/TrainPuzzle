package com.trainpuzzle.victory_condition;

public abstract class VictoryCondition {
	protected boolean conditionSatisfied;
	protected boolean conditionCanBeSatisfed;
	protected boolean not;
	public static final boolean NOT = true;
	protected VictoryCondition leftChild;
	protected VictoryCondition rightChild;
	protected VictoryCondition parent;
	
	public VictoryCondition() {
		this.conditionSatisfied =false;
		this.conditionCanBeSatisfed = true;
		not = false;
	}
	public VictoryCondition(final boolean not) {
		this.conditionSatisfied =false;
		this.conditionCanBeSatisfed = true;
		this.not = not;
	}
	public boolean isSatisfied() {
		if (not) {
			return !this.conditionSatisfied;
		}
		return this.conditionSatisfied;
	}
	private boolean isLeftSatisfied() {
		return leftChild.isSatisfied();
	}
	private boolean isRightSatisified() {
		return rightChild.isSatisfied();
	}
	
	public void setLeftChild(VictoryCondition leftChild) {
		this.leftChild = leftChild;
		this.leftChild.setParent(this);
		
	}
	public void setRightChild(VictoryCondition rightChild) {
		this.rightChild = rightChild;
		this.rightChild.setParent(this);
	}
	public void setParent(VictoryCondition parent) {
		this.parent = parent;
	}
	
	public VictoryCondition getLeftChild() {
		return leftChild;
	}
	public VictoryCondition getRightChild() {
		return rightChild;
	}
	public abstract void checkCoditionSatisfaction();
	
	
	
	
}
