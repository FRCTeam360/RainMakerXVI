package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
//tank drive 2 joysticks, left trigger engages down speed right trigger activates up speed
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	public static Joystick stickR;
	public static Joystick stickL;
	//public static double joyR = stickR.getRawAxis(1);
	//public static double joyL = stickL.getRawAxis(1);
	
	int autoLoopCounter;
	int totes; //num of totes carried
	boolean up;
    boolean down;
	//Button buttonShift= new JoystickButton(stickL, 1);
	double stickPos = 1;
	 //double joyR = stickR.getRawAxis(1);
	 //double joyL = stickL.getRawAxis(1);
	public static boolean fullSpeed;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	
	
    public void robotInit() {
    	myRobot 
    	= new RobotDrive(0,1);
    	myRobot.setExpiration(0.1);
    	stickR = new Joystick(1); 
    	stickL = new Joystick(0);
    	//fullSpeed = true;
//    	Button buttonUp = new JoystickButton(stickR, 1);
//       Button buttonDown = new JoystickButton(stickL, 1);
        
        
        
    }
   /*
    
    	

        	
        	
        	//double joyR = stickR.getRawAxis(1);
        	//double joyL = stickL.getRawAxis(1);
             ?	
        	stickPos = 1;
        	/*
        	if (stickPos <= .5 && stickPos >= .2){//checks to see is it is under 1/2 power so motor doesn't burn itself out	
        		//joyR *= 2;//double speed again
        		stickPos *= 2;
        		//myRobot.drive(joyL, joyR);
        		System.out.println("up");
        		myRobot.tankDrive(0.9, 0.9);
        	} 
        	*/
    /*
        if (stickPos >= .5 && stickPos <= 1){//checks to see is it is over 1 power so motor doesn't get REALLY low power
           	//joyR *= 2;//half speed again
           	stickPos *= 2;
           	myRobot.tankDrive(0.9, 0.9);
           	System.out.println("up");
           	fullSpeed = true;
            	
            }
	}
   
    
    public void shiftDown(){
    	
        	
        
        if (stickPos > .5 && stickPos <= 1){//checks to see is it is over 1 power so motor doesn't get REALLY low power
       	//joyR *= .5;//half speed again
       	stickPos *= .5;
       	myRobot.tankDrive(0.8, 0.8);
      	System.out.println("down");
        	
       } 
        		
        }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
	
    public void autonomousInit() {
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
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	up = stickR.getRawButton(1);
        down = stickL.getRawButton(1);
        double joyR = stickR.getRawAxis(1);
    	double joyL = stickL.getRawAxis(1);
    	//myRobot.tankdrive(joyR, joyL);
        
      	//myRobot.tankDrive(-joyR, joyL);
      	
           Timer.delay(0.005); // wait for a motor update time
        if(up == true && down == false) {
        	//shiftUp();
        		
        		fullSpeed = true;
        		System.out.println("speed = high");
        		//myRobot.tankDrive(0.9, 0.9);
        		
        	
        	
        }
        if(down == true && up == false){
        	//shiftDown();
        	
               fullSpeed = false;
               //myRobot.tankDrive(0.8, 0.8);
              	System.out.println("speed = low");
               
        }
        if (fullSpeed == true){
        	Timer.delay(0.005);
        	joyR *= .95;
        	joyL *= .95;
        	myRobot.tankDrive(-joyR, joyL);
        } else if (fullSpeed == false){
        	
        	joyR *= .7;
        	joyL *= .7;
        	Timer.delay(0.005);
        	myRobot.tankDrive(-joyR, joyL);
        }
        
          
   }
    /*
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        while (isOperatorControl() && isEnabled()) {    // don't use, its command based not iterative
       myRobot.tankDrive(stickR, stickL);
           Timer.delay(0.005);		// wait for a motor update time
       }
    }
    */
    
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