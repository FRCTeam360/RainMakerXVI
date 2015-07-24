package org.usfirst.frc.team360.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Centerers extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	DoubleSolenoid lowerIntakeSol;
	public Centerers(){
		lowerIntakeSol = new DoubleSolenoid(3, 4);
	}
    public void initDefaultCommand() {}
    public void close(){
    	lowerIntakeSol.set(DoubleSolenoid.Value.kReverse);
    }
    public void open(){
    	lowerIntakeSol.set(DoubleSolenoid.Value.kForward);
    }
}

