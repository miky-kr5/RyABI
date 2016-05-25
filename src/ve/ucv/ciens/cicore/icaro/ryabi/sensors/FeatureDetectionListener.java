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

import lejos.nxt.Sound;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;

/**
 * A {@link FeatureListener} implementation that just plays a beep when any feature is detected and stores said feature at the
 * global {@link SensorEventsQueue}.
 * 
 * @author Miguel Angel Astor Romero.
 */
public class FeatureDetectionListener implements FeatureListener {
	private SensorEventsQueue eventsQueue;

	/**
	 * Creates a new {@link FeatureDetectionListener}.
	 */
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
