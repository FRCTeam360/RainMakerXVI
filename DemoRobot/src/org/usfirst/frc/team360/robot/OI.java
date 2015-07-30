package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team360.robot.MotionProfiles;
import org.usfirst.frc.team360.robot.commands.centerTote;
import org.usfirst.frc.team360.robot.commands.closeTains;
import org.usfirst.frc.team360.robot.commands.driveHigh;
import org.usfirst.frc.team360.robot.commands.driveLow;
import org.usfirst.frc.team360.robot.commands.findFS;
import org.usfirst.frc.team360.robot.commands.getMaxVel;
import org.usfirst.frc.team360.robot.commands.highLift;
import org.usfirst.frc.team360.robot.commands.intakeTote;
import org.usfirst.frc.team360.robot.commands.normalMode;
import org.usfirst.frc.team360.robot.commands.openTains;
import org.usfirst.frc.team360.robot.commands.safeMode;
import org.usfirst.frc.team360.robot.triggers.DoubleButton;



/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
	public static final int leftJoystickPort = 0;
	public static final int rightJoystickPort = 1;
	public static final int OPJoyPort = 3;
    Joystick joyL;
    Joystick joyR;
	Joystick OPJoy;
	
	public static int numTote;
	public static boolean highSpeedDrive;
	public static boolean safeModeEnabled;
	public static boolean highManLift;
	public static boolean disablePressurize;
    public OI() {
    	joyL = new Joystick(leftJoystickPort);
    	joyR = new Joystick(rightJoystickPort);
    	OPJoy = new Joystick(OPJoyPort);
    	highSpeedDrive = false;
    	safeModeEnabled = false;
    	highManLift = false;
    	disablePressurize = false;

		new JoystickButton(joyR, 1).whenPressed(new driveHigh());
		//new JoystickButton(joyR, 2).whenPressed(new autoLift());
		new JoystickButton(joyL, 1).whenPressed(new driveLow());
		new JoystickButton(OPJoy, 1).whileHeld(new highLift());	
		new JoystickButton(OPJoy, 2).whileHeld(new intakeTote());	
		new JoystickButton(OPJoy, 3).whenPressed(new centerTote());
		new JoystickButton(OPJoy, 5).whenPressed(new closeTains());
		new JoystickButton(OPJoy, 6).whenPressed(new openTains());
		new JoystickButton(OPJoy, 10).whenPressed(new safeMode());
		new JoystickButton(OPJoy, 7).whileHeld(new getMaxVel(-1910));
		new JoystickButton(OPJoy, 8).whileHeld(new findFS());

		new DoubleButton(OPJoy, 11, 12).whenActive(new normalMode());
    }
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
