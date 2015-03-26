package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Const {
	
	 //recalculate - each level is up 12.5 inches from previous, and 1 rotation of drum = 360 tick. diameter is 2 3/8"
	 //2.375 * pi = 7.461 per rotation. If we want to go 12.5" - how many rotations - 1.477 rotations; 1.477 * 360 = 532
	// numbers are approximate as there will be adjustment based on how the rope wraps
	// default is 590 at lvl 1 + 110 for clearance
	//488
	public static float liftLevel1 = -710; 
	public static float liftLevel2 = -1310;  
	public static float liftLevel3 = -1910;
	public static float liftLevel4 = -2200;
	public static float liftLeveldrive = -120; 
	public static float liftLevelground = -25; 
	public static int level1Btn = 2;
	public static int level2Btn = 3;
	public static int level3Btn = 4;
	public static int level4Btn = 8; 
	public static int drivingLevelBtn = 5;
	public static int stopMotorBtn = 6;
	public static int outBtn = 7;     //grabber out
	public static int inBtn = 9;      //grabber in
	public static int lowInBtn = 6;      //lower grabber cntrl
	public static int groundBtn = 10;
	public static int offBtn = 12; //brake off
	public static int onBtn = 14;     //brake on
	public static int overideBtn = 15;
	public static int upBtn = 17;     //mapped from dpad north on gamepad
	public static int downBtn = 18;   //mapped from dpad south on gamepad
	public static final int iautograbturnright = 2;
	public static final int iautoforwardliftback = 3;
	public static final int iautoforward = 10;
	public static final int istop= 15;
	public static final int iRightRamp= 5;
	public static final int iLeftRamp= 4;
}
