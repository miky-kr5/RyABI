/*
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * See the LICENSE file for more details.
 */

package ve.ucv.ciens.cicore.icaro.ryabi;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.CompassPilot;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.AvoidObstaclesBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.CatchBallBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.SearchBallBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.SensorCalibrationBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.VictoryBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.WanderBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.FeatureDetectorsHandler;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.FeatureDetectionListener;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.LightFeatureDetector;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.QuitButtonListener;

/**
 * This class starts the execution of the program.
 * 
 * @author Miguel Angel Astor Romero.
 */
@SuppressWarnings("deprecation")
public class RyABI {
	private static final float WHEEL_DIAMETER = 56.0f;
	private static final float TRACK_WIDTH = 144.0f;

	private static ArcRotateMoveController  pilot;
	private static UltrasonicSensor         sonar;
	private static TouchSensor              touch;
	private static CompassHTSensor          compass;
	private static LightSensor              light;
	private static Behavior[]               behaviors;
	private static Arbitrator               arbitrator;
	private static RangeFeatureDetector     rangeDetector;
	private static TouchFeatureDetector     touchDetector;
	private static LightFeatureDetector     lightDetector;
	private static FeatureDetectionListener featureListener;
	private static FeatureDetectorsHandler  detectorHandler;

	/**
	 * Main method of the program.
	 */
	public static void main(String[] args) {
		boolean invertLightDetector;
		/* Create the sensors. */
		sonar = new UltrasonicSensor(SensorPort.S1);
		touch = new TouchSensor(SensorPort.S2);
		compass = new CompassHTSensor(SensorPort.S3);
		light = new LightSensor(SensorPort.S4);

		invertLightDetector = invertLightDetector();

		/* Create the pilot. */
		pilot = new CompassPilot(compass, WHEEL_DIAMETER, TRACK_WIDTH, Motor.A, Motor.C);

		/* Create the feature detectors. */
		rangeDetector = new RangeFeatureDetector(sonar, 30, 200);
		touchDetector = new TouchFeatureDetector(touch);
		lightDetector = new LightFeatureDetector(light, invertLightDetector);

		detectorHandler = FeatureDetectorsHandler.getInstance();
		detectorHandler.setRangeDetector(rangeDetector);
		detectorHandler.setTouchDetector(touchDetector);
		detectorHandler.setLightDetector(lightDetector);
		detectorHandler.disableAllDetectors();

		featureListener = new FeatureDetectionListener();
		rangeDetector.addListener(featureListener);
		touchDetector.addListener(featureListener);
		lightDetector.addListener(featureListener);

		/* Register escape button for forced exit. */
		Button.ESCAPE.addButtonListener(new QuitButtonListener());

		/* Start the sensor calibration. */
		behaviors = new Behavior[1];
		behaviors[0] = new SensorCalibrationBehavior(sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		arbitrator = new Arbitrator(behaviors, true);
		arbitrator.start();

		/* Wait for the user to press the center button. */
		System.out.println("Sensors ready.");
		System.out.println("Press ENTER");
		System.out.println("to start.");
		Button.ENTER.waitForPress();

		/* Create the behaviors. */
		behaviors = new Behavior[5];
		behaviors[0] = new WanderBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		behaviors[1] = new VictoryBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		behaviors[2] = new SearchBallBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		behaviors[3] = new CatchBallBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		behaviors[4] = new AvoidObstaclesBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);

		/* Start the program. */
		arbitrator = new Arbitrator(behaviors, true);
		arbitrator.start();
	}

	/**
	 * Asks the user to select the light feature detection mode.
	 * 
	 * @return True if the user wants to invert the light detector (report when the robot passes from a dark area to a light area).
	 */
	private static boolean invertLightDetector() {
		int btnID;
		boolean invertSensor = false, done = false;

		while(!done) {
			/* Show the prompt on the screen. */
			LCD.clear();
			System.out.println("Invert l. sens:");
			if(invertSensor)
				System.out.println("<< YES >>");
			else
				System.out.println("<< NO  >>");
			System.out.println("Press ENTER");
			System.out.println("to set.");

			/* Wait for any button press. */
			btnID = Button.waitForAnyPress();

			switch(btnID) {
			case Button.ID_LEFT:
			case Button.ID_RIGHT:
				/* If the user pressed any direction button then toggle the sensor. */
				invertSensor = !invertSensor;
				break;
			case Button.ID_ENTER:
				/* If the user pressed enter then end the loop. */
				done = true;
				break;
			default:
				/* If the user pressed escape then quit. */
				System.exit(1);
			}
		}

		return invertSensor;
	}
}
