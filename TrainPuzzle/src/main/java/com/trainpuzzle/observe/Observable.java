package com.trainpuzzle.observe;

public interface Observable {
	public void register(Observer observer);
	public void notifyAllObservers();
}