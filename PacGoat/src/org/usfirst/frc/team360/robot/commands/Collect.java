package org.usfirst.frc.team360.robot.commands;

import org.usfirst.frc.team360.robot.subsystems.Collector;
import org.usfirst.frc.team360.robot.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Get the robot set to collect balls.
 */
public class Collect extends CommandGroup {
    public Collect() {
    	addSequential(new SetCollectionSpeed(Collector.FORWARD));
    	addParallel(new CloseClaw());
    	addSequential(new SetPivotSetpoint(Pivot.COLLECT));
    	addSequential(new WaitForBall());
    }
}
