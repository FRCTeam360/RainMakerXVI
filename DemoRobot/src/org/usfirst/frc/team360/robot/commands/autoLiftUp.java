package org.usfirst.frc.team360.robot.commands;

import org.usfirst.frc.team360.robot.MotionProfiles;
import org.usfirst.frc.team360.robot.OI;
import org.usfirst.frc.team360.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class autoLiftUp extends Command {
	double[][] motionProfile;
	
	double[] liftconstants;
	double[] motionprofile;
	
	double pos, vel, accel;
	int i;
	
	boolean isDone;
	
    public autoLiftUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	switch(OI.numTote){
    	case 0: motionProfile = MotionProfiles.liftProfileBin; break;
    	case 1: motionProfile = MotionProfiles.liftProfileToteBin; break;
    	case 2: motionProfile = MotionProfiles.liftProfile2ToteBin; break;
    	case 3: motionProfile = MotionProfiles.liftProfile3ToteBin; break;
    	case 4: motionProfile = MotionProfiles.liftProfile4ToteBin; break;
    	case 5: motionProfile = MotionProfiles.liftProfile5ToteBin; break;
    	case 6: motionProfile = MotionProfiles.liftProfile6ToteBin; break;
    	}

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
    		if(OI.numTote == 6){
    			
    		}
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