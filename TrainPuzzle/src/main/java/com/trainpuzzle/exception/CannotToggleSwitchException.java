package com.trainpuzzle.exception;

public class CannotToggleSwitchException extends CannotOperateTrackException{

	private static final long serialVersionUID = 1L;

	public CannotToggleSwitchException() {
		super("Cannot toggle switch");
	}
	
	public CannotToggleSwitchException(String message) {
		super(message);
	}
}
