package ve.ucv.ciens.cicore.icaro.ryabi.sensors;

import java.util.Queue;

import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;

public final class SensorEventsQueue {
	private Queue<SensorEvent> touchEventsQueue;
	private Queue<SensorEvent> lightEventsQueue;
	private Queue<SensorEvent> sonarEventsQueue;

	private SensorEventsQueue() {
		touchEventsQueue = new Queue<SensorEvent>();
		lightEventsQueue = new Queue<SensorEvent>();
		sonarEventsQueue = new Queue<SensorEvent>();
	}

	private static class SingletonHolder {
		private static final SensorEventsQueue INSTANCE = new SensorEventsQueue();
	}

	public static SensorEventsQueue getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public synchronized void addEvent(Feature feature, FeatureDetector detector) {
		SensorEvent event = new SensorEvent(feature, detector);

		if(detector instanceof TouchFeatureDetector) {
			touchEventsQueue.addElement(event);
		} else if(detector instanceof LightFeatureDetector) {
			lightEventsQueue.addElement(event);
		} else if(detector instanceof RangeFeatureDetector) {
			sonarEventsQueue.addElement(event);
		}
	}

	public synchronized SensorEvent getNextTouchSensorEvent() {
		if(!touchEventsQueue.empty())
			return (SensorEvent) touchEventsQueue.pop();
		else
			return null;
	}

	public synchronized SensorEvent getNextLightSensorEvent() {
		if(!lightEventsQueue.empty())
			return (SensorEvent) lightEventsQueue.pop();
		else
			return null;
	}

	public synchronized SensorEvent getNextSonarSensorEvent() {
		if(!sonarEventsQueue.empty())
			return (SensorEvent) sonarEventsQueue.pop();
		else
			return null;
	}

	public synchronized boolean hasNextTouchSensorEvent() {
		return !touchEventsQueue.empty();
	}

	public synchronized boolean hasNextLightSensorEvent() {
		return !lightEventsQueue.empty();
	}

	public synchronized boolean hasNextSonarSensorEvent() {
		return !sonarEventsQueue.empty();
	}
}
