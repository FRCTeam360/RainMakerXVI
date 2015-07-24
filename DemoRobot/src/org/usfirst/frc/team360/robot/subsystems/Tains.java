package org.usfirst.frc.team360.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Tains extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	DoubleSolenoid upperIntakeSol;
	public Tains(){
		upperIntakeSol = new DoubleSolenoid(0, 7);
	}
	
    public void initDefaultCommand() {}
    public void close(){
    	upperIntakeSol.set(DoubleSolenoid.Value.kReverse);
    }
    public void open(){
    	upperIntakeSol.set(DoubleSolenoid.Value.kForward);
    }
    
}

