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
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.Rotations;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.States;

/**
 * This class implements a {@link BaseBehavior} that just closes the B {@link Motor} in order to catch the ball after it has been found.
 * 
 * @author Miguel Angel Astor Romero.
 */
public class CatchBallBehavior extends BaseBehavior {
	RobotStateSingleton state;

	/**
	 * Creates a new {@link CatchBallBehavior}.
	 * @param pilot
	 * @param sonar
	 * @param touch
	 * @param light
	 * @param compass
	 * @param wheelDiameter
	 * @param trackWidth
	 */
	public CatchBallBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelDiameter, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelDiameter, trackWidth);
		this.state = RobotStateSingleton.getInstance();
	}

	@Override
	public boolean takeControl() {
		return state.getState() == States.BALL_FOUND;
	}

	@Override
	public void action() {
		pilot.travel(65);
		Motor.B.backward();
		try { Thread.sleep(2000); } catch(InterruptedException e) { };

		/* Turn towards the start line and start moving. */
		Rotations.rotate180(compass, pilot);
		state.setState(States.WANDER);
	}

	@Override
	public void suppress() { }
}
