package ve.ucv.ciens.cicore.icaro.ryabi.sensors;

import lejos.geom.Point;
import lejos.nxt.LightSensor;
import lejos.robotics.RangeReading;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetectorAdapter;
import lejos.robotics.objectdetection.RangeFeature;

public class LightFeatureDetector extends FeatureDetectorAdapter {
	private static final int DELAY = 50;

	private LightSensor lightSensor;
	private int threshold;
	private boolean invert;
	private float angle = 0;
	private float range = 0;

	public LightFeatureDetector(LightSensor lightSensor) {
		this(lightSensor, 50, false, 0, 0);
	}

	public LightFeatureDetector(LightSensor lightSensor, int threshold) {
		this(lightSensor, threshold, false, 0, 0);
	}

	public LightFeatureDetector(LightSensor lightSensor, boolean invert) {
		this(lightSensor, 50, invert, 0, 0);
	}

	public LightFeatureDetector(LightSensor lightSensor, double xOffset, double yOffset) {
		this(lightSensor, 50, false, xOffset, yOffset);
	}

	public LightFeatureDetector(LightSensor lightSensor, int threshold, double xOffset, double yOffset) {
		this(lightSensor, threshold, false, xOffset, yOffset);
	}

	public LightFeatureDetector(LightSensor lightSensor, boolean invert, double xOffset, double yOffset) {
		this(lightSensor, 50, invert, xOffset, yOffset);
	}

	public LightFeatureDetector(LightSensor lightSensor, int threshold, boolean invert, double xOffset, double yOffset) {
		super(DELAY);
		this.lightSensor = lightSensor;
		this.threshold = threshold;
		this.invert = invert;

		// Calculate angle a distance of bumper from center:
		Point robot_center = new Point(0, 0);
		Point bumper_p = new Point((float)xOffset, (float)yOffset);
		range = (float)robot_center.distance(xOffset, yOffset);
		angle = robot_center.angleTo(bumper_p) - 90;
	}

	@Override
	public Feature scan() {
		RangeFeature rf = null;
		if(lightSensor.getLightValue() <= threshold) {
			RangeReading rr = new RangeReading(angle, range);
			rf = new RangeFeature(rr);
		}
		return rf;
	}

	@Override
	protected void notifyListeners(Feature feature) {
		super.notifyListeners(feature);

		if(invert) {
			while(lightSensor.getLightValue() >= threshold);
		} else {
			while(lightSensor.getLightValue() <= threshold);
		}
	}
}
