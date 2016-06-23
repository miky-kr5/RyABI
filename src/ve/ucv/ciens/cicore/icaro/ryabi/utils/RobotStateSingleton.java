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

/**
 * Singleton class that holds the current state of the robot's goal. See {@link States}.
 * 
 * @author Miguel Angel Astor Romero.
 */
public final class RobotStateSingleton {
	private States state;

	private RobotStateSingleton() {
		state = States.WANDER;
	}

	private static class SingletonHolder {
		private static final RobotStateSingleton INSTANCE = new RobotStateSingleton();
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the instance.
	 */
	public static RobotStateSingleton getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * Changes the state of the robot.
	 * 
	 * @param state the state to set.
	 */
	public synchronized void setState(States state) {
		System.out.println(getStateName(state));
		this.state = state;
	}

	/**
	 * Returns the current state of the robot.
	 * 
	 * @return the current state.
	 */
	public States getState() {
		return this.state;
	}
	
	private String getStateName(States state) {
		switch (state) {
		case BALL_FOUND:
			return "BALL FOUND";
		case DONE:
			return "DONE";
		case SEARCH_BALL:
			return "SEARCH BALL";
		case VICTORY:
			return "VICTORY";
		case WANDER:
			return "WANDER";
		default:
			return "ERROR";
		}
	}
}
