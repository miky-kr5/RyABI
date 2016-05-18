package ve.ucv.ciens.cicore.icaro.ryabi;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.SensorCalibrationBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.QuitButtonListener;

public class RyABI {
	private static final float WHEEL_RADIUS = 56.0f;
	private static final float TRACK_WIDTH = 155.0f;

	private static UltrasonicSensor sonar;
	private static TouchSensor      touch;
	private static CompassHTSensor  compass;
	private static LightSensor      light;
	private static Behavior[]       behaviors;
	private static Arbitrator       arbitrator;

	public static void main(String[] args) {
		/* Create the sensors. */
		sonar = new UltrasonicSensor(SensorPort.S1);
		touch = new TouchSensor(SensorPort.S2);
		compass = new CompassHTSensor(SensorPort.S3);
		light = new LightSensor(SensorPort.S4);

		/* Register escape button for forced exit. */
		Button.ESCAPE.addButtonListener(new QuitButtonListener());

		/* Create the behaviors. */
		behaviors = new Behavior[1];
		behaviors[0] = new SensorCalibrationBehavior(sonar, touch, light, compass, WHEEL_RADIUS, TRACK_WIDTH);

		/* Start the program. */
		arbitrator = new Arbitrator(behaviors, true);
		arbitrator.start();
	}
}
