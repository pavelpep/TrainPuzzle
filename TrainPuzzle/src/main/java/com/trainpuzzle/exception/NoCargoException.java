package com.trainpuzzle.exception;

public class NoCargoException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoCargoException() {
		
	}
	
	public NoCargoException(String message) {
		super(message);
	}
}
