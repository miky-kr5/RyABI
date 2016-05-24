package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.FeatureDetectorsHandler;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.States;

public class WanderBehavior extends BaseBehavior {
	private RobotStateSingleton     state;
	private FeatureDetectorsHandler detectorHandler;

	public WanderBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelDiameter, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelDiameter, trackWidth);
		state = RobotStateSingleton.getInstance();
		detectorHandler = FeatureDetectorsHandler.getInstance();
	}

	@Override
	public boolean takeControl() {
		if(state.getState() == States.WANDER) {
			detectorHandler.enableTouchDetector();
			detectorHandler.enableLightDetector();
			detectorHandler.disableRangeDetector();
			return true;

		} else
			return false;
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
