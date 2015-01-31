package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	
	VictorSP liftMotor;
	VictorSP motorL;
	VictorSP motorR;
	
	
	Joystick stickR;
	Joystick stickL;
	Joystick gamePad; 
	
	DoubleSolenoid intakeSol = new DoubleSolenoid(0, 1);
	
	Compressor compressor = new Compressor(0);//init compressor and maps it
	
	int autoLoopCounter;
	int totes; //num of totes carried
    
	double stickPos = 1;
	double Dgain = 0.011;
    double Pgain = 0.45;
    double Igain = 0.024;//.012
    float error;
    double last_error = 0;
    double Padjustment;
    double Dadjustment;
    double PID_adjust;
    double valJoyR;
    double valJoyL;
    
	boolean grab;
	boolean release; 
	boolean up;
    boolean down;
    boolean deployPrep;

	public static boolean halfSpeed;
    
    // pid cotrol variables
    
	double output;
	double integral;
	double derivative;
	
	float prevError;
	float Wait;
	float armPosition;
	float armTarget;
	float arm;
	float P;
	float I;
	float D;
	float dt;
	
	int exp1;
	int exp2;
	int setPoint;
	int position;
	
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
    	
    	liftMotor = new VictorSP(2);
    	
    	liftMotor.enableDeadbandElimination(true);
    	
    	halfSpeed = true; 
    	
    }
   
    public void disabledInit() {
    	
    	compressor.stop();//set percent to 0

    }
    
    public void autonomousInit() {
    	
    	compressor.start();
    	
    	autoLoopCounter = 0;
    	
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
    	
    	valJoyR = stickR.getRawAxis(1);
    	valJoyL = stickL.getRawAxis(1);
    	
    	up = stickR.getRawButton(1);
        down = stickL.getRawButton(1);
        grab = gamePad.getRawButton(1);
        release = gamePad.getRawButton(2);

      	intakeControl();
      	
      	Timer.delay(0.005); // wait for a motor update time
      	
        if (valJoyR >= .15 || valJoyR <= -.15 || valJoyL >= .15 || valJoyL <= -.15){
        	
        	//System.out.println("tank active");
        	
        	if(up == true && down == false) {
        		
        		halfSpeed = true;
        		
        		//System.out.println("speed = high");
 
        	} else if(down == true && up == false){
        	
               halfSpeed = false;
               
               //System.out.println("speed = low");
               
        	}
        
        	if (halfSpeed == false){
        	
        		fullSpeedDrive();
        	
        	} else if (halfSpeed == true){
        	
        		halfSpeedDrive();
        	
        	}
        
        }  
    }
    
    public void intakeControl() {
    	
    		grab = gamePad.getRawButton(7);
    		release = gamePad.getRawButton(8);   		       
    		
    		if (grab==true && release == false){
    			
                intakeSol.set(DoubleSolenoid.Value.kForward);//grab
                
                System.out.println("solenoid = forward");
                
            } else if (release==true && grab == false) {
            	
                intakeSol.set(DoubleSolenoid.Value.kReverse);//release
                
                System.out.println("solenoid = reverse");
                
            }
   }
    public void fullSpeedDrive(){
    	
    	double joyR = stickR.getRawAxis(1);
        double joyL = stickL.getRawAxis(1);
        
    	Timer.delay(0.005);
    	
    	joyR *= .95;
    	joyL *= .95;
    	
    	myRobot.tankDrive(-joyR, -joyL);
    	
    	System.out.println("speed = high");
    	
    }
    
    public void halfSpeedDrive(){
    	
    	double joyR = stickR.getRawAxis(1);
        double joyL = stickL.getRawAxis(1); 
        
    	Timer.delay(0.005);
    	
    	joyR *= .7;
    	joyL *= .7;
    	
    	myRobot.tankDrive(-joyR, -joyL);
    	
    	System.out.println("speed = low");
    	
    }
    
    public void liftControl() {
    	
    	//set the pid vals to variable USE THESE IN DECLARATION
    	// call liftControl from teleop periodic
    	//get encoder value
    	//hardcode the target for now for testing - always do Lift routine to same spot, eg. 1000
    	
    	doPID(P, I, D, dt, position, setPoint);   //performs PID output. PID coefficients = 1, 0, 0.15 respectively; time to differentiate = 1 ms; reads position from potentiometer; target angle
	
		output = output / 100;                                          //scales output back by 100 since the potentiometer says a few hundred counts equals less than 100 degrees in RL
		
		if(output > 1){ output = 1;}                                    //these two lines set the output to max should it exceed the max
		if(output < -1){ output = -1;}
		
		//below is where set motor speed to output from PID
		//use correct Java syntax for this
		//you will need to define the motor, too.
		//just copy the driver motor definitions in the same sections that they are done
		//you will also need to initialize the encoder counts to 0 somewhere, like in teleopInit
		//arm.Set(output); 
    	
    }
    
    public void doPID(float P, float I, float D, float dt, int position, int setPoint){ //arm to left is less than 470; to the right greater than 470
    	
       	//you need to translate this into Java syntax and declare your variables
	    //I think the output is the speed
    	
	    Wait(dt);
		error = position - setPoint;
		integral = integral + (error * dt);
		derivative = (error - prevError) / dt;
		output = (P * error) + (I * integral) + (D * derivative);
		prevError = error;
		
    	
	}
    
    private void Wait(float dt2) {
		// TODO Auto-generated method stub
		
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