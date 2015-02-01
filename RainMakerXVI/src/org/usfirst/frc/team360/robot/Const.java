package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.PIDOutput;

public class Const {
	public static int aChannel = 0; // encoder a channel
	public static int bChannel = 1; // encoder b channel
	public static boolean encoderDirection = false; // true = reverse false = forward.
	public static double KP = 0.1; // PID KP value
	public static double KI = 0; // PID KI value
	public static double KD = 0; // PID KD value
	public static double PIDPeriod = 0 ; // see PIDController method documentation
}
