package org.usfirst.frc.team360.robot.commands;

import org.usfirst.frc.team360.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class findFS extends Command {
	Timer time;
	int length;
	int lastPos;
    public findFS() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	time = new Timer();
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	time.start();
    	if(time.get() > 1){
    		System.out.println( Robot.lift.getEncoder());
    		Robot.lift.stop();
    	}else{
    		Robot.lift.runLift(-1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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