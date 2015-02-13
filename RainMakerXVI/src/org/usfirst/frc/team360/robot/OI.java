package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	RobotDrive myRobot;
	
	Joystick stickR;
	Joystick stickL;
	Joystick gamePad; 
	
	DoubleSolenoid intakeSol = new DoubleSolenoid(0, 1);
	
	Compressor compressor = new Compressor(0);//init compressor and maps it
	
	PIDController PID;
	
	int autoLoopCounter;
	int totes; //num of totes carried
	int exp1;
	int exp2;
	int setPoint;
	int position;
	int myVar = 1;
	
	double stickPos = 1;
	double Dgain = 0.011;
    double Pgain = 0.45;
    double Igain = 0.024;//.012
    double last_error = 0;
    double Padjustment;
    double Dadjustment;
    double PID_adjust;
    double valJoyR;
    double valJoyL;
	double integral;
	double derivative;
	//double valGamePad;

	public double output;
	
	boolean grab;
	boolean release; 
	boolean up;
    boolean down;
    boolean deployPrep;

	public boolean halfSpeed;

	float prevError;
	float Wait;
	float liftTarget;
	float error;

	VictorSP motorL = new VictorSP(0);
	VictorSP motorR = new VictorSP(1);
	VictorSP liftMotor = new VictorSP(9);
	
    // CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
	//public void Joysticks(){
    // Global static Joystick stick = new Joystick(0);
     //Joystick stick1 = new Joystick(1);
     //Button button = new JoystickButton(stick, 1);
    // Button button1 = new JoystickButton(stick1, 1);
	
	public void init(){
		
		myRobot = new RobotDrive(motorL, motorR);
    	
    	myRobot.setExpiration(0.1);
    	
    	stickR = new Joystick(1); 
    	stickL = new Joystick(0);
    	gamePad = new Joystick(2);
    
    	halfSpeed = true; 
    	
    	/* initialize the PID controller 
    	 * PID code is prototype
    	 * */
    	
	}
		
	public void varsForTeleop() {
		
		RobotDrive myRobot;
		
		Joystick stickR;
		Joystick stickL;
		Joystick gamePad; 
		
		DoubleSolenoid intakeSol = new DoubleSolenoid(0, 1);
		
		Compressor compressor = new Compressor(0);//init compressor and maps it
		
		
	}
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
     //button.whenPressed(new Up(2));

   
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    //button1.whileHeld(new ExampleCommand());
 
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand())
}