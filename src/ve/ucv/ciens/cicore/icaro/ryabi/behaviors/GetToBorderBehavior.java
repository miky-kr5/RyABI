package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.navigation.CompassPilot;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;
import ve.ucv.ciens.cicore.icaro.ryabi.detectors.FeatureDetectionListener;
import ve.ucv.ciens.cicore.icaro.ryabi.detectors.LightFeatureDetector;

@SuppressWarnings("deprecation")
public class GetToBorderBehavior extends BaseBehavior {
	private ArcRotateMoveController  pilot;
	private RangeFeatureDetector     rangeDetector;
	private TouchFeatureDetector     touchDetector;
	private LightFeatureDetector     lightDetector;
	private FeatureDetectionListener featureListener;

	public GetToBorderBehavior(UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass) {
		super(sonar, touch, light, compass);

		pilot = new CompassPilot(compass, 56f, 155f, Motor.A, Motor.C);

		rangeDetector = new RangeFeatureDetector(sonar, 20, 200);
		touchDetector = new TouchFeatureDetector(touch);
		lightDetector = new LightFeatureDetector(light);

		rangeDetector.enableDetection(true);
		touchDetector.enableDetection(true);
		lightDetector.enableDetection(true);

		featureListener = new FeatureDetectionListener(pilot, compass);
		rangeDetector.addListener(featureListener);
		touchDetector.addListener(featureListener);
		lightDetector.addListener(featureListener);
	}

	@Override
	public boolean takeControl() {
		return false;
	}

	@Override
	public void action() {
		rangeDetector.enableDetection(true);
		touchDetector.enableDetection(true);
		lightDetector.enableDetection(true);
	}

	@Override
	public void suppress() {
		rangeDetector.enableDetection(false);
		touchDetector.enableDetection(false);
		lightDetector.enableDetection(false);
	}
}
