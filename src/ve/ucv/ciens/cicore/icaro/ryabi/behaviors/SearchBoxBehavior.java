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

public class SearchBoxBehavior extends BaseBehavior {
	private SensorEventsQueue       queue;
	private boolean                 ballFound;
	private FeatureDetectorsHandler detectorHandler;
	private RobotStateSingleton     state;
	private boolean                 turnLeft;

	public SearchBoxBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelDiameter, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelDiameter, trackWidth);
		this.queue = SensorEventsQueue.getInstance();
		this.ballFound = false;
		this.detectorHandler = FeatureDetectorsHandler.getInstance();
		this.state = RobotStateSingleton.getInstance();
		this.turnLeft = true;
	}

	@Override
	public boolean takeControl() {
		if(ballFound)
			return false;

		if(state.getState() == States.SEARCH_BALL) {
			setDetectors();
			return true;
		}

		if(queue.hasNextLightSensorEvent()) {
			state.setState(States.SEARCH_BALL);
			setDetectors();

			while(queue.hasNextLightSensorEvent())
				queue.getNextLightSensorEvent();

			return true;

		}

		return false;
	}

	@Override
	public void action() {
		if(queue.hasNextRangeSensorEvent()) {

			if(pilot.isMoving())
				pilot.stop();
			ballFound = true;
			state.setState(States.BALL_FOUND);

			while(queue.hasNextRangeSensorEvent())
				queue.getNextRangeSensorEvent();

		} else {
			if(turnLeft) {
				Rotations.rotateM90(compass, pilot);
				pilot.travel(50);

				if(queue.hasNextTouchSensorEvent()) {
					pilot.travel(-100);

					turnLeft = false;

					while(queue.hasNextTouchSensorEvent())
						queue.getNextTouchSensorEvent();

					detectorHandler.enableTouchDetector();
				}

				Rotations.rotate90(compass, pilot);

			} else {
				Rotations.rotate90(compass, pilot);
				pilot.travel(50);

				if(queue.hasNextTouchSensorEvent()) {
					pilot.travel(-100);

					state.setState(States.WANDER);
					ballFound = true;

					while(queue.hasNextTouchSensorEvent())
						queue.getNextTouchSensorEvent();

					detectorHandler.enableTouchDetector();
				}

				Rotations.rotateM90(compass, pilot);

				if(ballFound) {
					Rotations.rotate180(compass, pilot);
				}
			}
		}
	}

	@Override
	public void suppress() {
		if(pilot.isMoving())
			pilot.stop();
	}

	private void setDetectors() {
		detectorHandler.enableTouchDetector();
		detectorHandler.disableLightDetector();
		detectorHandler.enableRangeDetector();
	}
}
