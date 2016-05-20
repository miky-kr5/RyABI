package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.LightSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEvent;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEventsQueue;

public class AvoidObstaclesBehavior extends BaseBehavior {
	private SensorEventsQueue       queue;

	public AvoidObstaclesBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelRadius, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelRadius, trackWidth);
		this.queue = SensorEventsQueue.getInstance();
	}

	@Override
	public boolean takeControl() {
		return queue.hasNextTouchSensorEvent();
	}

	@Override
	public void action() {
		SensorEvent event = queue.getNextTouchSensorEvent();

		pilot.stop();
		pilot.travel(-100);
		rotate90();
		pilot.travel(250);
		rotateM90();
		pilot.stop();

		if(!queue.hasNextTouchSensorEvent())
			event.detector.enableDetection(true);
	}

	@Override
	public void suppress() { }

	private void rotate90() {
		float cMeasure;

		compass.resetCartesianZero();

		pilot.setRotateSpeed(25);
		pilot.rotate(Integer.MIN_VALUE - 1, true);

		cMeasure = 360.0f;
		try { Thread.sleep(200); } catch (InterruptedException e) { }
		while(cMeasure > 270.0f) {
			cMeasure = compass.getDegreesCartesian();
		}
		pilot.stop();
	}

	private void rotateM90() {
		float cMeasure;

		compass.resetCartesianZero();

		pilot.setRotateSpeed(25);
		pilot.rotate(-3000, true);

		cMeasure = 0.0f;
		try { Thread.sleep(200); } catch (InterruptedException e) { }
		while(cMeasure < 90.0f) {
			cMeasure = compass.getDegreesCartesian();
		}
		pilot.stop();
	}
}
