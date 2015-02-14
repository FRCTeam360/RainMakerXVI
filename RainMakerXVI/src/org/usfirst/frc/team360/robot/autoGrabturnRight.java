package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DigitalInput;
public class autoGrabturnRight {
/*public void autoGrabturnRight(){
	OI controls = new OI();
	Robot robotInit = new Robot();
		//limit1.get();
	    //limit2.get();
		
	int autoStage;
	
		double p = 0.001; 
		double i = 0;
		double d = 0.0015;
		double Dt = 0.01;
		
		//encValR = encoderR.get();                                                                                                                                        
		
	switch(autoStage){
			
			case 1: //grab
				
				controls.intakeSol.set(DoubleSolenoid.Value.kForward);
				
				autoStage = 2;
				
			case 2: // start lifting to driving level, switch to stage 3 if half way up
				
				controls.liftTarget = Const.liftLevel2;  
				
				Init.liftControl1();
				
				if (encValLift > Const.liftLevel2/ 2){
					
					autoStage = 3;
					
					encoderR.reset();
					encoderL.reset();
					
				}
			case 3:	//turn right while still maintaining lift
				
				controls.liftTarget = Const.liftLevel2;  
				
				liftControl1();
				
				encValL = encoderL.get();
				
				if (encValL < 720){ //!!!!!!!!!!!!!need to adjust value or run through distance calculator!!!!!!!!!!!!!!!!!!
					
					controls.myRobot.tankDrive(-0.3, 0.3);
					
				}
				
				else {
					
					controls.myRobot.tankDrive(0, 0);
					
					autoStage = 4;
					
				}	
		}*/
}
