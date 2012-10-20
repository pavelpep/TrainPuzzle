package com.trainpuzzle.victory_condition;

public interface VictoryCondition {
	public boolean isSatisfied();
	public boolean isLeftSatisfied();
	public boolean isRightSatisified();
	
	public void setLeftChild(VictoryCondition leftChild);
	public void setRightChild(VictoryCondition rightChild);
	
}
