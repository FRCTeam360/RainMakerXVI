
package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	
	VictorSP lift;
	
	Compressor compressor;
	Encoder encoder;
	Joystick stickR;
	Joystick stickL;
	int autoLoopCounter;
	DoubleSolenoid intakeSol1 = new DoubleSolenoid(7, 0);
	DoubleSolenoid intakeSol2 = new DoubleSolenoid(6, 1);
	//Button buttonShift= new JoystickButton(stickL, 1);
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	
	
    public void robotInit() {
    	intakeSol1 = new DoubleSolenoid(7, 0);
    	intakeSol2 = new DoubleSolenoid(6, 1);
    	myRobot = new RobotDrive(0,1);
    	myRobot.setExpiration(0.1);
    	lift = new VictorSP(2);
    	stickR = new Joystick(1); 
    	stickL = new Joystick(0);
    	encoder = new Encoder(0, 1, true, EncodingType.k1X);
    	
    	compressor = new Compressor(0);
    	
      	encoder.reset();
    	
    	encoder.setMaxPeriod(.1);
 
      	encoder.setMinRate(10);
      	
      	
    	encoder.setDistancePerPulse(5);
    	
      	encoder.setSamplesToAverage(7);
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
    		System.out.println("hele");
			myRobot.drive(0.5, 0.5); 	// drive forwards half speed
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
		encoder.reset();
		
		compressor.start();
		//intakeSol1.set(DoubleSolenoid.Value.kForward);
		//intakeSol1.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * This function is called periodically during operator control
     */
    
	public void teleopPeriodic() {
    	// test solenoid
		float enc = encoder.get();
		boolean up = stickR.getRawButton(1);
		boolean down = stickL.getRawButton(1);
      	myRobot.tankDrive(stickR, stickL);
      	System.out.println(enc); // test encoder
      	double valStickL = stickL.getRawAxis(1);
      //	lift.set(valStickL);
        
      	if(down == true && up == false){
      		intakeSol1.set(DoubleSolenoid.Value. kReverse);
      		intakeSol2.set(DoubleSolenoid.Value.kReverse);
      		System.out.println("down");
      	}
      	
      	if(up == true && down == false){
      		intakeSol1.set(DoubleSolenoid.Value.kForward);
      		intakeSol2.set(DoubleSolenoid.Value.kForward);
      		System.out.println("up");
      	}
      	
      	Timer.delay(0.005);		// wait for a motor update time
          
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