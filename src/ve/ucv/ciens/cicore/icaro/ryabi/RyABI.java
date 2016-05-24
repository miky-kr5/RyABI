package ve.ucv.ciens.cicore.icaro.ryabi;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.CompassPilot;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.AvoidObstaclesBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.CatchBallBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.SearchBoxBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.SensorCalibrationBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.VictoryBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.behaviors.WanderBehavior;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.FeatureDetectorsHandler;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.FeatureDetectionListener;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.LightFeatureDetector;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.QuitButtonListener;

@SuppressWarnings("deprecation")
public class RyABI {
	private static final float WHEEL_DIAMETER = 56.0f;
	private static final float TRACK_WIDTH = 155.0f;

	private static ArcRotateMoveController  pilot;
	private static UltrasonicSensor         sonar;
	private static TouchSensor              touch;
	private static CompassHTSensor          compass;
	private static LightSensor              light;
	private static Behavior[]               behaviors;
	private static Arbitrator               arbitrator;
	private static RangeFeatureDetector     rangeDetector;
	private static TouchFeatureDetector     touchDetector;
	private static LightFeatureDetector     lightDetector;
	private static FeatureDetectionListener featureListener;
	private static FeatureDetectorsHandler  detectorHandler;

	public static void main(String[] args) {
		/* Create the sensors. */
		sonar = new UltrasonicSensor(SensorPort.S1);
		touch = new TouchSensor(SensorPort.S2);
		compass = new CompassHTSensor(SensorPort.S3);
		light = new LightSensor(SensorPort.S4);

		/* Create the pilot. */
		pilot = new CompassPilot(compass, WHEEL_DIAMETER, TRACK_WIDTH, Motor.A, Motor.C);

		/* Create the feature detectors. */
		rangeDetector = new RangeFeatureDetector(sonar, 20, 200);
		touchDetector = new TouchFeatureDetector(touch);
		lightDetector = new LightFeatureDetector(light);

		detectorHandler = FeatureDetectorsHandler.getInstance();
		detectorHandler.setRangeDetector(rangeDetector);
		detectorHandler.setTouchDetector(touchDetector);
		detectorHandler.setLightDetector(lightDetector);
		detectorHandler.disableAllDetectors();

		featureListener = new FeatureDetectionListener();
		rangeDetector.addListener(featureListener);
		touchDetector.addListener(featureListener);
		lightDetector.addListener(featureListener);

		/* Register escape button for forced exit. */
		Button.ESCAPE.addButtonListener(new QuitButtonListener());

		/* Create the behaviors. */
		behaviors = new Behavior[6];
		behaviors[0] = new WanderBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		behaviors[1] = new VictoryBehavior();
		behaviors[2] = new SearchBoxBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		behaviors[3] = new CatchBallBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		behaviors[4] = new AvoidObstaclesBehavior(pilot, sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);
		behaviors[5] = new SensorCalibrationBehavior(sonar, touch, light, compass, WHEEL_DIAMETER, TRACK_WIDTH);

		/* Start the program. */
		arbitrator = new Arbitrator(behaviors, true);
		arbitrator.start();
	}
}
