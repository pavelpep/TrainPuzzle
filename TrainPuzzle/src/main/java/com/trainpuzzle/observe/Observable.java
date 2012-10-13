package com.trainpuzzle.observe;

import java.util.Set;
import java.util.List;

public interface Observable {
	public void register(Observer observer);
	public void notifyAllObservers();
}
