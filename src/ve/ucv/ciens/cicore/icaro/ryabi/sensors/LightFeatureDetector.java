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

import lejos.geom.Point;
import lejos.nxt.LightSensor;
import lejos.robotics.RangeReading;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetectorAdapter;
import lejos.robotics.objectdetection.RangeFeature;
import lejos.robotics.objectdetection.TouchFeatureDetector;

/**
 * <p>A {@link FeatureDetectorAdapter} subclass that reports events detected by the light sensor every 50 milliseconds. It is based
 * on the {@link TouchFeatureDetector} class.</p>
 * 
 * <p>The detector can take into account the position of the light sensor with respect to the rotation center of the robot.
 * Use the following guide to calculate the x offset and y offset of the sensor:</p>
 * 
 * <p><pre>+-----------------+</pre></p>
 * <p><pre>|        y----L   |</pre></p>
 * <p><pre>|        |    |   |</pre></p>
 * <p><pre>|W       C----x  W|</pre></p>
 * <p><pre>|                 |</pre></p>
 * <p><pre>|                 |</pre></p>
 * <p><pre>|                 |</pre></p>
 * <p><pre>|                 |</pre></p>
 * <p><pre>|                 |</pre></p>
 * <p><pre>+-----------------+</pre></p>
 * 
 * <p>Where:</p>
 * 
 * <ul>
 *   <li> C is the rotation center of the robot. </li>
 *   <li> W are the centers of the differential wheels of the robot. </li>
 *   <li> L is the light sensor. </li>
 *   <li> x and y are the distances from the rotation center to the light sensor taken along the respective edges of the robot. </li>
 * </ul>
 * 
 * <p>The front of the robot is at the top of the diagram.</p>
 * 
 * @author Miguel Angel Astor Romero.
 */
public class LightFeatureDetector extends FeatureDetectorAdapter {
	private static final int DELAY = 50;

	private LightSensor lightSensor;
	private int threshold;
	private boolean invert;
	private float angle = 0;
	private float range = 0;

	/**
	 * Creates a new {@link LightFeatureDetector} using the given light sensor. The detection threshold is
	 * set to 50% and it assumes the sensor is at the rotation center of the robot. The detector will report events
	 * when the light measurement is less than 50%.
	 * 
	 * @param lightSensor the light sensor to use.
	 */
	public LightFeatureDetector(LightSensor lightSensor) {
		this(lightSensor, 50, false, 0, 0);
	}

	/**
	 * Creates a new {@link LightFeatureDetector} using the given light sensor and the given threshold. It assumes the sensor is at
	 * the rotation center of the robot. The detector will report events when the light measurement is less than the threshold.
	 * 
	 * @param lightSensor the light sensor to use.
	 * @param threshold the detection threshold. It will be clamped to [0, 100].
	 */
	public LightFeatureDetector(LightSensor lightSensor, int threshold) {
		this(lightSensor, threshold, false, 0, 0);
	}

	/**
	 * Creates a new {@link LightFeatureDetector} using the given light sensor. The detection threshold is
	 * set to 50% and it assumes the sensor is at the rotation center of the robot. The detector will report events
	 * when the light measurement is more than or equal to 50% if invert is true.
	 * 
	 * @param lightSensor the light sensor to use.
	 * @param invert
	 */
	public LightFeatureDetector(LightSensor lightSensor, boolean invert) {
		this(lightSensor, 50, invert, 0, 0);
	}

	/**
	 * Creates a new {@link LightFeatureDetector} using the given light sensor. The detection threshold is
	 * set to 50%. The offsets determine the position of the sensor with respect to the center of the robot.
	 * The detector will report events when the light measurement is less than 50%.
	 * 
	 * To calculate the offsets use the following guide: 
	 * 
	 * @param lightSensor the light sensor to use.
	 * @param xOffset distance of the sensor to the rotation center of the robot.
	 * @param yOffset distance of the sensor to the rotation center of the robot.
	 */
	public LightFeatureDetector(LightSensor lightSensor, double xOffset, double yOffset) {
		this(lightSensor, 50, false, xOffset, yOffset);
	}

