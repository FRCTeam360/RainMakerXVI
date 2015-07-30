package org.usfirst.frc.team360.robot.subsystems;

//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

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
	Joystick joyL;
	Joystick joyR;
	public DriveTrain(){
		motorL = new VictorSP(RobotMap.leftMotorPort);//init speed controllers/ motors
		motorR = new VictorSP(RobotMap.rightMotorPort);
		
	}
	public void highTankDrive(int joyLPlace, int joyRPlace){
		
		joyL = new Joystick(joyLPlace);
		joyR = new Joystick(joyRPlace);
		
		if(joyL.getRawAxis(1) > .05 || joyL.getRawAxis(1) < -.05){
			motorL.set(joyL.getRawAxis(1)*.95);//set to 95% of joy y then set motor speed to that 
		}
		if(joyR.getRawAxis(1) > .05 || joyR.getRawAxis(1) < -.05){
			motorR.set(joyR.getRawAxis(1)*.95);
		}
	}
	public void lowTankDrive(int joyLPlace, int joyRPlace){
		joyL = new Joystick(joyLPlace);
		joyR = new Joystick(joyRPlace);
		
		if(joyL.getRawAxis(1) > .05 || joyL.getRawAxis(1) < -.05){
		motorL.set(joyL.getRawAxis(1)*.5);//set to 50% of joy y then set motor speed to that 
		}
		if(joyR.getRawAxis(1) > .05 || joyR.getRawAxis(1) < -.05){
		motorR.set(joyR.getRawAxis(1)*.5);
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
