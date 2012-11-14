package com.trainpuzzle.exception;

public class CannotRemoveTrackException extends Exception {

	private static final long serialVersionUID = 1L;

	public CannotRemoveTrackException() {
		super("Cannot remove track");
	}
	
	public CannotRemoveTrackException(String message) {
		super(message);
	}
}