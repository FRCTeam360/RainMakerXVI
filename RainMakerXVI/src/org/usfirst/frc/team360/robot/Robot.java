package org.usfirst.frc.team360.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

/*tank drive 2 Joysticks, left trigger engages down speed right trigger
 activates up speed
 driver has 2 joystick right, left each joystick controls that side, 
 left is mapped to port 0 right 1 
 on the OP console
 button 6 operates the lower sols
 press once to fire in again for out
 button 7 fire upper sols out
 button 9 fires upper sols in
 button 2 selects the turn grab tote and turn right auto // place with tote in robot
 button 3 selects the go forward grab bin and go back // place as close to bin as possible
 button 10 selects the go forward auto //place bot as close to autozone as possible
*/

public class Robot extends IterativeRobot {

/*	Command autonomousCommand;

	SendableChooser autoChooser;
*/
	
	Timer time;
	
	OI controls = OI.GetInstance(); // new OI();

	Encoder encoderLift;
	Encoder encoderR;
	Encoder encoderL;

	private int autoStage;
	private int iautochoose;
	private int x;
	
	private double error;
	private double integral;
	private double derivative;
	private double output;
	private double prevError;
	private double valJoyLI;
	private double encValLiftRaw;
	private double speedFactor;
	private double joyR;
	private double joyL;
	private double demVoltz;
	
	private float encValLift;
	private float encValR;
	private float encValL;
	private float quaterDist;
	private float threeQuarterDist;
	private float startPos;
	private float targetDistance;
	private float trueQuaterDist;
	private float trueThreeQuarterDist;

	private boolean liftPID;
	private boolean liftPIDLVL1;
	private boolean liftPIDLVL2;
	private boolean liftPIDLVL3;
	private boolean manOverRide;
	private boolean rWeThereYet;
	private boolean solFire;
	private boolean canSolFire1;
	private boolean canSolFire2;
	private boolean rUReadyToRumble;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	public void robotInit() {

		System.out.println("plz work ethernet cord m8");
		
		rWeThereYet = false;
		
		/*autoChooser = new SendableChooser();

		autoChooser.addDefault("default program", new testComAuto());
		autoChooser.addObject("left", new autoGrabTurnLeft());
		autoChooser.addObject("forward", new goForward(360));

		SmartDashboard.putData("Auto mode chooser", autoChooser);

		controls.init();*/
		
		encoderInits();
		
		time = new Timer();
		
		LiveWindow.run();

		controls.liftMotor.enableDeadbandElimination(true);

		iautochoose = 999;
		
		controls.lowerSolOut = true;
		controls.lowerSolIn = false;
		
	}

	public void encoderInits(){
		
		encoderLift = new Encoder(4, 5, true, EncodingType.k1X);//inits and maps encoder
		encoderR = new Encoder(2, 3, true, EncodingType.k1X);//ditto
		encoderL = new Encoder(0, 1, true, EncodingType.k1X);//ditto
		
		encoderLift.reset();//resets the encoder
		encoderR.reset();//ditto
		encoderL.reset();//ditto

		encoderLift.setMaxPeriod(.1);
		encoderR.setMaxPeriod(.1);
		encoderL.setMaxPeriod(.1);

		encoderLift.setMinRate(10);
		encoderR.setMinRate(10);
		encoderL.setMinRate(10);

		encoderLift.setDistancePerPulse(5);
		encoderR.setDistancePerPulse(5);
		encoderL.setDistancePerPulse(5);

		/* encoderLift.setReverseDirection(true);
		encoderR.setReverseDirection(true);*/
		
		encoderL.setReverseDirection(true);

		encoderLift.setSamplesToAverage(7);
		encoderR.setSamplesToAverage(7);
		encoderL.setSamplesToAverage(7);

	}
	
	public void disabledInit() {

		iautochoose = Const.iLeftRamp;// run left ramp by default
		
		controls.compressor.stop();// stops encoder

		controls.motorL.stopMotor();//stops motor
		controls.motorR.stopMotor();//ditto
		controls.liftMotor.stopMotor();//ditto

		SmartDashboard.putString("Robot Status: ", "Deactivated");//sets robot readout to disabled
		SmartDashboard.putString("Teleop Status: ", "");//clears readout
		SmartDashboard.putString("Autonomous Status: ", "");//ditto
		SmartDashboard.putString("Test Status: ", "");//ditto

	}

	public void disabledPeriodic() {

		resetLogic();

		//auto chooser
		
		autoChooser();

	}
	
	public void autoChooser(){
		
		if (controls.gamePad.getRawButton(Const.iLeftRamp) == true) {
			
			iautochoose = Const.iLeftRamp;
			
		}
		
		if (controls.gamePad.getRawButton(Const.iRightRamp) == true) {
			
			iautochoose = Const.iRightRamp;
			
		}

		if (controls.gamePad.getRawButton(Const.iautograbturnright) == true) {
			
			iautochoose = Const.iautograbturnright;
			
		}
		if (controls.gamePad.getRawButton(Const.iautoforwardliftback) == true) {
			
			iautochoose = Const.iautoforwardliftback;
			
		}
		if (controls.gamePad.getRawButton(Const.iautoforward) == true) {
			
			iautochoose = Const.iautoforward;
		
		}
		if(controls.gamePad.getRawButton(Const.istop) == true){
			
			iautochoose = Const.istop;
		}
		SmartDashboard.putString("iterativeautonomous", "");
		
		switch (iautochoose) {

		case 0:
			
			SmartDashboard.putString("iterativeautonomous", "Ramp Left");
			
		break;
		
		case Const.iLeftRamp:
			
			SmartDashboard.putString("iterativeautonomous", "Ramp Left");
			
		break;
		
		case Const.iautograbturnright:
			
			SmartDashboard.putString("iterativeautonomous", "grab tote turn right");
			
		break;
		case Const.iautoforwardliftback:
			
			SmartDashboard.putString("iterativeautonomous", "container BACK");
			
		break;
		case Const.iautoforward:
			
			SmartDashboard.putString("iterativeautonomous", "FORWARD");
			
		break;
		case Const.istop:
			
			SmartDashboard.putString("iterativeautonomous", "none selected");
			
		break;
		
		case Const.iRightRamp:
			
			SmartDashboard.putString("iterativeautonomous", "right ramp");
			
		break;
			
		}
	}
 	
