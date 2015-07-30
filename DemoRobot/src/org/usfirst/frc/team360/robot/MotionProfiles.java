package org.usfirst.frc.team360.robot;

public class MotionProfiles {

	public static double[][] liftProfileBin;
	public static double[][] liftProfileDown;
	public static double[][] liftProfileToteBin;
	public static double[][] liftProfile2ToteBin;
	public static double[][] liftProfile3ToteBin;
	public static double[][] liftProfile4ToteBin;
	public static double[][] liftProfile5ToteBin;
	public static double[][] liftProfile6ToteBin;
	public static double[][] liftProfileGround;
	public static double[]	liftConstants;
	
		public MotionProfiles(){
			
			//[0] = Kv, [1] = Ka, [2] = Kp
			liftConstants[0] = .5;
			liftConstants[1] = .5;
			liftConstants[2] = .5;
			//array [0][i] = setpoint, [1][i] = velocity, [2][i] = acceleration
			liftProfileBin[0][0] = .1;
			liftProfileBin[0][1] = .1;
			liftProfileBin[0][2] = .1;
			liftProfileBin[0][3] = .1;
			liftProfileBin[0][4] = .1;
			liftProfileBin[0][5] = .1;
			liftProfileBin[0][6] = .1;
			liftProfileBin[0][7] = .1;
			liftProfileBin[0][8] = .1;
			liftProfileBin[0][9] = .1;
			liftProfileBin[0][10] = .1;
			liftProfileBin[0][11] = .1;
			liftProfileBin[0][12] = .1;
			liftProfileBin[0][13] = .1;
			liftProfileBin[0][14] = .1;
			liftProfileBin[0][15] = .1;
			liftProfileBin[1][0] = .1;
			liftProfileBin[1][1] = .1;
			liftProfileBin[1][2] = .1;
			liftProfileBin[1][3] = .1;
			liftProfileBin[1][4] = .1;
			liftProfileBin[1][5] = .1;
			liftProfileBin[1][6] = .1;
			liftProfileBin[1][7] = .1;
			liftProfileBin[1][8] = .1;
			liftProfileBin[1][9] = .1;
			liftProfileBin[1][10] = .1;
			liftProfileBin[1][11] = .1;
			liftProfileBin[1][12] = .1;
			liftProfileBin[1][13] = .1;
			liftProfileBin[1][14] = .1;
			liftProfileBin[1][15] = .1;
			liftProfileBin[2][0] = .1;
			liftProfileBin[2][1] = .1;
			liftProfileBin[2][2] = .1;
			liftProfileBin[2][3] = .1;
			liftProfileBin[2][4] = .1;
			liftProfileBin[2][5] = .1;
			liftProfileBin[2][6] = .1;
			liftProfileBin[2][7] = .1;
			liftProfileBin[2][8] = .1;
			liftProfileBin[2][9] = .1;
			liftProfileBin[2][10] = .1;
			liftProfileBin[2][11] = .1;
			liftProfileBin[2][12] = .1;
			liftProfileBin[2][13] = .1;
			liftProfileBin[2][14] = .1;
			liftProfileBin[2][15] = .1;
			
		}
	
}