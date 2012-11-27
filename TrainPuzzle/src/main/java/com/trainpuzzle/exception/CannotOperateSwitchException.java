package com.trainpuzzle.exception;

public class CannotOperateSwitchException extends CannotOperateTrackException{

	private static final long serialVersionUID = 1L;

	public CannotOperateSwitchException() {
		super("Cannot operate switch");
	}
	
	public CannotOperateSwitchException(String message) {
		super(message);
	}
}
