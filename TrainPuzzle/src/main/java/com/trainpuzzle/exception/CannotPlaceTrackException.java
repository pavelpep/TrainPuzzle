package com.trainpuzzle.exception;

public class CannotPlaceTrackException extends CannotOperateTrackException {

	private static final long serialVersionUID = 1L;

	public CannotPlaceTrackException() {
		super("Cannot place track");
	}
	
	public CannotPlaceTrackException(String message) {
		super(message);
	}
}