package org.usfirst.frc.team360.robot.subsystems;

import org.usfirst.frc.team360.robot.RobotMap;
import org.usfirst.frc.team360.robot.commands.pressurize;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneumatics extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Compressor compressor;
	public Pneumatics() {
		compressor = new Compressor(RobotMap.compressorPort);
	}

	public void start(){
		compressor.start();
	}
	public void stop(){
		compressor.stop();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new pressurize());
    }
}

