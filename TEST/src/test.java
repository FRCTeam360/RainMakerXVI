/**
 * 
 */
//package org.usfirst.frc.team360.test;

/**
 * @author user
 *
 */
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		method1();
		
		int int1;
		int1 = 1; // "=" for setting variables
		double double1; 
		double1 = 0.1; // always decimal
		float float1;
		float1 = 1000; //+,-, any number
		String string1;
		string1 = "Hello World";
		System.out.println(string1); 
		boolean bool1;
		bool1 = true;
		
		if(bool1 == true){ //"==" for logic
			System.out.println("bool = true");
		
		}
        while (bool1 == false){
        	System.out.println("bool = false");
        }
        for (int x = 0; x < 20; x = x + 1){ //will run 19 times
        	}
	}
	public static void method1(){
		System.out.println("method1");
		
	}
}
