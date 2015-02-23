package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class testComAuto extends Command {
	OI controls = OI.GetInstance();
	private boolean done = false;
    public testComAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	controls.intakeSol1.set(DoubleSolenoid.Value.kForward);//grab
		controls.intakeSol2.set(DoubleSolenoid.Value.kForward);//grab
		controls.intakeSol1.set(DoubleSolenoid.Value.kReverse);//grab
		controls.intakeSol2.set(DoubleSolenoid.Value.kReverse);//grab
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
