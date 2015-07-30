package org.usfirst.frc.team360.robot.commands;

import org.usfirst.frc.team360.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class getMaxVel extends Command {
	Timer time;
	int length;
	int lastPos;
    public getMaxVel(int _length) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	time = new Timer();

    	length = _length;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	time.start();
    	if(Robot.lift.getEncoder()> length){
    		System.out.println(time.get());
    		Robot.lift.stop();
    	}else{
    		Robot.lift.runLift(-1);
			SmartDashboard.putInt("liftPos", Robot.lift.getEncoder());
			SmartDashboard.putInt("liftVel", Robot.lift.getEncoder() - lastPos);
			lastPos = Robot.lift.getEncoder();
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