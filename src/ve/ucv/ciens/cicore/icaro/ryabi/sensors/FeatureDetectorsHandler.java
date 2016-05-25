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

import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;
import lejos.robotics.subsumption.Behavior;

/**
 * Singleton class that holds all the feature detectors in one place so that the different {@link Behavior} implementations
 * can operate on them.
 * 
 * @author Miguel Angel Astor Romero.
 */
public final class FeatureDetectorsHandler {
	private RangeFeatureDetector     rangeDetector;
	private TouchFeatureDetector     touchDetector;
	private LightFeatureDetector     lightDetector;

	private FeatureDetectorsHandler() {
		rangeDetector = null;
		touchDetector = null;
		lightDetector = null;
	}

	private static class SingletonHolder {
		private static final FeatureDetectorsHandler INSTANCE = new FeatureDetectorsHandler();
	}

	/**
	 * Gets the singleton instance of this class.
	 * 
	 * @return the instacne.
	 */
	public static FeatureDetectorsHandler getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public synchronized void setRangeDetector(RangeFeatureDetector rangeDetector) {
		this.rangeDetector = rangeDetector;
	}

	public synchronized void setTouchDetector(TouchFeatureDetector touchDetector) {
		this.touchDetector = touchDetector;
	}

	public synchronized void setLightDetector(LightFeatureDetector lightDetector) {
		this.lightDetector = lightDetector;
	}

	public synchronized void enableRangeDetector() {
		if(rangeDetector != null)
			rangeDetector.enableDetection(true);
	}

	public synchronized void disableRangeDetector() {
		if(rangeDetector != null)
			rangeDetector.enableDetection(false);
	}

	public synchronized void enableTouchDetector() {
		if(touchDetector != null)
			touchDetector.enableDetection(true);
	}

	public synchronized void disableTouchDetector() {
		if(touchDetector != null)
			touchDetector.enableDetection(false);
	}

	public synchronized void enableLightDetector() {
		if(lightDetector != null)
			lightDetector.enableDetection(true);
	}

	public synchronized void disableLightDetector() {
		if(lightDetector != null)
			lightDetector.enableDetection(false);
	}

	public synchronized void enableAllDetectors() {
		if(rangeDetector != null)
			rangeDetector.enableDetection(true);
		if(touchDetector != null)
			touchDetector.enableDetection(true);
		if(lightDetector != null)
			lightDetector.enableDetection(true);
	}

	public synchronized void disableAllDetectors() {
		if(rangeDetector != null)
			rangeDetector.enableDetection(false);
		if(touchDetector != null)
			touchDetector.enableDetection(false);
		if(lightDetector != null)
			lightDetector.enableDetection(false);
	}
}
