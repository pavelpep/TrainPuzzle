package com.trainpuzzle.exception;

public class InvalidCommonHeadingException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidCommonHeadingException() {
		super("Not having only one common heading!");
	}
	
	public InvalidCommonHeadingException(String message) {
		super(message);
	}
}
