package com.trainpuzzle.model.level.victory_condition;

public class TreeNodeUserObject implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private VictoryCondition victoryCondition;
	private String label;
	
	public TreeNodeUserObject(VictoryCondition victoryCondition, String label) {
		this.victoryCondition = victoryCondition;
		setLabel(label);
	}
	
	public VictoryCondition getVictoryCondition(){
		return victoryCondition;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
}
