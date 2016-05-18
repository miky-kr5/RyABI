package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.subsumption.Behavior;

public abstract class BaseBehavior implements Behavior {
	protected UltrasonicSensor         sonar;
	protected TouchSensor              touch;
	protected CompassHTSensor          compass;
	protected LightSensor              light;

	public BaseBehavior(UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass) {
		this.sonar = sonar;
		this.touch = touch;
		this.compass = compass;
		this.light = light;
	}

	@Override
	public abstract boolean takeControl();

	@Override
	public abstract void action();

	@Override
	public abstract void suppress();
}
