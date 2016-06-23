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

package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * This class implements a {@link BaseBehavior} that performs automatic calibration of the {@link CompassHTSensor} and {@link LightSensor}.
 * 
 * @author Miguel Angel Astor Romero.
 */
public class SensorCalibrationBehavior extends BaseBehavior {
	private boolean sensorsCalibrated;

	/**
	 * Creates a new {@link SensorCalibrationBehavior}
	 * 
	 * @param sonar
	 * @param touch
	 * @param light
	 * @param compass
	 * @param wheelRadius
	 * @param trackWidth
	 */
	public SensorCalibrationBehavior(UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelRadius, float trackWidth) {
		super(null, sonar, touch, light, compass, wheelRadius, trackWidth);
		sensorsCalibrated = false;
	}

	@Override
	public boolean takeControl() {
		return !sensorsCalibrated;
	}

	@Override
	public void action() {
		/* Calibrate the compass by turning slowly by 720 degrees. */
		System.out.println("Calib. compass");
		DifferentialPilot p = new DifferentialPilot(wheelDiameter, trackWidth, Motor.A, Motor.C);
		p.setRotateSpeed(20);
		compass.startCalibration();
		p.rotate(720, false);
		compass.stopCalibration();

		/* Ask the user for input in calibrating the light sensor. */
		System.out.println("Calib. light s.");
		light.setFloodlight(true);
		System.out.println("Point at dark\nand press ENTER");
		Button.ENTER.waitForPress();
		light.calibrateLow();
		System.out.println("Point at light\nand press ENTER");
		Button.ENTER.waitForPress();
		light.calibrateHigh();

		sensorsCalibrated = true;
	}

	@Override
	public void suppress() { }
}
