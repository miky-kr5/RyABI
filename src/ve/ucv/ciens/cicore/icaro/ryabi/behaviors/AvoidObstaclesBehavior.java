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
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEvent;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEventsQueue;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.Rotations;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.States;

/**
 * This class implements a {@link BaseBehavior} that attempts to evade obstacles detected by the {@link TouchSensor} by moving
 * a bit to the right.
 * 
 * @author Miguel Angel Astor Romero.
 */
public class AvoidObstaclesBehavior extends BaseBehavior {
	private RobotStateSingleton state;
	private SensorEventsQueue   queue;

	/**
	 * Creates a new {@link AvoidObstaclesBehavior}.
	 */
	public AvoidObstaclesBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelRadius, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelRadius, trackWidth);
		this.state = RobotStateSingleton.getInstance();
		this.queue = SensorEventsQueue.getInstance();
	}

	@Override
	public boolean takeControl() {
		return queue.hasNextTouchSensorEvent() && state.getState() == States.WANDER;
	}

	@Override
	public void action() {
		SensorEvent event = queue.getNextTouchSensorEvent();

		event.detector.enableDetection(false);
		pilot.stop();
		pilot.travel(-100);
		Rotations.rotate90(compass, pilot);
		pilot.travel(250);
		Rotations.rotateM90(compass, pilot);
		pilot.stop();

		while(queue.hasNextTouchSensorEvent())
			event = queue.getNextTouchSensorEvent();

		event.detector.enableDetection(true);
	}

	@Override
	public void suppress() { }
}
