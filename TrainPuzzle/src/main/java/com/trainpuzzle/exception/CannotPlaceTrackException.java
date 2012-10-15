package com.trainpuzzle.exception;

public class CannotPlaceTrackException extends Exception{
	
	public CannotPlaceTrackException() {
		super("Cannot place the track");
	}
	
	public CannotPlaceTrackException(String message) {
		super(message);
	}
}
