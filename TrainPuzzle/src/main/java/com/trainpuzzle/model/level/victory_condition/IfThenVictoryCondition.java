package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class IfThenVictoryCondition extends LogicalVictoryCondition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public IfThenVictoryCondition() {
		userObject = new TreeNodeUserObject(this,"Complete in order");
		DefaultMutableTreeNode displayNode = new DefaultMutableTreeNode(userObject);
		this.setDisplayNode(displayNode);
		setTreeModel(new DefaultTreeModel(displayNode));
	}
	
	protected boolean checkChildrenSatisfied() {
		boolean satisfied = true;
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				satisfied = false;
				break;
			}
		}
		return satisfied;
	}
	
	@Override
	public void processEvent(Event event) {
		for(VictoryCondition child : this.getChildren()) {
			if(!child.isSatisfied()) {
				child.processEvent(event);
				break;
			}
		}
	}
	
	
}