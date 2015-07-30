package org.usfirst.frc.team360.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class velocityPID extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	double power;
	int positionError;
	public velocityPID(){
		
		
	}
	public double run(int position, double[] motionProfile, double[] constants){
		positionError = (int) motionProfile[2] - position;
		power = constants[0] * motionProfile[0] + constants[1] * motionProfile[1]+ constants[2] * positionError;
		if(power>1){
			power = 1;
		} else if(power<-1){
			power = -1;
		}
		return power;
		
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
