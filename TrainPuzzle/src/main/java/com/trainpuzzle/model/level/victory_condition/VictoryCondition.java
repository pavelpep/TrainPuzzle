package com.trainpuzzle.model.level.victory_condition;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Represents 'component' in the composite programming pattern
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Composite_pattern">Wikipedia/Composite_pattern</a>
 */
public interface VictoryCondition{
		
	public boolean isSatisfied();
	public void processEvent(Event event);
	public void resetEvents();
	public DefaultMutableTreeNode getDisplayNode(); 
	public void setDisplayNode(DefaultMutableTreeNode displayNode);
	public DefaultTreeModel getTreeModel();
	public void setTreeModel(DefaultTreeModel treeModel);
	
}