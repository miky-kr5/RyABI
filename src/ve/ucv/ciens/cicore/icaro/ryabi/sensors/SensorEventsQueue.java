/*
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * See the LICENSE file for more details.
 */

package ve.ucv.ciens.cicore.icaro.ryabi.sensors;

import java.util.Queue;

import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;

/**
 * A singleton {@link Queue} holder that keeps track of the different events detected by the sensors in FIFO order.
 * 
 * @author Miguel Angel Astor Romero.
 */
public final class SensorEventsQueue {
	private Queue<SensorEvent> touchEventsQueue;
	private Queue<SensorEvent> lightEventsQueue;
	private Queue<SensorEvent> rangeEventsQueue;

	private SensorEventsQueue() {
		touchEventsQueue = new Queue<SensorEvent>();
		lightEventsQueue = new Queue<SensorEvent>();
		rangeEventsQueue = new Queue<SensorEvent>();
	}

	private static class SingletonHolder {
		private static final SensorEventsQueue INSTANCE = new SensorEventsQueue();
	}

	/**
	 * Gets the singleton instance of this class.
	 * 
	 * @return the instance.
	 */
	public static SensorEventsQueue getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * Adds a new event to the respective queue based on it's type.
	 * 
	 * @param feature the detected feature.
	 * @param detector the respective detector.
	 */
	public synchronized void addEvent(Feature feature, FeatureDetector detector) {
		SensorEvent event = new SensorEvent(feature, detector);

		if(detector instanceof TouchFeatureDetector) {
			touchEventsQueue.addElement(event);
		} else if(detector instanceof LightFeatureDetector) {
			lightEventsQueue.addElement(event);
		} else if(detector instanceof RangeFeatureDetector) {
			rangeEventsQueue.addElement(event);
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

	public synchronized SensorEvent getNextRangeSensorEvent() {
		if(!rangeEventsQueue.empty())
			return (SensorEvent) rangeEventsQueue.pop();
		else
			return null;
	}

	public synchronized boolean hasNextTouchSensorEvent() {
		return !touchEventsQueue.empty();
	}

	public synchronized boolean hasNextLightSensorEvent() {
		return !lightEventsQueue.empty();
	}

	public synchronized boolean hasNextRangeSensorEvent() {
		return !rangeEventsQueue.empty();
	}
}
