package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class activeSol extends Command {
	OI controls = OI.GetInstance();
	private boolean done = false;
	
	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		SmartDashboard.putString("Solenoid Status: ", "Forward");
		controls.intakeSol1.set(DoubleSolenoid.Value.kForward);//grab
		controls.intakeSol2.set(DoubleSolenoid.Value.kForward);//grab
		done = true;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		end();
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return done;
	}

}
