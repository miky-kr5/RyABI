package ve.ucv.ciens.cicore.icaro.ryabi.detectors;

import lejos.nxt.Sound;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;

public class FeatureDetectionListener implements FeatureListener {
	private ArcRotateMoveController pilot;
	private CompassHTSensor         compass;

	public FeatureDetectionListener(ArcRotateMoveController pilot, CompassHTSensor compass) {
		this.pilot = pilot;
		this.compass = compass;
	}

	@Override
	public void featureDetected(Feature feature, FeatureDetector detector) {
		if(detector instanceof TouchFeatureDetector) {
			

		} else if(detector instanceof LightFeatureDetector) {
			// TODO: Add light sensor handling here.

		}else if(detector instanceof RangeFeatureDetector) {
			// TODO: Add sonar handling here.
			Sound.beep();

			detector.enableDetection(false);
			pilot.stop();
			pilot.travel(-100);
			rotate90();
			pilot.forward();
			detector.enableDetection(true);
		}
	}

	private void rotate90() {
		float cMeasure;

		compass.resetCartesianZero();

		pilot.setRotateSpeed(25);
		pilot.rotate(3000, true);

		cMeasure = 360.0f;
		try { Thread.sleep(200); } catch (InterruptedException e) { }
		while(cMeasure > 285.0f) {
			cMeasure = compass.getDegreesCartesian();
		}
		pilot.stop();
	}
}
