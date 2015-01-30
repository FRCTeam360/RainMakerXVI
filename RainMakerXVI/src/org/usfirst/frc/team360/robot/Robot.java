package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
//tank drive 2 Joysticks, left trigger engages down speed right trigger activates up speed
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	 Joystick stickR;
	 Joystick stickL;
	 Joystick gamePad; 
	
	
	
	
	int autoLoopCounter;
	int totes; //num of totes carried
	boolean grab;
	boolean release; 
	boolean up;
    boolean down;
	double stickPos = 1;
	//double joyR = stickR.getRawAxis(1);
	//double joyL = stickL.getRawAxis(1);
	//double Intake = gamePad.getRawAxis(2);
	public static boolean halfSpeed;
	DoubleSolenoid intakeSol = new DoubleSolenoid(0, 1);
	public Compressor compressor = new Compressor(0);//init compressor and maps it
	
	

	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	
	
    public void robotInit() {
    	myRobot = new RobotDrive(0,1);
    	myRobot.setExpiration(0.1);
    	stickR = new Joystick(1); 
    	stickL = new Joystick(0);
    	gamePad = new Joystick(2);
    	halfSpeed = true; 
        
    }
   
    public void disabledInit() {
    	compressor.stop();
    	//set percent to 0

    }
    
    public void autonomousInit() {
    	compressor.start();
    	//autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
   
	public void autonomousPeriodic() {
    	
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    
	public void teleopInit(){
		 compressor.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	up = stickR.getRawButton(1);
        down = stickL.getRawButton(1);
        grab = gamePad.getRawButton(1);
        release = gamePad.getRawButton(2);
        double joyR = stickR.getRawAxis(1);
        double joyL = stickL.getRawAxis(1);
    	double joy3 = gamePad.getRawAxis(1); 
    	//myRobot.tankdrive(joyR, joyL);
        
      	//myRobot.tankDrive(-joyR, joyL); 
      	intakeControl();
           Timer.delay(0.005); // wait for a motor update time
        if(up == true && down == false) {
        	//shiftUp();
        		
        		halfSpeed = true;
        		System.out.println("speed = high");
        		//myRobot.tankDrive(0.9, 0.9);
        		
        	
        	
        }
        if(down == true && up == false){
        	//shiftDown();
        	
               halfSpeed = false;
               //myRobot.tankDrive(0.8, 0.8);
              	System.out.println("speed = low");
               
        }
        if (halfSpeed == false){
        	Timer.delay(0.005);
        	joyR *= .95;
        	joyL *= .95;
        	myRobot.tankDrive(-joyR, joyL);
        } else if (halfSpeed == true){
        	
        	joyR *= 7;
        	joyL *= .7;
        	Timer.delay(0.005);
        	myRobot.tankDrive(-joyR, joyL);
        }
    }  
    	public void intakeControl() {
    		grab = gamePad.getRawButton(7);
    		release = gamePad.getRawButton(8);
            //Claw control
            if (grab==true)
            {
                intakeSol.set(DoubleSolenoid.Value.kForward);//grab
                System.out.println("speed = reverse");
            } 
            else if (release==true) 
            {
                intakeSol.set(DoubleSolenoid.Value.kReverse);//release
                System.out.println("speed = forward");
            }
   }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    public void intake(){
    	//do intaky stuff
    	
    	//totes++; // add another tote to the stack
    }
    public void deployStack(){
    	
    }
}