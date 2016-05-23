package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.subsumption.Behavior;

public abstract class BaseBehavior implements Behavior {
	protected ArcRotateMoveController pilot;
	protected UltrasonicSensor        sonar;
	protected TouchSensor             touch;
	protected CompassHTSensor         compass;
	protected LightSensor             light;
	protected float                   wheelDiameter;
	protected float                   trackWidth;

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
