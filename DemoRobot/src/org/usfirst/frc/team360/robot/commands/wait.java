package org.usfirst.frc.team360.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class wait extends Command {
	Timer time;
	double amountTime;
    public wait(double amountOfTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	time = new Timer();
    	amountTime = amountOfTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	time.start();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        	return time.hasPeriodPassed(amountTime);

    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("done");
    	time.stop();
    	time.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
