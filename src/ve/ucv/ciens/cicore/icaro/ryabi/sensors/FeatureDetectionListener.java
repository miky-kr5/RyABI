package ve.ucv.ciens.cicore.icaro.ryabi.sensors;

import lejos.nxt.Sound;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;

public class FeatureDetectionListener implements FeatureListener {
	private SensorEventsQueue eventsQueue;

	public FeatureDetectionListener() {
		this.eventsQueue = SensorEventsQueue.getInstance();
	}

	@Override
	public void featureDetected(Feature feature, FeatureDetector detector) {
		detector.enableDetection(false);
		eventsQueue.addEvent(feature, detector);
		Sound.beep();
	}
}
