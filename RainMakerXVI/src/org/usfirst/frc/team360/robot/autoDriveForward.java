package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class autoDriveForward extends CommandGroup {
    
    public  autoDriveForward() {
    	addSequential(new goForward(240));
    }
}
