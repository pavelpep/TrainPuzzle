package com.trainpuzzle.exception;

public class TrainCrashException extends Exception {
	private static final long serialVersionUID = 1L;

	public TrainCrashException() {
		
	}
	
	public TrainCrashException(String message) {
		super(message);
	}
}
