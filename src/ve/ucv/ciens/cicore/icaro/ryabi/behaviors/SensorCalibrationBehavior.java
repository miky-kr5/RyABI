package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class SensorCalibrationBehavior extends BaseBehavior {
	private boolean sensorsCalibrated;

	public SensorCalibrationBehavior(UltrasonicSensor sonar, TouchSensor touch, LightSensor light, CompassHTSensor compass, float wheelRadius, float trackWidth) {
		super(null, sonar, touch, light, compass, wheelRadius, trackWidth);
		sensorsCalibrated = false;
	}

	@Override
	public boolean takeControl() {
		return !sensorsCalibrated;
	}

	@Override
	public void action() {
		System.out.println("Calib. compass");
		DifferentialPilot p = new DifferentialPilot(wheelRadius, trackWidth, Motor.A, Motor.C);
		p.setRotateSpeed(25);
		compass.startCalibration();
		p.rotate(720, false);
		compass.stopCalibration();

		System.out.println("Calib. light s.");
		light.setFloodlight(true);
		System.out.println("Point at dark\nand press ENTER");
		Button.ENTER.waitForPress();
		light.calibrateLow();
		System.out.println("Point at light\nand press ENTER");
		Button.ENTER.waitForPress();
		light.calibrateHigh();

		sensorsCalibrated = true;
	}

	@Override
	public void suppress() { }
}
