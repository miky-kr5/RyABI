package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.CompassPilot;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;

@SuppressWarnings("deprecation")
public class WanderBehavior extends BaseBehavior {
	private RobotStateSingleton           state;

	public WanderBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelRadius, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelRadius, trackWidth);

		state = RobotStateSingleton.getInstance();

		pilot = new CompassPilot(compass, 56f, 155f, Motor.A, Motor.C);
	}

	@Override
	public boolean takeControl() {
		return state.getState() == RobotStateSingleton.States.WANDER;
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
