package repository_test;

import java.util.*;

/**
 * This class is used to test checkin's done by jsc15 (Jesse Chahal). I have
 * also added a few things to test out SVN keyword replacement for comments.
 * This could prove useful in the future when we begin actual development. 
 * <p>
 * Apparently there is an issue with Java libraries if you try to use a Linux machine
 * with the current project setup. If JRE location is changed to the correct location on a
 * Linux machine it will no longer work on Windows machines (at least thats the assumption I make).
 * So I assume that we all need to use the same OS to prevent this issue...
 * 
 * @author $Author$
 * @version $Revision$
 * @since $LastChangedDate$
 *
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
