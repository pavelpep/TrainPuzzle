package com.trainpuzzle.victory_condition;

public abstract class VictoryCondition {
	protected boolean conditionSatisfied;
	protected boolean conditionCanBeSatisfed;
	protected VictoryCondition leftChild;
	protected VictoryCondition rightChild;
	protected VictoryCondition Parent;
	
	public VictoryCondition() {
		this.conditionSatisfied =false;
		this.conditionCanBeSatisfed = true;
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
	
	public void setLeftChild(VictoryCondition leftChild) {
		this.leftChild = leftChild;
		this.leftChild.setParent(this);
		
	}
	public void setRightChild(VictoryCondition rightChild) {
		this.rightChild = rightChild;
		this.rightChild.setParent(this);
	}
	public void setParent(VictoryCondition parent) {
		Parent = parent;
	}
	
	public VictoryCondition getLeftChild() {
		return leftChild;
	}
	public VictoryCondition getRightChild() {
		return rightChild;
	}
	public abstract void checkCoditionSatisfaction();
	
	
	
	
}
