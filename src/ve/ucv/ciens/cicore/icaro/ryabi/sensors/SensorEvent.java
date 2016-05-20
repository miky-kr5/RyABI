package ve.ucv.ciens.cicore.icaro.ryabi.sensors;

import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;

public class SensorEvent {
	public Feature feature;
	public FeatureDetector detector;

	public SensorEvent(Feature feature, FeatureDetector detector) {
		this.feature = feature;
		this.detector = detector;
	}
}
