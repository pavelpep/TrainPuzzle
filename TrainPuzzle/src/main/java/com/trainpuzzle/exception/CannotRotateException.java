package com.trainpuzzle.exception;

public class CannotRotateException extends CannotOperateTrackException {

	private static final long serialVersionUID = 1L;
	
	public CannotRotateException() {
		super("Cannot rotate track");
	}
	
	public CannotRotateException(String message) {
		super(message);
	}
}