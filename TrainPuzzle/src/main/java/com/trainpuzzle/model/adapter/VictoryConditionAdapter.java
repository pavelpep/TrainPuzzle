package com.trainpuzzle.model.adapter;

import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.trainpuzzle.model.level.victory_condition.*;

public class VictoryConditionAdapter implements TreeModel {
	TreeModelListener treeModelListener; //FIXME: Listener probably needs to be implemented/extended in VictoryCondition somehow?
	VictoryCondition rootCondition;
	
	
	public VictoryConditionAdapter(VictoryCondition root) {
		rootCondition = root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		AndVictoryCondition victoryCondition = (AndVictoryCondition) parent;
		List<VictoryCondition> parentsChildren = victoryCondition.getChildren();
		
		if(parentsChildren.size() < index) {
			return null; //as requested in TreeModel docs
		}
		
		return parentsChildren.get(index);
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
			return -1; //as requested in TreeModel docs
		}
		AndVictoryCondition victoryCondition = (AndVictoryCondition) parent;
		
		return victoryCondition.getChildren().indexOf(child);
	}
	
	@Override
	public Object getRoot() {
		return rootCondition;
	}

	@Override
	public boolean isLeaf(Object node) {
		return node instanceof LeafVictoryCondition;
	}

/* **************************************************
	Still need to complete below features,
	Listener probably needs to be implemented/extended in VictoryCondition somehow?	
***************************************************************** */
	
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
		// TODO Auto-generated method stub
	}

}
