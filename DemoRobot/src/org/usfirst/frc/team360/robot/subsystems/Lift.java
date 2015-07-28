package org.usfirst.frc.team360.robot.subsystems;

import org.usfirst.frc.team360.robot.RobotMap;

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
	Joystick OPJoy;
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
	public void lowManuelLift(int OPJoyPlace){
		OPJoy = new Joystick(OPJoyPlace);
		if(OPJoy.getRawAxis(1) > .05 || OPJoy.getRawAxis(1) < -.05){
			motorLift.set(OPJoy.getRawAxis(1)*5);
		}
	}
	public int getEncoder(){
		position = liftEnc.get();
		return position;
	}
	public void resetEncoder(){
		liftEnc.reset();//resets the encoder
	}
	public void highManuelLift(int OPJoyPlace){
		OPJoy = new Joystick(OPJoyPlace);
		if(OPJoy.getRawAxis(1) > .05 || OPJoy.getRawAxis(1) < -.05){
			motorLift.set(OPJoy.getRawAxis(1)*9);
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
        //setDefaultCommand(new MySpecialCommand());
    }
}

