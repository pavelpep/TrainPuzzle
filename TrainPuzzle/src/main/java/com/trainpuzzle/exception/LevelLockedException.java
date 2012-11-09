
package com.trainpuzzle.exception;

public class LevelLockedException extends Exception {

	private static final long serialVersionUID = 1L;

	public LevelLockedException() {
		super("You cannot load a level that is locked!");
	}
	
	public LevelLockedException(String message) {
		super(message);
	}
}
