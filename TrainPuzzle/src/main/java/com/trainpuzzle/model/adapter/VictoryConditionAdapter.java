package com.trainpuzzle.model.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.trainpuzzle.model.level.victory_condition.*;

public class VictoryConditionAdapter implements TreeModel {
	TreeModelListener treeModelListener;
	VictoryCondition rootCondition;
	
	public VictoryConditionAdapter(VictoryCondition root) {
		rootCondition = root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		AndVictoryCondition victoryCondition = (AndVictoryCondition) parent;
		List<VictoryCondition> parentsChildren = victoryCondition.getChildren();
		
		
		List<VictoryConditionAdapter> adaptedParentsChildren = new ArrayList<VictoryConditionAdapter>();
		for (VictoryCondition child : parentsChildren) {
			adaptedParentsChildren.add(new VictoryConditionAdapter(child));
		}
		
		if(parentsChildren.size() < index) {
			return null; // As requested in TreeModel documents
		}
		return adaptedParentsChildren.get(index);
	}

	
	@Override
	public int getChildCount(Object parent) {
		if(parent instanceof LeafVictoryCondition) {
			return -1;
		}
		
		AndVictoryCondition victoryCondition = (AndVictoryCondition) parent;
		List<VictoryCondition> parentsChildren = victoryCondition.getChildren();
		
		return parentsChildren.size();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if(parent == null || child == null) {
			return -1; // As requested in TreeModel documents
		}
		AndVictoryCondition victoryCondition = (AndVictoryCondition) parent;
		
		return victoryCondition.getChildren().indexOf(child);
	}
	
	@Override
	public Object getRoot() {
		return new VictoryConditionAdapter(rootCondition);
	}

	@Override
	public boolean isLeaf(Object node) {
		return node instanceof LeafVictoryCondition;
	}

/* 
 * **************************************************
	TODO: Still need to complete features below. 
	Listener needs to be implemented/extended properly in VictoryCondition somehow?	
 * ************************************************** 
*/
	
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		treeModelListener = l;
	}
	
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		treeModelListener = null;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		
	}
}
