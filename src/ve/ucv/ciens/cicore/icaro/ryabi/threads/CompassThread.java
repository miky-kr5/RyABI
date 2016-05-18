package ve.ucv.ciens.cicore.icaro.ryabi.threads;

import lejos.nxt.LCD;
import lejos.nxt.addon.CompassHTSensor;

public class CompassThread extends Thread {
	CompassHTSensor compass;
	boolean done;

	public CompassThread(CompassHTSensor compass) {
		this.compass = compass;
		done = false;
	}

	public synchronized void end() {
		done = true;
	}

	public void run() {
		while(!done) {
			LCD.clear();
			System.out.println("C (DEG): " + compass.getDegrees());
			System.out.println("C (CRT): " + compass.getDegreesCartesian());
			try {
				Thread.sleep(1000);
			} catch(InterruptedException i) { }
		}
	}
}
