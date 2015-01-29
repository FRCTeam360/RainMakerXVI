package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.Joystick; //imports basic joystick package
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

public class BasicJoystickControl {
	
	 public void Joystick() {
		 
		 Joystick Joy1; //initilize the joystick
		 
		 Joy1 = new Joystick(1); //maps joystick to port
		 
		 
		 
	    }
	
	 public void JoystickOpt1() { // joystick value taking 1
		 
		 Joystick JoyOpt1; 
		 
		 JoyOpt1 = new Joystick(1); 
		 
		 double JoyOpt1OutPut;// finds joystick output on scale from 1 to -1 and buttons on pushed/not pushed
		 
		 //simpler than method 2 but only allows for 5 of 6 axis  and 2 of 12 buttons
		 
		 //for joystick 1Y is forward -1Y is backwards/ 1X is right -1X is backwards/ 1Z is twist 
		 
		 JoyOpt1OutPut = JoyOpt1.getX(); //finds the output of side-to-side
		 JoyOpt1OutPut = JoyOpt1.getY(); //finds the output of forward/ backwards
		 JoyOpt1OutPut = JoyOpt1.getZ(); //finds the output of 
		 JoyOpt1OutPut = JoyOpt1.getThrottle(); //finds the output of flappy thingy 
		 JoyOpt1OutPut = JoyOpt1.getTwist(); //finds the output of x axis
		 JoyOpt1OutPut = JoyOpt1.getX(); //finds the output of x axis

		 
	    }

public void JoystickOpt2() { // joystick value taking 2
		 
		 Joystick JoyOpt2;
		 
		 JoyOpt2 = new Joystick(2);
		 
		 
		 
	    }
	 
}
