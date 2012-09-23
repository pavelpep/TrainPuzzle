package repository_test;

import java.util.*;

/**
 * This class is used to test checkin's done by jsc15 (Jesse Chahal). I have
 * also added a few things to test out SVN keyword replacement for comments.
 * This could prove useful in the future when we begin actual development. 
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 * $Author$
 */
public class Jsc15 {
	/** Class attribute used for nothing */
	private String testField;
	
	public Jsc15() {
	}
	
	public Jsc15(String testField) {
		this.testField = testField;
	}
	
	
	/**
	 * This is a test method that is used to print 'hello world'.
	 * 
	 * @param randomParam integer to be appended to the 'hello world' statement
	 * @return true if something was printed, else false
	 */
	public static boolean helloWorld(int randomParam) {
		
		try {
			System.out.println("hello world " + String.valueOf(randomParam));
			
			return true;
		} catch(Exception e) {
			System.out.println("Error printing hello World:");
			e.printStackTrace();
		}
		
		return false;
	}
	
}
