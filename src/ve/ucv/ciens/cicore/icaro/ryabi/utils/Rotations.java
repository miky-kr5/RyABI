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
package ve.ucv.ciens.cicore.icaro.ryabi.utils;

import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;

/**
 * Helper class that allows the robot to make 90 degrees turns to the left and right and 180 degrees turns.
 * 
 * @author Miguel Angel Astor Romero.
 */
public abstract class Rotations {
	/**
	 * Rotates the robot 90 degrees clockwise.
	 * 
	 * @param compass A {@link CompassHTSensor} to use to make good turns.
	 * @param pilot The {@link ArcRotateMoveController} implementation that controls the robot.
	 */
	public static void rotate90(CompassHTSensor compass, ArcRotateMoveController pilot) {
		float cMeasure = 0.0f, diff;

		pilot.stop();
		compass.resetCartesianZero();
		pilot.setRotateSpeed(25);
		diff = 90.0f - cMeasure;
		do {
			pilot.rotate(diff);
			cMeasure = 360.0f - compass.getDegreesCartesian();
			diff = 90.0f - cMeasure;
		} while(Math.abs(diff) > 1.0f);

		pilot.stop();
	}

	/**
	 * Rotates the robot 90 degrees counterclockwise.
	 * 
	 * @param compass A {@link CompassHTSensor} to use to make good turns.
	 * @param pilot The {@link ArcRotateMoveController} implementation that controls the robot.
	 */
	public static void rotateM90(CompassHTSensor compass, ArcRotateMoveController pilot) {
		float cMeasure = 0.0f, diff;

		pilot.stop();
		compass.resetCartesianZero();
		pilot.setRotateSpeed(25);
		diff = -90;
		do {
			pilot.rotate(diff);
			cMeasure = compass.getDegreesCartesian();
			diff = -90 + cMeasure;
		} while(Math.abs(diff) > 1.0f);

		pilot.stop();
	}
}
