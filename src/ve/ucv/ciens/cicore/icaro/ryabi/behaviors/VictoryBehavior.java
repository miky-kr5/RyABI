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

import java.awt.Point;

import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.FeatureDetectorsHandler;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEventsQueue;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.States;

/**
 * This class implements a {@link Behavior} that plays some music when the robot has completed it's objective.
 * 
 * @author Miguel Angel Astor Romero.
 */
@SuppressWarnings("unused")
public class VictoryBehavior implements Behavior {
	private static final int C       = 262;
	private static final int D       = 287;
	private static final int E       = 320;
	private static final int F       = 349;
	private static final int G       = 392;
	private static final int A       = 440;
	private static final int B       = 494;
	private static final int ROUND   = 1000;
	private static final int WHITE   = 500;
	private static final int BLACK   = 250;
	private static final int QUARTER = 125;

	private Point[]                 score;
	private RobotStateSingleton     state;
	private SensorEventsQueue       queue;
	private FeatureDetectorsHandler detectorHandler;

	/**
	 * Creates a new {@link VictoryBehavior}.
	 */
	public VictoryBehavior() {
		state = RobotStateSingleton.getInstance();
		this.queue = SensorEventsQueue.getInstance();
		this.detectorHandler = FeatureDetectorsHandler.getInstance();

		this.score = new Point[17];
		score[0]  = new Point(B, BLACK);
		score[1]  = new Point(A, BLACK);
		score[2]  = new Point(G, WHITE);
		score[3]  = new Point(B, BLACK);
		score[4]  = new Point(A, BLACK);
		score[5]  = new Point(G, WHITE);
		score[6]  = new Point(G, QUARTER);
		score[7]  = new Point(G, QUARTER);
		score[8]  = new Point(G, QUARTER);
		score[9]  = new Point(G, QUARTER);
		score[10] = new Point(A, QUARTER);
		score[11] = new Point(A, QUARTER);
		score[12] = new Point(A, QUARTER);
		score[13] = new Point(A, QUARTER);
		score[14] = new Point(B, BLACK);
		score[15] = new Point(A, BLACK);
		score[16] = new Point(G, WHITE);
	}

	@Override
	public boolean takeControl() {
		/* Check if the current state is VICTORY. */
		if(state.getState() == States.VICTORY) {
			detectorHandler.disableAllDetectors();
			return true;
		}

		/* If the state is not VICTORY, then check if there is a light sensor
		 * event indicating that the goal line has been found. */
		if(queue.hasNextLightSensorEvent()) {
			/* Set the state to VICTORY and disable sensors. */
			state.setState(States.VICTORY);
			detectorHandler.disableAllDetectors();

			/* Discard extraneous light events. */
			while(queue.hasNextLightSensorEvent())
				queue.getNextLightSensorEvent();

			return true;
		}

		return false;
	}

	@Override
	public void action() {
		/* Play some music! */
		playMusic(score);

		/* Set the final state. */
		state.setState(States.DONE);
	}

	@Override
	public void suppress() { }

	private void playMusic(Point[] score) {
		for(Point note: score)
			playToneDuration(note.x, note.y);
	}

	/**
	 * Plays the given tone for the specified time given in milliseconds.
	 * 
	 * @param tone
	 * @param milis
	 */
	private void playToneDuration(int tone, int milis) {
		Sound.playTone(tone, milis);
		try { Thread.sleep(milis); } catch(InterruptedException ie) {}
	}
}
