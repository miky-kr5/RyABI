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

public class CatchBallBehavior extends BaseBehavior {
	RobotStateSingleton state;

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
		Motor.B.forward();
		try { Thread.sleep(2000); } catch(InterruptedException e) { };

		Rotations.rotate180(compass, pilot);
		state.setState(States.WANDER);
	}

	@Override
	public void suppress() { }
}
