package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class enablePIDLift extends Command {
	OI controls = OI.GetInstance();
	
	private boolean done = false;
	
	private double error;
	private double integral;
	private double derivative;
	private double output;
	private double prevError;
	
    public enablePIDLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	controls.liftMotor.set(output);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

    public void liftControl1() {
    	
    	//set the pid vals to variable USE THESE IN DECLARATION
    	// call liftControl from teleop periodic
    	//get encoder value
    	//hardcode the target for now for testing - always do Lift routine to same spot, eg. 1000

    	double p = 0.001; 
    	double i = 0;
    	double d = 0.0015;
    	double Dt = 0.01; 
    	
    	
    	
    	
    	
    	double PIDOUTPUT1 = doPID1(p, i, d, Dt, controls.EnvGlbValR, 50);// ONLY CHANGE THE LAST NUMBER DO NOT TOUCH ANYTHING ELSE HERE
    	
	    System.out.println(PIDOUTPUT1 + "output 1.11");
	    
	    PIDOUTPUT1 /= 10;   
     
    	//scales output back by 100 since the potentiometer says a few hundred counts equals less than 100 degrees in RL

    	if(PIDOUTPUT1 > 1){
    		
    		System.out.println("#1.1");
    		
    		PIDOUTPUT1 = .7; 
    		
    		controls.liftMotor.set(PIDOUTPUT1);
 
    		} else if (PIDOUTPUT1 < -1){
    		
    		System.out.println("#2.1");
    		
    		PIDOUTPUT1 = .7;
    		
    		controls.liftMotor.set(PIDOUTPUT1);
       
    		}
    	              
    	if (PIDOUTPUT1 > -1 || PIDOUTPUT1 < 1){
    		
    		System.out.println("#4.1");
    
    		controls.liftMotor.set(PIDOUTPUT1);

    		}
    	
    	//System.out.println(encValLift + "encValLift 2");
    	
    }
public double doPID1(double P, double I, double D, double dt, float position, float setPoint){ //arm to left is less than 470; to the right greater than 470
    	
       	//you need to translate this into Java syntax and declare your variables
	    //I think the output is the speed

    	Timer.delay(dt);

		SmartDashboard.putDouble( "position: ", position); 
      	SmartDashboard.putDouble( "setPoint: ", setPoint);
      	
		error = position - setPoint;
		
		integral = integral + (error * dt);
		
		derivative = (error - prevError) / dt;
		
		output = (P * error) + (I * integral) + (D * derivative);
		
		prevError = error;
		
		SmartDashboard.putDouble( "error: ", error); 
		SmartDashboard.putDouble( "output: ", output); 
		
		return -output;
    	
	}
}
