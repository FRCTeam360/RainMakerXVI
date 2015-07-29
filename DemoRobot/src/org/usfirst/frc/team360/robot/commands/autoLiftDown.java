package org.usfirst.frc.team360.robot.commands;

import org.usfirst.frc.team360.robot.MotionProfiles;
import org.usfirst.frc.team360.robot.OI;
import org.usfirst.frc.team360.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class autoLiftDown extends Command {

	double[][] motionProfile;
	
	double[] liftconstants;
	double[] motionprofile;

	int i;
	
	boolean isDone;
	
    public autoLiftDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	motionProfile = MotionProfiles.liftProfileDown;
    	liftconstants = MotionProfiles.liftConstants;
    	requires(Robot.lift);
    	requires(Robot.velocitypid);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
         i = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    
    	if(i < motionProfile.length){

    		motionprofile[0] = motionProfile[0][i];
    		motionprofile[1] = motionProfile[1][i];
    		motionprofile[2] = motionProfile[2][i];
    		
   			double speed = Robot.velocitypid.run( Robot.lift.getEncoder(), motionprofile, liftconstants);
   		
   			Robot.lift.runLift(speed);
    		
   			i++;
    	
    	}else{

    		isDone = true;
    		
    		OI.numTote++;
    		
    	}
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