	/**
	 * Creates a new {@link LightFeatureDetector} using the given light sensor. The detection threshold is
	 * set to the given threshold. The offsets determine the position of the sensor with respect to the center of the robot.
	 * The detector will report events when the light measurement is less than the given threshold.
	 * 
	 * To calculate the offsets use the following guide: 
	 * 
	 * @param lightSensor the light sensor to use.
	 * @param threshold the detection threshold. It will be clamped to [0, 100].
	 * @param xOffset distance of the sensor to the rotation center of the robot.
	 * @param yOffset distance of the sensor to the rotation center of the robot.
	 */
	public LightFeatureDetector(LightSensor lightSensor, int threshold, double xOffset, double yOffset) {
		this(lightSensor, threshold, false, xOffset, yOffset);
	}

	/**
	 * Creates a new {@link LightFeatureDetector} using the given light sensor. The detection threshold is
	 * set to 50%. The offsets determine the position of the sensor with respect to the center of the robot.
	 * The detector will report events when the light measurement is more than or equal to 50% if invert is true.
	 * 
	 * To calculate the offsets use the following guide: 
	 * 
	 * @param lightSensor the light sensor to use.
	 * @param invert
	 * @param xOffset distance of the sensor to the rotation center of the robot.
	 * @param yOffset distance of the sensor to the rotation center of the robot.
	 */
	public LightFeatureDetector(LightSensor lightSensor, boolean invert, double xOffset, double yOffset) {
		this(lightSensor, 50, invert, xOffset, yOffset);
	}

	/**
	 * Creates a new {@link LightFeatureDetector} using the given light sensor. The detection threshold is
	 * set to the given threshold. The offsets determine the position of the sensor with respect to the center of the robot.
	 * The detector will report events when the light measurement is more than the given threshold if invert is true.
	 * 
	 * To calculate the offsets use the following guide: 
	 * 
	 * @param lightSensor the light sensor to use.
	 * @param threshold the detection threshold. It will be clamped to [0, 100].
	 * @param invert
	 * @param xOffset distance of the sensor to the rotation center of the robot.
	 * @param yOffset distance of the sensor to the rotation center of the robot.
	 */
	public LightFeatureDetector(LightSensor lightSensor, int threshold, boolean invert, double xOffset, double yOffset) {
		super(DELAY);
		this.lightSensor = lightSensor;
		this.threshold = threshold;
		this.invert = invert;

		/* Clamp the detection threshold. */
		this.threshold = (this.threshold > 100) ? 100 : this.threshold;
		this.threshold = (this.threshold < 0)   ? 0   : this.threshold;
		
		/* Calculate angle and distance of light sensor from center. */
		Point robot_center = new Point(0, 0);
		Point bumper_p = new Point((float)xOffset, (float)yOffset);
		range = (float)robot_center.distance(xOffset, yOffset);
		angle = robot_center.angleTo(bumper_p) - 90;
	}

	@Override
	public Feature scan() {
		RangeFeature rf = null;

		/* Calculate the distance and angle of the feature and return it. Take into
		 * account if the detection factor is inverted. */
		if(invert) {
			if(lightSensor.getLightValue() >= threshold) {
				RangeReading rr = new RangeReading(angle, range);
				rf = new RangeFeature(rr);
			}
		} else {
			if(lightSensor.getLightValue() < threshold) {
				RangeReading rr = new RangeReading(angle, range);
				rf = new RangeFeature(rr);
			}
		}
		return rf;
	}

	@Override
	protected void notifyListeners(Feature feature) {
		super.notifyListeners(feature);

		/* Stall the detector until the detection ends to avoid notifying the listeners
		 * multiple times for the same feature. */
		if(invert) {
			while(lightSensor.getLightValue() >= threshold);
		} else {
			while(lightSensor.getLightValue() < threshold);
		}
	}
}
