package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEvent;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEventsQueue;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.Rotations;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.States;

public class AvoidObstaclesBehavior extends BaseBehavior {
	private RobotStateSingleton state;
	private SensorEventsQueue   queue;

	public AvoidObstaclesBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelRadius, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelRadius, trackWidth);
		this.state = RobotStateSingleton.getInstance();
		this.queue = SensorEventsQueue.getInstance();
	}

	@Override
	public boolean takeControl() {
		return state.getState() == States.WANDER && queue.hasNextTouchSensorEvent();
	}

	@Override
	public void action() {
		SensorEvent event = queue.getNextTouchSensorEvent();

		pilot.stop();
		pilot.travel(-100);
		Rotations.rotate90(compass, pilot);
		pilot.travel(250);
		Rotations.rotateM90(compass, pilot);
		pilot.stop();

		if(!queue.hasNextTouchSensorEvent())
			event.detector.enableDetection(true);
	}

	@Override
	public void suppress() { }
}