	public void autonomousInit() {

		/*autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
*/
		autoStage = 1;//sets auto stage to start

		controls.compressor.start();//starts compressor

		encoderR.reset();//resets the encoders
		encoderL.reset();//ditto

		controls.autoLoopCounter = 0;

		SmartDashboard.putString("Autonomous Status: ", "Active");// sets auto readout to true

		/*controls.myRobot.drive(.5, -.5);
		
		Timer.delay(1.5);
	
		controls.myRobot.drive(0, 0);*/
	}
	
	/**
	 * This function is called periodically during autonomous
	 */

	public void autonomousPeriodic() {
			
		resetLogic();
		
		/*960 is apparrently a perfect right angle turn
		Scheduler.getInstance().run();
		 limit1.get();
		 limit2.get();*/
		
		double p = 0.001;
		double i = 0;
		double d = 0.0015;
		double Dt = 0.01;
		
		controls.EnvGlbValR = encoderR.get();
		controls.EnvGlbValL = encoderL.get();
		controls.EnvGlbValLift = encoderLift.get();

		switch (iautochoose) {			
		
		case Const.iRightRamp:
			rightRamp();
			break;
			
		case Const.iLeftRamp:
			leftRamp();
			break;
		
		case Const.iautograbturnright:
			
			auto1ToteRight();
			
			break;
			
		case Const.iautoforwardliftback:
			
			autoForwardliftBack();
			
			break;
			
		case Const.iautoforward:
			
			autoForward();
			
			break;
			
		}
		
		//rightRamp();
		
		/*Tote2Bin1();

		controls.myRobot.Drive.SetSaftleyEnabled(true);
		
		controls.myRobot.drive(.5, -.5);
		
		Timer.delay(1.5);
	
		controls.myRobot.drive(0, 0);
		*/
		
		}

