package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class LeafVictoryCondition implements VictoryCondition, java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final boolean HASTIMELIMIT = true;
	private Event condition;
	private boolean conditionSatisfied = false;
	private boolean hasTimeLimit = false;
	private int timeLimit;
	private String name;
	private DefaultMutableTreeNode displayNode;
	private DefaultTreeModel treeModel;
	private TreeNodeUserObject userObject;
	
	public LeafVictoryCondition(Event condition) {
		this.condition = condition;
		name = condition.toString();
		
		userObject = new TreeNodeUserObject(this,condition.toString());
		DefaultMutableTreeNode displayNode = new DefaultMutableTreeNode(userObject);
		this.setDisplayNode(displayNode);
		
		treeModel = new DefaultTreeModel(displayNode);
	}
	
	public LeafVictoryCondition(boolean hasTimeLimit, int timeLimit,Event condition) {
		this.condition = condition;
		this.hasTimeLimit = hasTimeLimit;
		this.timeLimit = timeLimit;
		name = condition.toString();
		setDisplayNode(new DefaultMutableTreeNode(name));
		treeModel = new DefaultTreeModel(displayNode);
	}
	
	public Event getCondition() {
		return condition;
	}
	
	@Override
	public boolean isSatisfied() {
		return conditionSatisfied;
	}

	@Override
	public void resetEvents() {
		conditionSatisfied = false;
		this.displayNode.setUserObject(condition.toString());
	}

	@Override
	public void processEvent(Event event) {
		if (!conditionSatisfied) {
			if(!hasTimeLimit) {
				conditionSatisfied = condition.equals(event);
			}
			else {
				conditionSatisfied = (event.getTime() <= timeLimit) && condition.equals(event);
			}
			if(conditionSatisfied) {
				this.displayNode.setUserObject(event.toString()+ " Clear!");
				treeModel.nodeChanged(displayNode);
			}
		}
	}

	public String getName() {
		if(conditionSatisfied) {
			return name+ " Clear!";
		}
		else {
			return name;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public DefaultMutableTreeNode getDisplayNode() {
		return displayNode;
	}

	public void setDisplayNode(DefaultMutableTreeNode displayNode) {
		this.displayNode = displayNode;
	}
	
	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	public void setTreeModel(DefaultTreeModel treeModel) {
		this.treeModel = treeModel;
	}
	
}

