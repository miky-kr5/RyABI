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
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEventsQueue;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.Rotations;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.States;

/**
 * This class implements a {@link BaseBehavior} that searches slowly to the left and right of the robot for the pedestal where the ball
 * should be in. It first searches to the left of the robot until it hits an obstacle. Then it will search to the right of the robot
 * until it hits another obstacle.
 * 
 * If the ball is not found after searching on both sides then the robot will make a 180 degrees turn and will start moving back
 * to the start line.
 * 
 * @author Miguel Angel Astor Romero.
 */
public class SearchBallBehavior extends BaseBehavior {
	private SensorEventsQueue       queue;
	private boolean                 ballFound;
	private FeatureDetectorsHandler detectorHandler;
	private RobotStateSingleton     state;
	private boolean                 turnLeft;

	/**
	 * Creates a new {@link SearchBallBehavior}.
	 * @param pilot
	 * @param sonar
	 * @param touch
	 * @param light
	 * @param compass
	 * @param wheelDiameter
	 * @param trackWidth
	 */
	public SearchBallBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelDiameter, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelDiameter, trackWidth);
		this.queue = SensorEventsQueue.getInstance();
		this.ballFound = false;
		this.detectorHandler = FeatureDetectorsHandler.getInstance();
		this.state = RobotStateSingleton.getInstance();
		this.turnLeft = true;
	}

	@Override
	public boolean takeControl() {
		/* If the ball has already been found then this behavior should not take control again. */
		if(ballFound) {
			return false;
		}

		/* If the current state is SEARCH_BALL then set the detectors and take control. */
		if(state.getState() == States.SEARCH_BALL && !ballFound) {
			setDetectors();
			return true;
		}

		/* If the state is not SEARCH_BALL but there is at least one light feature detected
		 * indicating that the finish line has been reached then set the detectors and take control. */
		if(queue.hasNextLightSensorEvent() && !ballFound) {
			state.setState(States.SEARCH_BALL);
			setDetectors();

			/* Discard unneeded detected light features. */
			while(queue.hasNextLightSensorEvent())
				queue.getNextLightSensorEvent();

			return true;

		}

		return false;
	}

	@Override
	public void action() {
		boolean _ballFound = false;
		boolean obstacleFound = false;

		/* Discard unneeded touch events. */
		while(queue.hasNextTouchSensorEvent())
			queue.getNextTouchSensorEvent();

		if(turnLeft) {
			moveLeft();
		} else {
			obstacleFound = moveRight();
		}

		pilot.stop();

		if(!obstacleFound) {
			_ballFound = searchBall();
			if(_ballFound) {
				ballFound = true;
				state.setState(States.BALL_FOUND);
			}
		} else {
			ballFound = true;
			Rotations.rotate90(compass, pilot);
			Rotations.rotate90(compass, pilot);
			pilot.travel(250);
			state.setState(States.WANDER);
		}
	}

	@Override
	public void suppress() { }

	/**
	 * Enables the touch and range feature detectors and disables the light feature detector.
	 */
	private void setDetectors() {
		detectorHandler.enableTouchDetector();
		detectorHandler.disableLightDetector();
		detectorHandler.enableRangeDetector();
	}

	private void moveLeft() {
		/* Search to the left of the robot. */
		Rotations.rotateM90(compass, pilot);
		pilot.travel(100);

		turnLeft = !obstacle();

		Rotations.rotate90(compass, pilot);
	}

	private boolean moveRight() {
		boolean obstacleFound = false;

		/* Search to the right of the robot. */
		Rotations.rotate90(compass, pilot);
		pilot.travel(100);

		obstacleFound = obstacle();

		Rotations.rotateM90(compass, pilot);

		return obstacleFound;
	}

	private boolean searchBall() {
		boolean found = false;

		while(queue.hasNextRangeSensorEvent())
			queue.getNextRangeSensorEvent();

		try { Thread.sleep(2000); } catch(InterruptedException e) { };
		if(queue.hasNextRangeSensorEvent()) {
			found = true;

			/* Discard unneeded range features. */
			while(queue.hasNextRangeSensorEvent())
				queue.getNextRangeSensorEvent();
		}

		return found;
	}

	private boolean obstacle() {
		boolean obstacleFound = false;

		if(queue.hasNextTouchSensorEvent()) {
			pilot.travel(-200);

			/* Discard unneeded touch events. */
			while(queue.hasNextTouchSensorEvent())
				queue.getNextTouchSensorEvent();

			detectorHandler.enableTouchDetector();
			obstacleFound = true;
		}

		return obstacleFound;
	}
}
