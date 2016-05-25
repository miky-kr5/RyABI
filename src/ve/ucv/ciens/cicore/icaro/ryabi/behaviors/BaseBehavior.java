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
import lejos.robotics.subsumption.Behavior;

/**
 * Base class for {@link Behavior} implementations that need to use multiple sensors and a pilot.
 * 
 * @author Miguel Angel Astor Romero.
 */
public abstract class BaseBehavior implements Behavior {
	protected ArcRotateMoveController pilot;
	protected UltrasonicSensor        sonar;
	protected TouchSensor             touch;
	protected CompassHTSensor         compass;
	protected LightSensor             light;
	protected float                   wheelDiameter;
	protected float                   trackWidth;

	/**
	 * Creates a new {@link BaseBehavior}. Has to be called by subclasses.
	 * 
	 * @param pilot A differential pilot that implements {@link ArcRotateMoveController}
	 * @param sonar A range finder sensor.
	 * @param touch A touch sensor.
	 * @param light A monochromatic light sensor.
	 * @param compass A HiTechnic compass sensor.
	 * @param wheelDiameter The diameter of the active wheels. Assumes both wheels are the same size.
	 * @param trackWidth The distance between the center of both active wheels.
	 */
	public BaseBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelDiameter, float trackWidth) {
		this.pilot = pilot;
		this.sonar = sonar;
		this.touch = touch;
		this.compass = compass;
		this.light = light;
		this.wheelDiameter = wheelDiameter;
		this.trackWidth = trackWidth;
	}

	@Override
	public abstract boolean takeControl();

	@Override
	public abstract void action();

	@Override
	public abstract void suppress();
}