	public void autoForwardliftBack() {

		encValR = encoderR.get();
		encValL = encoderL.get();
		encValLift = encoderLift.get();

		switch (autoStage) {

		case 1: // go forward 2 feet
			
			if (encValL > -300) {

				controls.motorL.set(.7);
				controls.motorR.set(-.7);
	

			} else {

				controls.motorL.set(-1);
				controls.motorR.set(1);

				//Timer.delay(.05);
				
				autoStage = 2;
				rWeThereYet = false;

				startPos = encValLift;
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				encoderR.reset();
				encoderL.reset();
				
				controls.liftTarget = Const.liftLevel2;
				
				targetDistance = controls.liftTarget - startPos;
			
			}
			
		break;
		
		case 2: /* start lifting to driving level, switch to stage 3 if half way
			 up
			targetDistance = controls.liftTarget - startPos;
			controls.liftTarget = Const.liftLevel2;
*/
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 3;

				encoderR.reset();
				encoderL.reset();

			}
			
		break;

		case 3: /* go backwards about nine feet to auto zone while still
			 maintaining lift
			 wheels are 4" in diameter, so 1 rotation is 12.56 inches,
			 approx 1 foot
			 so need to go about 8 rotations.
			 there are x encoder ticks per wheel rotation, based on the
			 gearing and 360 ticks per encoder rotation.
			 think 480 ticks per wheel rotation.		
			 big gear has 80 teeth, little gear has 60 teeth

		
			System.out.println(encValL);
			System.out.println(encValR);
	*/
			controls.motorL.set(-.4);
			controls.motorR.set(.4);
				
			if (encValL > 3300) {

				controls.motorL.set(1);
				controls.motorR.set(-1);
			
				/*controls.motorL.stopMotor();
				controls.motorR.stopMotor();*/

				autoStage = 4;
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				encoderR.reset();
				encoderL.reset();
				
				rWeThereYet = false;
				
				startPos = encValLift;
				
				encoderR.reset();
				encoderL.reset();
				
				controls.liftTarget = Const.liftLevelground;
				
				targetDistance = controls.liftTarget - startPos;
			}
				
			break;
			
			/*case 4:// lower container
			
				nonPIDLift();

				if (rWeThereYet == true) {

					autoStage = 5;

					encoderR.reset();
					encoderL.reset();

				}
				
			break;
		
			case 5: // back up tew foots
				
				controls.motorL.set(-.4);
				controls.motorR.set(.4);
			
				if (encValL > 400) {

					controls.motorL.set(.7);
					controls.motorR.set(-.7);
			
					controls.motorL.stopMotor();
					controls.motorR.stopMotor();
				
					autoStage = 99;

				}
			break;
*/
		}
	}
	
	public void autoForward() {

		encValR = encoderR.get();
		encValL = encoderL.get();
		encValLift = encoderLift.get();

		/*System.out.println(encValL);*/
		
		switch (autoStage) {

		case 1: // go forward 4 feet
			
			if (encValL < 1500) {

				controls.myRobot.tankDrive(-0.7, -0.7);

			} else {

				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 99;

			   }
			
			break;
			
			}
		}

	public void auto1ToteRight(){

		encValR = encoderR.get();
		encValL = encoderL.get();
		encValLift = encoderLift.get();

		switch (autoStage) { 	
		
		case 1:
			
			controls.upperIntakeSol.set(DoubleSolenoid.Value.kReverse);// release
			
			autoStage = 2;
			
			rWeThereYet = false;

			startPos = encValLift;
			
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
			
			encoderR.reset();
			encoderL.reset();
			
			controls.liftTarget = Const.liftLeveldrive;
			
			targetDistance = controls.liftTarget - startPos;
			
			autoStage = 2;
			
			
		break;
		
		case 2: /*start lifting to driving level, switch to stage 3 if half way
			 up
			targetDistance = controls.liftTarget - startPos;
			controls.liftTarget = Const.liftLevel2;
*/
		
			
			
			
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 3;
		
				encoderR.reset();
				encoderL.reset();

			}
	
		break;
		case 3: // go forward 2 feet
			
			if (encValL > -910) {

				controls.motorL.set(.7);
				
				if (encValR > -910) {

					controls.motorR.set(.7);
					
				}

			} else if (encValR > -910) { 

				controls.motorR.set(.7);
				
			} else {

				controls.motorL.set(-1);
				controls.motorR.set(-1);

				autoStage = 4;
				rWeThereYet = false;

				startPos = encValLift;
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				encoderR.reset();
				encoderL.reset();
				
				controls.liftTarget = Const.liftLevel2;
				
				targetDistance = controls.liftTarget - startPos;
			
			}
			
		break;
		case 4:/* go backwards about nine feet to auto zone while still
			 maintaining lift
			 wheels are 4" in diameter, so 1 rotation is 12.56 inches,
			 approx 1 foot
			 so need to go about 8 rotations.
			 there are x encoder ticks per wheel rotation, based on the
			 gearing and 360 ticks per encoder rotation.
			 think 480 ticks per wheel rotation.		
			 big gear has 80 teeth, little gear has 60 teeth
		
			System.out.println(encValL);
			System.out.println(encValR);*/
	
			controls.motorL.set(.4);
			controls.motorR.set(-.4);
				
			if (encValL < -4500) {

				controls.motorL.set(-1);
				controls.motorR.set(1);
			
				/*controls.motorL.stopMotor();
				controls.motorR.stopMotor();*/

				autoStage = 5;
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				encoderR.reset();
				encoderL.reset();
				
				rWeThereYet = false;
				
				startPos = encValLift;
				
				encoderR.reset();
				encoderL.reset();
				
				controls.liftTarget = Const.liftLevelground;
				
				targetDistance = controls.liftTarget - startPos;
			
			}
				
			break;	
		
			/*case 5:// lower container
			
				nonPIDLift();

				if (rWeThereYet == true) {

					autoStage = 6;

					encoderR.reset();
					encoderL.reset();

				}
				
			break;
			
			case 6:
				
				controls.upperIntakeSol.set(DoubleSolenoid.Value.kReverse);// reverse
				
				autoStage = 7;

				encoderR.reset();
				encoderL.reset();
				
				break;
			
			case 7: // back up tew foots
				
				controls.motorL.set(-.3);
				controls.motorR.set(.3);
			
				if (encValL > 400) {

					controls.motorL.set(.7);
					controls.motorR.set(-.7);
			
					controls.motorL.stopMotor();
					controls.motorR.stopMotor();
				
					autoStage = 99;

				}
				
			break;*/
		}
	}
 	
	public void Tote1Bin1Right(){
		
		encValR = encoderR.get();
		encValL = encoderL.get();
		encValLift = encoderLift.get();
		
		/*System.out.println(encValLift);
		System.out.println(encValL + "L");
		System.out.println(encValR +"R");*/
		
		switch (autoStage) { 	
		case 1:
		
			if (encValL > -100) {

				controls.motorL.set(.5);
				controls.motorR.set(-.5);
			
			}  else {

				controls.motorL.set(-1);
				controls.motorR.set(-1);

				autoStage = 2;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevel2;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
		break;
		case 2:
		
			/*  start lifting to driving level, switch to stage 3 if half way
			 up
			targetDistance = controls.liftTarget - startPos;
			controls.liftTarget = Const.liftLevel2;*/
			
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 3;
		
				encoderR.reset();
				encoderL.reset();

			}
			
		break;
		case 3:
			
			if(encValL > -300){
				
				controls.motorL.set(.5);
				controls.motorR.set(-.5);
				
			}else {

				controls.motorL.set(-1);
				controls.motorR.set(-1);

				autoStage = 4;
				rWeThereYet = false;

				startPos = encValLift;
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				encoderR.reset();
				encoderL.reset();
				
				controls.liftTarget = Const.liftLevelground;
				
				targetDistance = controls.liftTarget - startPos;
				
				rWeThereYet = false;
				
			}
		break;
		case 4:
			
			nonPIDLift();
			
			if(rWeThereYet == true){
				
				autoStage = 5;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
				
			}
		break;
		case 5:
			
			controls.upperIntakeSol.set(DoubleSolenoid.Value.kForward);// release
			
			autoStage = 6;
			rWeThereYet = false;

			startPos = encValLift;
			
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
			
			encoderR.reset();
			encoderL.reset();
			
			controls.liftTarget = Const.liftLeveldrive;
			
			targetDistance = controls.liftTarget - startPos;
			
		break;
		case 6:
			
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 7;
		
				encoderR.reset();
				encoderL.reset();

			}
		break;
		case 7:
			
			if (encValL < 910) {

				controls.motorL.set(-.7);
				
				if (encValR < -910) {

					controls.motorR.set(-.7);
					
				}

			} else if (encValR< 910) { 

				controls.motorR.set(-.7);
				
			} else {

				controls.motorL.set(-1);
				controls.motorR.set(-1);

				autoStage = 8;
				
				rWeThereYet = false;

				startPos = encValLift;
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				encoderR.reset();
				encoderL.reset();
				
				controls.liftTarget = Const.liftLevel2;
				
				targetDistance = controls.liftTarget - startPos;
			
			}
		break;
		case 8:
			
			/* go backwards about nine feet to auto zone while still
			 maintaining lift
			 wheels are 4" in diameter, so 1 rotation is 12.56 inches,
			 approx 1 foot
			 so need to go about 8 rotations.
			 there are x encoder ticks per wheel rotation, based on the
			 gearing and 360 ticks per encoder rotation.
			 think 480 ticks per wheel rotation.		
			 big gear has 80 teeth, little gear has 60 teeth
		
			System.out.println(encValL);
			System.out.println(encValR);*/
	
			controls.motorL.set(.4);
			controls.motorR.set(-.4);
				
			if (encValL < -4000) {

				controls.motorL.set(-1);
				controls.motorR.set(1);
			
				/*controls.motorL.stopMotor();
				controls.motorR.stopMotor();*/

				autoStage = 9;
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				encoderR.reset();
				encoderL.reset();
				
				rWeThereYet = false;
				
				startPos = encValLift;
				
				encoderR.reset();
				encoderL.reset();
				
				controls.liftTarget = Const.liftLevelground;
				
				targetDistance = controls.liftTarget - startPos;
			
			}	
		break;	
		case 9:// lower container
			
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 10;

				encoderR.reset();
				encoderL.reset();

			}
		break;
		case 10:
				
			controls.upperIntakeSol.set(DoubleSolenoid.Value.kReverse);// reverse

			autoStage = 11;

			encoderR.reset();
			encoderL.reset();
				
		break;
		case 11: // back up tew foots
				
			controls.motorL.set(-.3);
			controls.motorR.set(.3);
			
			if (encValL > 400) {

				controls.motorL.set(.7);
				controls.motorR.set(-.7);
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				autoStage = 99;

			}
				
		break;
		}
	}
	
	public void leftRamp(){
	
		encValR = encoderR.get();
		encValL = encoderL.get();
		encValLift = encoderLift.get();

		/*System.out.println(encValL);*/
		
		switch (autoStage) {

		case 1: // init
			
			rWeThereYet = false;

			startPos = encValLift;
		
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
		
			encoderR.reset();
			encoderL.reset();
		
			controls.liftTarget = Const.liftLevel2;
		
			targetDistance = controls.liftTarget - startPos;	
			
			autoStage = 2;
			
			break;
		case 2://lift rc
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 3;
		
				encoderR.reset();
				encoderL.reset();
				
				

			}
			break;
		
		case 3: //forward
			
			System.out.println(encValR);
			
			if (encValL > -650) {

				controls.motorL.set(.5);
				controls.motorR.set(-.5);
			
			}  else {

				controls.motorL.set(-.5);
				controls.motorR.set(.5);
				
				Timer.delay(.075);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				autoStage = 4;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
		break;
		case 4://center
			controls.lowerIntakeSol.set(DoubleSolenoid.Value.kReverse);// release
			
			autoStage = 8;
			rWeThereYet = false;

			startPos = encValLift;
			
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
			
			encoderR.reset();
			encoderL.reset();
			
			controls.liftTarget = 25;
			
			targetDistance = controls.liftTarget - startPos;
			
			//controls.lowerIntakeSol.set(DoubleSolenoid.Value.kForward);// release
		break;
		/*case 5://down
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 6;
		
				encoderR.reset();
				encoderL.reset();
				
				

			}
			break;
		case 6://pinch
			Timer.delay(.03);
			controls.upperIntakeSol.set(DoubleSolenoid.Value.kReverse);// release
			
			autoStage = 8;
			rWeThereYet = false;

			startPos = encValLift;
			
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
			
			encoderR.reset();
			encoderL.reset();
			
			controls.liftTarget = Const.liftLeveldrive;
			
			targetDistance = controls.liftTarget - startPos;
			
		
		break;
		case 7://up
			nonPIDLift();
System.out.println(encValL);
			Timer.delay(.6);

				autoStage = 8;
		
				encoderR.reset();
				encoderL.reset();

			
		break;*/
		case 8://turn with tote
			System.out.println(encValL);
			
			if (encValL > -655) {

				controls.motorL.set(.5);
				//controls.motorR.set(.5);
			
				if (encValL > -655) {

					//controls.motorL.set(.5);
					controls.motorR.set(.5);
				
				}
				
			}  else {

				if (encValL > -655) {

					//controls.motorL.set(.5);
					controls.motorR.set(.5);
				
				}
				Timer.delay(.2);
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 9;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
			
		break;
		case 10://unpinch lower
			
			controls.lowerIntakeSol.set(DoubleSolenoid.Value.kForward);// release
			
			autoStage = 12;
			rWeThereYet = false;

			startPos = encValLift;
			
			encoderR.reset();
			encoderL.reset();
			
			controls.liftTarget = Const.liftLevel2;
			
			targetDistance = controls.liftTarget - startPos;
			
		break;
			
		case 9://back with tote
			
			//System.out.println(encValR);
			
			if (encValL < 100) {

				controls.motorL.set(-.5);
				controls.motorR.set(.5);
			
			}  else {

				/*controls.motorL.set(.5);
				controls.motorR.set(-.5);
				
				Timer.delay(.075);*/
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 10;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevel2;
			
				targetDistance = controls.liftTarget - startPos;
		
		}
			
		//break;
	/*	case 11://lift
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 12;
		
				encoderR.reset();
				encoderL.reset();
				
				

			}
			break;*/
		case 12://back
			//System.out.println(encValR);
			
			if (encValL < 900) {

				controls.motorL.set(-.5);
				controls.motorR.set(.5);
			
			}  else {

				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 13;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
		break;
		case 13://turn 3
			System.out.println(encValL);
			
			if (encValL < 1200) {

				controls.motorL.set(-.5);
				//controls.motorR.set(.5);
			
				if (encValL < 1200) {

					//controls.motorL.set(.5);
					controls.motorR.set(-.5);
				 
				}
				
			}  else {

				if (encValL < 1200) {

					//controls.motorL.set(.5);
					controls.motorR.set(-.5);
				
				}
				
				/*controls.motorL.set(.5);
				controls.motorR.set(.5);
				
				Timer.delay(.2);*/
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 14;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
			break;
		case 14://drive @90
			if (encValL > -1100) {

				controls.motorL.set(.5);
				controls.motorR.set(-.5);
			
			}  else {

				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 15;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}
			break;
		case 15://turn to positioning turn
			System.out.println(encValL);
			
			if (encValL > -1100) {

				controls.motorL.set(.5);
				//controls.motorR.set(.5);
			
				if (encValL > -1100) {

					//controls.motorL.set(.5);
					controls.motorR.set(.5);
				
				}
				
			}  else {

				if (encValL > -1100) {

					//controls.motorL.set(.5);
					controls.motorR.set(.5);
				
				}
				/*controls.motorL.set(.5);
				controls.motorR.set(.5);
				
				Timer.delay(.005);
				*/
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 16;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
			break;
		case 16://positioning drive
			if (encValL > -1000) {

				controls.motorL.set(.5);
				controls.motorR.set(-.5);
			
			}  else {

				/*controls.motorL.set(-.5);
				controls.motorR.set(.5);
				
				Timer.delay(.075);*/
				
				controls.lowerIntakeSol.set(DoubleSolenoid.Value.kReverse);// release
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 17;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
			break;
		case 17: //last turn
//System.out.println(encValL);
			
			if (encValL > -1100) {

				controls.motorL.set(.5);
				//controls.motorR.set(.5);
			
				if (encValL > -1100) {

					//controls.motorL.set(.5);
					controls.motorR.set(.5);
				
				}
				
			}  else {

				if (encValL > -1100) {

					//controls.motorL.set(.5);
					controls.motorR.set(.5);
				
				}
				/*controls.motorR.set(-.5);
				controls.motorL.set(-.5);*/
				Timer.delay(.2);
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 18;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
			break;
		case 18://hit into wall
			if (encValL > -2400) {

				controls.motorL.set(.5);
				controls.motorR.set(-.5);
			
			}  else {

				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 19;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
			break;
		}
	}

	public void rightRamp(){
		encValR = encoderR.get();
		encValL = encoderL.get();
		encValLift = encoderLift.get();

		/*System.out.println(encValL);*/
		
		switch (autoStage) {

		case 1: // init
			
			rWeThereYet = false;

			startPos = encValLift;
		
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
		
			encoderR.reset();
			encoderL.reset();
		
			controls.liftTarget = Const.liftLevel2;
		
			targetDistance = controls.liftTarget - startPos;	
			
			autoStage = 2;
			
			break;
		case 2://lift rc
			nonPIDLift();

			if (rWeThereYet == true) {

				autoStage = 3;
		
				encoderR.reset();
				encoderL.reset();
				
			}
			break;
		
		case 3: //forward
			
			//System.out.println(encValR);
			
			if (encValL > -550) {

				controls.motorL.set(.5);
				controls.motorR.set(-.5);
			
			}  else {

				/*controls.motorL.set(-.5);
				controls.motorR.set(.5);*/
				
				//Timer.delay(.075);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				autoStage = 4;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
		}	
		break;
		case 4://center
			controls.lowerIntakeSol.set(DoubleSolenoid.Value.kReverse);// release
			
			autoStage = 5;
			rWeThereYet = false;

			startPos = encValLift;
			
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
			
			encoderR.reset();
			encoderL.reset();
			
			controls.liftTarget = 25;
			
			targetDistance = controls.liftTarget - startPos;
			
			//controls.lowerIntakeSol.set(DoubleSolenoid.Value.kForward);// release
		break;
		case 5://turn w/ tote
			if (encValL > -1700) {

				controls.motorL.set(.5);
				
				//controls.motorR.set(.5);
			
				if (encValL > -1700) {

					//controls.motorL.set(.5);
					
					controls.motorR.set(.5);
				
				}
				
			}  else {

				if (encValL > -1700) {

					//controls.motorL.set(.5);
				
					controls.motorR.set(.5);
				
				}
				
				//Timer.dely(.2);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 6;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
			}
			break;
		case 6:
			//center
			controls.lowerIntakeSol.set(DoubleSolenoid.Value.kForward);// release
			
			autoStage = 7;
			rWeThereYet = false;

			startPos = encValLift;
			
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
			
			encoderR.reset();
			encoderL.reset();
			
			controls.liftTarget = 7;
			
			targetDistance = controls.liftTarget - startPos;
			
			//controls.lowerIntakeSol.set(DoubleSolenoid.Value.kForward);// release
			
		break;
		case 7://back
			
			if (encValL <1200) {

				controls.motorL.set(-.5);
				controls.motorR.set(.5);
			
			}  else {

				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 8;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
			}
		break;
		case 8:
			if (encValL > -455) {

				controls.motorL.set(.5);
				
				//controls.motorR.set(.5);
			
				if (encValL > -455) {

					//controls.motorL.set(.5);
					
					controls.motorR.set(.5);
				
				}
				
			}  else {

				if (encValL > -455) {

					//controls.motorL.set(.5);
				
					controls.motorR.set(.5);
				
				}
				
				//Timer.delay(.2);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 9;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
			}
		break;
		case 9://forward // to tote
			if (encValL > -1700) {

				controls.motorL.set(.5);
				controls.motorR.set(-.5);
			
			}  else {

				/*controls.motorL.set(-.5);
				controls.motorR.set(.5);*/
				
				//Timer.delay(.075);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				autoStage = 10;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
			}
		break;
		case 10:
			if (encValL > -655) {

				controls.motorL.set(.5);
				
				//controls.motorR.set(.5);
			
				if (encValL > -655) {

					//controls.motorL.set(.5);
					
					controls.motorR.set(.5);
				
				}
				
			}  else {

				if (encValL > -655) {

					//controls.motorL.set(.5);
				
					controls.motorR.set(.5);
				
				}
				
				//Timer.delay(.2);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 11;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
			}
		break;
		case 11:
			
			if (encValL < 1800) {

				controls.motorL.set(-.5);
				controls.motorR.set(.5);
			
			}  else {

				/*controls.motorL.set(-.5);
				controls.motorR.set(.5);*/
				
				//Timer.delay(.075);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				autoStage = 100;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
			}
			
		break;
		case 12:
			if (encValL > -900) {

				controls.motorL.set(.5);
				
				//controls.motorR.set(.5);
			
				if (encValL > -900) {

					//controls.motorL.set(.5);
					
					controls.motorR.set(.5);
				
				}
				
			}  else {

				if (encValL > -900) {

					//controls.motorL.set(.5);
				
					controls.motorR.set(.5);
				
				}
				
				//Timer.delay(.2);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();

				autoStage = 13;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
			}
		break;
		case 13:
			controls.lowerIntakeSol.set(DoubleSolenoid.Value.kReverse);// release
			
			autoStage = 14;
			rWeThereYet = false;

			startPos = encValLift;
			
			controls.motorL.stopMotor();
			controls.motorR.stopMotor();
			
			encoderR.reset();
			encoderL.reset();
			
			controls.liftTarget = 25;
			
			targetDistance = controls.liftTarget - startPos;
			
			//controls.lowerIntakeSol.set(DoubleSolenoid.Value.kForward);// release
		
		break;
		case 14:
			
			if (encValL > -700) {

				controls.motorL.set(.5);
				controls.motorR.set(-.5);
			
			}  else {

				/*controls.motorL.set(-.5);
				controls.motorR.set(.5);*/
				
				//Timer.delay(.075);
				
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
				
				autoStage = 15;
				rWeThereYet = false;

				startPos = encValLift;
			
				controls.motorL.stopMotor();
				controls.motorR.stopMotor();
			
				encoderR.reset();
				encoderL.reset();
			
				controls.liftTarget = Const.liftLevelground;
			
				targetDistance = controls.liftTarget - startPos;
		
			}
		break;
		}
	}
	
	public void teleopInit() {

		controls.compressor.start();//start compressor

		liftPID = false;//set lift pid to false
		manOverRide = false;// set manuel override to false

		//encoderLift.reset();// resets encoder
		
		encoderR.reset();// resets encoders
		encoderL.reset();//ditto

		SmartDashboard.putString("Teleop Status: ", "Active");//sets teleop readout to true

		SmartDashboard.putString("Lift Status: ", "");// clears vals of smart window

	}

	/**
	 * This function is called periodically during operator control
	 */

	public void teleopPeriodic() {

		//time.start();
		
		
		encValLift = encoderLift.get();
		
		encValL = encoderL.get();
		encValR = encoderR.get();
		
		System.out.println(encValLift + " L");
		
		System.out.println(encValL + " Lift");
		
		System.out.println(encValR + " R");
		
		//TeleopEncReset();//resets the encoder
		 
		intakeControl();//solenoid/tote intake control

		resetLogic();//the emergency button reset
		
		manualLift();//joystick lift control

		//liftTF();//decides manuel lift or button lift

		doubleJoystickTankDrive();// drive shifter stuff

		//singleJoystickTankDrive();
		
		Timer.delay(0.005); /* wait for motor update time

		setTarget();
		System.out.println(liftPID + "Lift Tr place");
		
		System.out.println(encValLift + " L");*/
		
	}

	public void doubleJoystickTankDrive() {

		controls.valJoyR = controls.stickR.getRawAxis(1);
		controls.valJoyL = controls.stickL.getRawAxis(1);

		controls.up = controls.stickR.getRawButton(1);
		controls.down = controls.stickL.getRawButton(1);
		
		if (controls.valJoyL >= .001 || controls.valJoyL <= -.001) {//left buffer logic

			// System.out.println("tank active");

			if (controls.up == true && controls.down == false) {//shifter button logic

				controls.halfSpeed = true;

			} else if (controls.down == true && controls.up == false) {//ditto

				controls.halfSpeed = false;

			}

			if (controls.halfSpeed == false) {//shifter speed logic

				fullSpeedDriveL();

				SmartDashboard.putString("Drive Status: ", "Full");

			} else if (controls.halfSpeed == true) {//ditto

				halfSpeedDriveL();

				SmartDashboard.putString("Drive Status: ", "Half");

			}
		}
		if (controls.valJoyR >= .001 || controls.valJoyR <= -.001) {//right buffer logic

			// System.out.println("tank active");

			if (controls.up == true && controls.down == false) {//shifter button logic

				controls.halfSpeed = true;

			} else if (controls.down == true && controls.up == false) {//ditto

				controls.halfSpeed = false;

			}
			if (controls.halfSpeed == false) {//shifter speed logic

				fullSpeedDriveR();

				SmartDashboard.putString("Drive Status: ", "Full");

			} else if (controls.halfSpeed == true) {//ditto

				halfSpeedDriveR();

				SmartDashboard.putString("Drive Status: ", "Half");

			}
		}
	}
	
	public void intakeControl() {

		controls.upperGrab = controls.gamePad.getRawButton(Const.inBtn);
		controls.upperRelease = controls.gamePad.getRawButton(Const.outBtn);
		controls.lowerRelease = controls.gamePad.getRawButton(Const.lowInBtn);
		
		if (controls.upperGrab == true && controls.upperRelease == false) {

			controls.upperIntakeSol.set(DoubleSolenoid.Value.kReverse);// grab
		
			SmartDashboard.putString("Solenoid Status: ", "Forward");
			
			/*controls.lowerIntakeSol.set(DoubleSolenoid.Value.kReverse);
			
			SmartDashboard.putString("Lower Solenoid Status: ", "Reverse");
			*/
			controls.lowerSolOut = false;
			controls.lowerSolIn = true;

		} else if (controls.upperRelease == true && controls.upperGrab == false) {

			controls.upperIntakeSol.set(DoubleSolenoid.Value.kForward);// reverse
			
			SmartDashboard.putString("Solenoid Status: ", "Reverse");

		}
		if (controls.lowerRelease == true) {
			if(controls.lowerSolOut == true){
				
				controls.lowerIntakeSol.set(DoubleSolenoid.Value.kForward);// grab
				
				SmartDashboard.putString("Lower Solenoid Status: ", "Forward");
				
				//holUp(30);
				
				Timer.delay(.17);
				
				//if(rUReadyToRumble = true){ 

					controls.lowerSolOut = false;
					controls.lowerSolIn = true;
				//	rUReadyToRumble = false;
							
					//time.reset();
					//time.stop();
					
			//	}
			} else if(controls.lowerSolIn == true){
				
				controls.lowerIntakeSol.set(DoubleSolenoid.Value.kReverse);// grab
				
				SmartDashboard.putString("Lower Solenoid Status: ", "Reverse");
				
				Timer.delay(.17);
				
				//holUp(30);
				
				//if (rUReadyToRumble = true){
				
				controls.lowerSolOut = true;
				controls.lowerSolIn = false;
				//rUReadyToRumble = false;
				
				//time.reset();
				//time.stop();
				
				//}
			}	
		}
	}

	public void liftTF() {
		
		if (controls.gamePad.getRawButton(Const.overideBtn)
				&& manOverRide == false) {

			manOverRide = true;

		} else if (controls.gamePad.getRawButton(Const.overideBtn)
				&& manOverRide == true) {

			manOverRide = false;

		}

		if (manOverRide == true) {

			manualLift();
		
		}else if (manOverRide == false) {

			nonPIDLift();

		}
	}

	public void fullSpeedDriveL() {

		joyL = controls.stickL.getRawAxis(1);

		Timer.delay(0.005);

		joyL *= .95;
		
		//controls.myRobot.tankDrive(-joyR, -joyL);

		controls.motorR.set(joyL);

	}

	public void halfSpeedDriveL() {

		joyL = controls.stickL.getRawAxis(1);

		Timer.delay(0.005);

		joyL *= .7;

		//controls.myRobot.tankDrive(-joyR, -joyL);

		controls.motorR.set(joyL);

	}

	public void fullSpeedDriveR() {

		joyR = controls.stickR.getRawAxis(1);
	
		Timer.delay(0.005);

		joyR *= .95;
		
		//controls.myRobot.tankDrive(-joyR, -joyL);

		controls.motorL.set(-joyR);

	}

	public void halfSpeedDriveR() {

		joyR = controls.stickR.getRawAxis(1);

		Timer.delay(0.005);

		joyR *= .7;
		
		//controls.myRobot.tankDrive(-joyR, -joyL);

		controls.motorL.set(-joyR);
		
	}

	public void setTarget() {

		if (controls.gamePad.getRawButton(Const.level1Btn) == true
				&& controls.gamePad.getRawButton(Const.level2Btn) == false
				&& controls.gamePad.getRawButton(Const.level3Btn) == false
				&& controls.gamePad.getRawButton(Const.drivingLevelBtn) == false
				&& controls.gamePad.getRawButton(Const.groundBtn) == false
				&& controls.gamePad.getRawButton(Const.level4Btn) == false) {
			
			rWeThereYet = false;
			liftPID = true;

			startPos = encValLift;

			controls.liftTarget = Const.liftLevel1;
			
			targetDistance = controls.liftTarget - startPos;
			
			SmartDashboard.putString("Lift Status: ", "Level 1");

		} else if (controls.gamePad.getRawButton(Const.level1Btn) == false
				&& controls.gamePad.getRawButton(Const.level2Btn) == true
				&& controls.gamePad.getRawButton(Const.level3Btn) == false
				&& controls.gamePad.getRawButton(Const.drivingLevelBtn) == false
				&& controls.gamePad.getRawButton(Const.groundBtn) == false
				&& controls.gamePad.getRawButton(Const.level4Btn) == false) {
			
			rWeThereYet = false;
			liftPID = true;

			startPos = encValLift;

			controls.liftTarget = Const.liftLevel2;
			
			targetDistance = controls.liftTarget - startPos;
			
			SmartDashboard.putString("Lift Status: ", "Level 2");

		} else if (controls.gamePad.getRawButton(Const.level1Btn) == false
				&& controls.gamePad.getRawButton(Const.level2Btn) == false
				&& controls.gamePad.getRawButton(Const.level3Btn) == true
				&& controls.gamePad.getRawButton(Const.drivingLevelBtn) == false
				&& controls.gamePad.getRawButton(Const.groundBtn) == false
				&& controls.gamePad.getRawButton(Const.level4Btn) == false) {
			
			rWeThereYet = false;
			liftPID = true;

			startPos = encValLift;

			controls.liftTarget = Const.liftLevel3;
			
			targetDistance = controls.liftTarget - startPos;
			
			SmartDashboard.putString("Lift Status: ", "Lift Level Drive");

		} else if (controls.gamePad.getRawButton(Const.level1Btn) == false
				&& controls.gamePad.getRawButton(Const.level2Btn) == false
				&& controls.gamePad.getRawButton(Const.level3Btn) == false
				&& controls.gamePad.getRawButton(Const.drivingLevelBtn) == true
				&& controls.gamePad.getRawButton(Const.groundBtn) == false
				&& controls.gamePad.getRawButton(Const.level4Btn) == false) {
			
			rWeThereYet = false;
			liftPID = true;

			startPos = encValLift;

			controls.liftTarget = Const.liftLeveldrive;
			
			targetDistance = controls.liftTarget - startPos;
			
			SmartDashboard.putString("Lift Status: ", "Level Drive");
			
		} else if (controls.gamePad.getRawButton(Const.level1Btn) == false
				&& controls.gamePad.getRawButton(Const.level2Btn) == false
				&& controls.gamePad.getRawButton(Const.level3Btn) == false
				&& controls.gamePad.getRawButton(Const.drivingLevelBtn) == false
				&& controls.gamePad.getRawButton(Const.groundBtn) == false
				&& controls.gamePad.getRawButton(Const.level4Btn) == true) {
			
			rWeThereYet = false;
			liftPID = true;

			startPos = encValLift;

			controls.liftTarget = Const.liftLevel4;
			
			targetDistance = controls.liftTarget - startPos;
			
			SmartDashboard.putString("Lift Status: ", "Level 4");

		} else if (controls.gamePad.getRawButton(Const.level1Btn) == false
				&& controls.gamePad.getRawButton(Const.level2Btn) == false
				&& controls.gamePad.getRawButton(Const.level3Btn) == false
				&& controls.gamePad.getRawButton(Const.drivingLevelBtn) == false
				&& controls.gamePad.getRawButton(Const.groundBtn) == true
				&& controls.gamePad.getRawButton(Const.level4Btn) == false) {

			rWeThereYet = false;

			liftPID = true;

			startPos = encValLift;

			canSolFire1 = setSpeedDown();
			canSolFire2 = setSpeedUp();

			/* if (canSolFire1 = true){
			  
			 controls.intakeSol1.set(DoubleSolenoid.Value.kForward);//release
			 controls.intakeSol2.set(DoubleSolenoid.Value.kForward);//release
			  
			 } else if (canSolFire2 = true){
			  
			 controls.intakeSol1.set(DoubleSolenoid.Value.kForward);//release
			 controls.intakeSol2.set(DoubleSolenoid.Value.kForward);//release
			  
			  }*/
			
			controls.liftTarget = Const.liftLevelground;
			
			targetDistance = controls.liftTarget - startPos;

		}
	}

	public void manualLift() {
		
		encValLift = encoderLift.get();
		
		valJoyLI = controls.joystickManLift.getRawAxis(1);
		
		if(controls.joystickManLift.getRawButton(2) == true){//if side grey button is pushed
			if(x == 0){// holding logic runs once
				
				controls.EnvGlbValLift = encoderLift.get();
				
				x++;
			}
			
			//System.out.println(controls.EnvGlbValLift + "thingy");
			
			if(controls.EnvGlbValLift > encValLift ){// move to keep the same encoder count so you bounce
				
				controls.liftMotor.set(-.3);
			
			} else if(controls.EnvGlbValLift < encValLift){
				
				controls.liftMotor.set(.3);
			
			}
		}else {//uses normal control
			
			 if(encValLift > 25){  
				
				System.out.println("hellx");
				
				controls.liftMotor.set(.1);
				
			} else {
				if(controls.joystickManLift.getRawButton(1) == false){
					if (valJoyLI >= .1 || valJoyLI <= -.1) {

						controls.liftMotor.set(-valJoyLI * .6);
					
					} else {
					
						controls.liftMotor.stopMotor();
					
			
					}
				} else if (controls.joystickManLift.getRawButton(1) == true){
					if (valJoyLI >= .1 || valJoyLI <= -.1) {

						controls.liftMotor.set(-valJoyLI * .9);
					
					} else {
					
						controls.liftMotor.stopMotor();
					
			
					}
				}
			}
			x =0;
		}
		//}
	//}
		
		 

		
		/*
		 * if (controls.gamePad.getRawButton(Const.upBtn) == true) {
		 * 
		 * controls.liftMotor.set(.6);
		 * 
		 * System.out.println( "Manuel override up");
		 * 
		 * } else if (controls.gamePad.getRawButton(Const.downBtn) == true) {
		 * 
		 * controls.liftMotor.set(-.2);
		 * 
		 * System.out.println( "Manuel override down");
		 * 
		 * } else {
		 * 
		 * controls.liftMotor.set(0); }
		 */
	}
  
	/**
	 * This function is called periodically during test mode
	 */

	public void testInit(){
		
		encoderR.reset();// resets the encoders
		encoderL.reset();//ditto
		encoderLift.reset();//ditto
		
	}
	
	public void testPeriodic() {
		
		encValLift = encoderLift.get();
		encValL = encoderL.get();
		encValR = encoderR.get();
		
		/*System.out.println(encValL + "L");
		System.out.println(encValLift);
		System.out.println(encValR + "R");
	
		LiveWindow.run();*/

		SmartDashboard.putString("Test Status: ", "Enabled");

	}

	public void nonPIDLift() {

		if (rWeThereYet == false) {
			if (targetDistance > 0) {// if target pos means we go up
				if (encValLift >= controls.liftTarget) {
					
					controls.liftMotor.stopMotor();
					
					rWeThereYet = true;
					
				} else {
					
					setSpeedUp();
					
				}
			} else if (targetDistance < 0) { // if target negative go down

				if (encValLift <= controls.liftTarget) {
					
					controls.liftMotor.stopMotor();
					
					rWeThereYet = true;
					
				} else {
					
					setSpeedDown();
					
				}
			} else if (targetDistance == 0) {
				
				controls.liftMotor.set(0);
				
				rWeThereYet = true;
				
			}
		}
	}

	public boolean setSpeedUp() {

		trueQuaterDist = controls.liftTarget / 4;
		trueThreeQuarterDist = quaterDist * 3;
		threeQuarterDist = trueThreeQuarterDist + startPos;
		quaterDist = trueQuaterDist + startPos;
		
		if (quaterDist > encValLift) {// if 0-25%

			controls.liftMotor.set(-.60);

		} else if (threeQuarterDist > encValLift && quaterDist <= encValLift) {// if 25-75%

			controls.liftMotor.set(-.60);

		} else if (threeQuarterDist <= encValLift
				&& controls.liftTarget >= encValLift) {// if 75-100%

			controls.liftMotor.set(-.3);

		} else {
			
			controls.liftMotor.stopMotor();
			
			solFire = true;
			
		}
		return solFire;
	}

	public boolean setSpeedDown() {

		trueQuaterDist = targetDistance / 4;
		trueThreeQuarterDist = quaterDist * 3;
		threeQuarterDist = trueThreeQuarterDist + startPos;
		quaterDist = trueQuaterDist + startPos;
		
		if (quaterDist < encValLift) {

			controls.liftMotor.set(.7);

		} else if (threeQuarterDist < encValLift && quaterDist >= encValLift) {

			controls.liftMotor.set(.9);

		} else if (threeQuarterDist >= encValLift
				&& controls.liftTarget <= encValLift) {

			controls.liftMotor.set(.4);

		} else {
			
			controls.liftMotor.stopMotor();
			
			solFire = true;
		
		}
		
		return solFire;

	}

	private static Robot instance;

	public static Robot GetInstance() {
		
		if (instance == null) {
			
			instance = new Robot();
		
		}
		
		return instance;
		
	}

	public void TeleopEncReset(){

		encoderL.reset();
		encoderR.reset();
	
	}

	public void resetLogic(){
		
		if(controls.gamePad.getRawButton(1) == true && controls.joystickManLift.getRawButton(5) == true && controls.joystickManLift.getRawButton(10) == true){
			
			encoderLift.reset();
			encoderL.reset();
			encoderR.reset();
			
		}
	}

	public boolean holUp(double howLong){
		
		//time.start();
		
		/*if (time.get() > howLong){
			
			rUReadyToRumble = true;
		
		} else {
		
			rUReadyToRumble = false;
		
		}*/
		
		return rUReadyToRumble;
		
	}
		
	public void singleJoystickTankDrive(){
		
		//joystick must be mapped to port 1
		//FOR EMERGENCIES ONLY
		//MUST USE DUAL JOYSTICK/GAMEPAD-THINGY
		controls.valJoyR = controls.stickR.getRawAxis(1);
		controls.valJoyL = controls.stickR.getRawAxis(3);
		joyR = controls.stickR.getRawAxis(1);
		joyL = controls.stickR.getRawAxis(3);
		
		controls.up = controls.stickR.getRawButton(7);
		controls.down = controls.stickR.getRawButton(8);
		
		if (controls.up == true && controls.down == false) {

			controls.halfSpeed = true;

		} else if (controls.down == true && controls.up == false) {

			controls.halfSpeed = false;

		}
		
		if (controls.halfSpeed == false) {

			speedFactor = .95;

			SmartDashboard.putString("Drive Status: ", "Full");
			
		} else if (controls.halfSpeed == true) {

			speedFactor = .7;

			SmartDashboard.putString("Drive Status: ", "Half");

		}
		
		if (controls.valJoyL >= .01 || controls.valJoyL <= -.01) {

			// System.out.println("tank active");

			controls.motorL.set(joyL);
			
			SmartDashboard.putDouble("JoyL: ", joyL);
			
		} else {
			
			controls.motorL.stopMotor();
			
		}
		
		if (controls.valJoyR >= .01 || controls.valJoyR <= -.01) {
 
			// System.out.println("tank active");

			controls.motorR.set(-joyR);
			
			SmartDashboard.putDouble("JoyR: ", joyR);
			
		} else {
			
			controls.motorR.stopMotor();
			
		}
	} 
}