package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class autoGrabTurnLeft extends CommandGroup {

	public autoGrabTurnLeft()	{	
		
    	addSequential(new activeSol());
    	//addParallel(new enablePIDLift());//pid level 1 for auto
    	addSequential(new enablePIDTurnRight());
    	addSequential(new goForward(360));
    	
    	
    	
    }
}
