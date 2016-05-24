package ve.ucv.ciens.cicore.icaro.ryabi.utils;

import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;

public abstract class Rotations {
	public static void rotate90(CompassHTSensor compass, ArcRotateMoveController pilot) {
		float cMeasure;

		compass.resetCartesianZero();

		pilot.setRotateSpeed(25);
		pilot.rotate(Integer.MIN_VALUE - 1, true);

		cMeasure = 360.0f;
		try { Thread.sleep(200); } catch (InterruptedException e) { }
		while(cMeasure > 270.0f) {
			cMeasure = compass.getDegreesCartesian();
		}
		pilot.stop();
	}

	public static void rotateM90(CompassHTSensor compass, ArcRotateMoveController pilot) {
		float cMeasure;

		compass.resetCartesianZero();

		pilot.setRotateSpeed(25);
		pilot.rotate(-3000, true);

		cMeasure = 0.0f;
		try { Thread.sleep(200); } catch (InterruptedException e) { }
		while(cMeasure < 90.0f) {
			cMeasure = compass.getDegreesCartesian();
		}
		pilot.stop();
	}

	public static void rotate180(CompassHTSensor compass, ArcRotateMoveController pilot) {
		float cMeasure;

		compass.resetCartesianZero();

		pilot.setRotateSpeed(25);
		pilot.rotate(Integer.MIN_VALUE - 1, true);

		cMeasure = 360.0f;
		try { Thread.sleep(200); } catch (InterruptedException e) { }
		while(cMeasure > 180.0f) {
			cMeasure = compass.getDegreesCartesian();
		}
		pilot.stop();
	}
}
