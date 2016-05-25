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

import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;

/**
 * Container class that aggregates a detected {@link Feature} and the {@link FeatureDetector} that found it.
 * 
 * @author Miguel Angel Astor Romero.
 */
public class SensorEvent {
	/**
	 * A detected feature.
	 */
	public Feature feature;
	
	/**
	 * The detector responsible for finding the feature.
	 */
	public FeatureDetector detector;

	/**
	 * Commodity constructor.
	 * 
	 * @param feature the detected feature.
	 * @param detector the respective detector.
	 */
	public SensorEvent(Feature feature, FeatureDetector detector) {
		this.feature = feature;
		this.detector = detector;
	}
}
