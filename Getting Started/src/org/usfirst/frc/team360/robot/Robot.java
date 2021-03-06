package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick stickR;
	Joystick stickL;
	int autoLoopCounter;
	//Button buttonShift= new JoystickButton(stickL, 1);
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	
	
    public void robotInit() {
    	myRobot = new RobotDrive(0,1);
    	myRobot.setExpiration(0.1);
    	stickR = new Joystick(0); 
    	stickL = new Joystick(1);
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
	
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
   
	public void autonomousPeriodic() {
    	if(autoLoopCounter < 200) //Check if we've completed 50 loops (approximately 1 seconds)
		{
			myRobot.drive(0.8, 0.8); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0);
			//autoLoopCounter = 0;// stop robot
		}
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
  
    	double valJoyR = stickR.getRawAxis(1);
    	double valJoyL = stickL.getRawAxis(1);
    	
      	myRobot.tankDrive(stickR, stickL);
           Timer.delay(0.005);		// wait for a motor update time
       	SmartDashboard.putDouble("Right: ", valJoyR);
    	SmartDashboard.putDouble("Left: ", valJoyL);
   }
    //public void operatorControl() {
       // myRobot.setSafetyEnabled(true);
       // while (isOperatorControl() && isEnabled()) {
       //	myRobot.tankDrive(stickR, stickL);
        //    Timer.delay(0.005);		// wait for a motor update time
     //   }
    //}
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
