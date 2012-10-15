package com.trainpuzzle.exception;

public class CannotPlaceTrackException extends Exception{
	
	public CannotPlaceTrackException() {
		super("Cannot place track");
	}
	
	public CannotPlaceTrackException(String message) {
		super(message);
	}
}
