package repository_test;

class someFunctions {
	String name = "Joey";
	
	public void checkIn_Student() {
		System.out.println("Student " + getName() + " has checked in.");
	}
	
	public String getName() {
		return name;
	}
}

public class Joey {
	public static void main(String[]args) {
		someFunctions Joey = new someFunctions();
		
		Joey.checkIn_Student();
	}
}