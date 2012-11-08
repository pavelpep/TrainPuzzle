
package com.trainpuzzle.exception;

public class CannotLoadLockedLevelException extends Exception {

	private static final long serialVersionUID = 1L;

	public CannotLoadLockedLevelException() {
		super("You cannot load a level that is locked!");
	}
	
	public CannotLoadLockedLevelException(String message) {
		super(message);
	}
}
