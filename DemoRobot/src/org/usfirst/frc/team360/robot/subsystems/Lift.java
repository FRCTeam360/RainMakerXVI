package org.usfirst.frc.team360.robot.subsystems;

import org.usfirst.frc.team360.robot.OI;
import org.usfirst.frc.team360.robot.RobotMap;
import org.usfirst.frc.team360.robot.commands.manLift;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	VictorSP motorLift;
	public Encoder liftEnc;
	public int position;
	public Lift(){
		motorLift = new VictorSP(RobotMap.liftMotorPort);
		liftEnc = new Encoder(RobotMap.liftEncoderPortOne, RobotMap.liftEncoderPortTwo, true, EncodingType.k1X);//inits and maps encoder)
		liftEnc.reset();//resets the encoder
		liftEnc.setMaxPeriod(.1);
		liftEnc.setMinRate(10);
		liftEnc.setDistancePerPulse(5);
		liftEnc.setSamplesToAverage(7);
	}
	public void lowManuelLift(){

		if(OI.OPJoy.getRawAxis(1) > .05 || OI.OPJoy.getRawAxis(1) < -.05){
			motorLift.set(OI.OPJoy.getRawAxis(1)*3);
		}
	}
	public int getEncoder(){
		position = liftEnc.get();
		return position;
	}
	public void resetEncoder(){
		liftEnc.reset();//resets the encoder
	}
	public void highManuelLift(){
		if(OI.OPJoy.getRawAxis(1) > .05 || OI.OPJoy.getRawAxis(1) < -.05){
			motorLift.set(OI.OPJoy.getRawAxis(1)*5);
		}
	}
	public void runLift(double speed) {
		motorLift.set(speed);
	}
	
	public void stop(){
		motorLift.stopMotor();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new manLift());
    }
}

