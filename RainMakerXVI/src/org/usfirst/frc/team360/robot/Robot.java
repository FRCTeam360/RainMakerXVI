
package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

//tank drive 2 Joysticks, left trigger engages down speed right trigger activates up speed

public class Robot extends IterativeRobot {

	OI controls = new OI(); 

	Encoder encoder = new Encoder( 0, 1, true, EncodingType.k1X);
	double Enc=encoder.get();
	//PIDController PID = new PIDController(Const.KP, Const.KI, Const.KD, encoder, liftMotor, Const.PIDPeriod); 
	public static double error;
	public static double integral;
	public static double derivative;
	public static double output;
	public static double prevError;
	public static float encVal;
	public static boolean liftTR;
	public static boolean valThing;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
	
    public void robotInit() {
    	
    	controls.init();
      	encoder.reset();
    	encoder.setMaxPeriod(.1);
      	encoder.setMinRate(10);
    	encoder.setDistancePerPulse(5);
      	encoder.setReverseDirection(true);
      	encoder.setSamplesToAverage(7);
    }
   
    public void disabledInit() {
    	
    	controls.compressor.stop();//set percent to 0

    }
    
    public void autonomousInit() {
    	
    	controls.compressor.start();
    	
    	controls.autoLoopCounter = 0;
    	
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
//		PID.
//		PID.enable();
		//controls.compressor.start();
		
		encoder.reset();// resets encoder
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    
    	controls.valJoyR = controls.stickR.getRawAxis(1);
    	controls.valJoyL = controls.stickL.getRawAxis(1);
    	
    	controls.up = controls.stickR.getRawButton(1);
    	controls.down = controls.stickL.getRawButton(1);
    	controls.grab = controls.gamePad.getRawButton(1);
    	controls.release = controls.gamePad.getRawButton(2);
    	
    
    	
    	//System.out.println(encVal);
    	
    	
    	
      	intakeControl();
      
      	SmartDashboard.putDouble( "Jo1 Axis 2", controls.stickR.getRawAxis(2));
//      	PID.enable();
      	
      	liftTR = true;
      	Timer.delay(0.005); // wait for wa motor update time
//      	PID.setSetpoint(100);
//      	if(valThing = true) {
//      	
//          		liftTR = true;
//         
//      	}
      	if(liftTR == true){
      		liftControl();
      	}
      	
        if (controls.valJoyR >= .01 || controls.valJoyR <= -.01 || controls.valJoyL >= .01 || controls.valJoyL <= -.01){
        	
        	//System.out.println("tank active");
        	
        	if(controls.up == true && controls.down == false) {
        		
        		controls.halfSpeed = true;
        		
        		//System.out.println("speed = high");
 
        	} else if(controls.down == true && controls.up == false){
        	
        		controls.halfSpeed = false;
               
               //System.out.println("speed = low");
               
        	}
        
        	if (controls.halfSpeed == false){
        	
        		fullSpeedDrive();
        	
        	} else if (controls.halfSpeed == true){
        	
        		halfSpeedDrive();
        	
        	}
        
        }  
    }
    
    public void intakeControl() {
    	
    	controls.grab = controls.gamePad.getRawButton(7);
    	controls.release = controls.gamePad.getRawButton(8);   		       
    		
    		if (controls.grab==true && controls.release == false){
    			
    			controls.intakeSol.set(DoubleSolenoid.Value.kForward);//grab
                
                System.out.println("solenoid = forward");
                
            } else if (controls.release==true && controls.grab == false) {
            	
            	controls.intakeSol.set(DoubleSolenoid.Value.kReverse);//release
                
               SmartDashboard.putData("solenoid = reverse", encoder);
                
            }
   }
    public void fullSpeedDrive(){
    	
    	double joyR = controls.stickR.getRawAxis(1);
        double joyL = controls.stickL.getRawAxis(1); 
        
    	Timer.delay(0.005);
    	
    	joyR *= .95;
    	joyL *= .95;
    	
    	controls.myRobot.tankDrive(-joyR, -joyL);
    	
    	System.out.println("speed = high");
    	
    }
    
    public void halfSpeedDrive(){
    	
    	double joyR = controls.stickR.getRawAxis(1);
        double joyL = controls.stickL.getRawAxis(1); 
        
    	Timer.delay(0.005);
    	
    	joyR *= .7;
    	joyL *= .7;
    	
    	controls.myRobot.tankDrive(-joyR, -joyL);
    	
    	System.out.println("speed = low");
    	
    }
    
    public void liftControl() {
    	
    	//set the pid vals to variable USE THESE IN DECLARATION
    	// call liftControl from teleop periodic
    	//get encoder value
    	//hardcode the target for now for testing - always do Lift routine to same spot, eg. 1000
    	
    	//controls.dt = 0;
    	encVal = encoder.get();
    	double p = 0.001; 
    	double i = 0;
    	double d = 0.0015;
    	double Dt = 0.01; 

 
    	double PIDOUTPUT = doPID(p, i, d, Dt, -encVal, 90);// ONLY CHANGE THE LAST NUMBER DO NOT TOUCH ANYTHING ELSE HERE
    System.out.println(PIDOUTPUT + "output 0");
//    	PIDOUTPUT = PIDOUTPUT / 10;   
    	//scales output back by 100 since the potentiometer says a few hundred counts equals less than 100 degrees in RL

    	if(PIDOUTPUT > 5){
    		PIDOUTPUT = -.7; 
    		controls.liftMotor.set(PIDOUTPUT);
    		}                     
    	if (PIDOUTPUT < -5){
    		PIDOUTPUT = -.7;
    		controls.liftMotor.set(PIDOUTPUT);
    		}
    	
    	//controls.liftMotor.set(PIDOUTPUT);
//    	controls.liftMotor.set(.7);

		
		
		/*int stopAt = 500;
		int current = 0;
		while(encVal < PIDOUTPUT){
			encVal = encoder.get();
			current ++;
			if(current > stopAt) { break; }
		}*/
//		controls.motorL.stopMotor();
//		controls.motorR.stopMotor();
		//below is where set motor speed to output from PID
		//use correct Java syntax for this
		//you will need to define the motor, too.
		//just copy the driver motor definitions in the same sections that they are done
		//you will also need to initialize the encoder counts to 0 somewhere, like in teleopInit
		//arm.Set(output); 
    	
    }
    
    public double doPID(double P, double I, double D, double dt, float position, float setPoint){ //arm to left is less than 470; to the right greater than 470
    	
       	//you need to translate this into Java syntax and declare your variables
	    //I think the output is the speed
    	
//    	float P;
//    	float I;
//    	float D;
    	Timer.delay(dt);
		error = position - setPoint;
		System.out.println(position + "position");
		System.out.println(setPoint + "set point");
		integral = integral + (error * dt);
		derivative = (error - prevError) / dt;
		output = (P * error) + (I * integral) + (D * derivative);
		prevError = error;
		System.out.println(error + "error");
		
		return -output;
    	
	}
    

	/**
     * This function is called periodically during test mode
     */
    
    public void testPeriodic() {
    	
    	LiveWindow.run();
    	
    }
    
    public void deployStack(){
    
   
    }
    
}