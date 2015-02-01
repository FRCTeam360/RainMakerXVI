package org.usfirst.frc.team360.robot;

public class variableScope {
	public static int aStaticInt; // variableScope.aStaticInt;
	// class level variables - you can reference these anywhere in the class.
	private int myPrivateInt; // only available in this class.
	public int myPublicInt;// available outside of this class as {variable}.myPublicInt;
	
	private void myPrivateMethod() {
		int methodLevelInt; // only available in this method.
		
		for(int i = 1; i < 10; i++) {
			int loopVariable; // only available in the for loop.
		}
	}
}
