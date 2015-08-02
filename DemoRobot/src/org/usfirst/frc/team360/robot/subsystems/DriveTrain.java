package org.usfirst.frc.team360.robot.subsystems;

//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team360.robot.OI;
import org.usfirst.frc.team360.robot.RobotMap;
import org.usfirst.frc.team360.robot.commands.drive;
/**
 *drive train code 
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	VictorSP motorL;
	VictorSP motorR;
	public DriveTrain(){
		motorL = new VictorSP(RobotMap.leftMotorPort);//init speed controllers/ motors
		motorR = new VictorSP(RobotMap.rightMotorPort);
		
	}
	public void highTankDrive(){
	
		if(OI.joyL.getRawAxis(1) > .05 || OI.joyL.getRawAxis(1) < -.05){
			motorL.set(OI.joyL.getRawAxis(1)*.5);//set to 95% of joy y then set motor speed to that 
		}
		if(OI.joyR.getRawAxis(1) > .05 || OI.joyR.getRawAxis(1) < -.05){
			motorR.set(OI.joyR.getRawAxis(1)*.5);
		}
	}
	public void lowTankDrive(){
		
		if(OI.joyL.getRawAxis(1) > .05 || OI.joyL.getRawAxis(1) < -.05){
		motorL.set(OI.joyL.getRawAxis(1)*.3);//set to 50% of joy y then set motor speed to that 
		}
		if(OI.joyR.getRawAxis(1) > .05 || OI.joyR.getRawAxis(1) < -.05){
		motorR.set(OI.joyR.getRawAxis(1)*.3);
		}
	}
	public void stop() {
		motorL.stopMotor();
		motorR.stopMotor();
	}
	
/*	public Encoder getLeftEncoder() {
		return leftEncoder;
	}

	public Encoder getRightEncoder() {
		return rightEncoder;
	}*/
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new drive());
    }
}

