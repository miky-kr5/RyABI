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

import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.FeatureDetectorsHandler;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.States;

/**
 * This class implements a {@link BaseBehavior} that wanders in a straight line.
 * 
 * @author Miguel Angel Astor Romero.
 */
public class WanderBehavior extends BaseBehavior {
	private RobotStateSingleton     state;
	private FeatureDetectorsHandler detectorHandler;

	/**
	 * Create a new {@link WanderBehavior}.
	 * 
	 * @param pilot
	 * @param sonar
	 * @param touch
	 * @param light
	 * @param compass
	 * @param wheelDiameter
	 * @param trackWidth
	 */
	public WanderBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelDiameter, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelDiameter, trackWidth);
		state = RobotStateSingleton.getInstance();
		detectorHandler = FeatureDetectorsHandler.getInstance();
	}

	@Override
	public boolean takeControl() {
		/* Check if the state is wander. If it is, then set the sensors and return true. */
		if(state.getState() == States.WANDER) {
			detectorHandler.enableTouchDetector();
			detectorHandler.enableLightDetector();
			detectorHandler.disableRangeDetector();
			return true;

		} else
			return false;
	}

	@Override
	public void action() {
		if(!pilot.isMoving())
			pilot.forward();
	}

	@Override
	public void suppress() {
		if(pilot.isMoving())
			pilot.stop();
	}
}
