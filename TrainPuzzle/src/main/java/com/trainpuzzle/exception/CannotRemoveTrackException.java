package com.trainpuzzle.exception;

public class CannotRemoveTrackException extends Exception {
	
	public CannotRemoveTrackException() {
		super("Cannot remove track");
	}
	
	public CannotRemoveTrackException(String message) {
		super(message);
	}
}
