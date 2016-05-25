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

package ve.ucv.ciens.cicore.icaro.ryabi.utils;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

/**
 * This class implements a {@link ButtonListener} that force-quits the program if the escape button of the robot
 * has been pressed for 3 seconds or more.
 * 
 * @author Miguel Angel Astor Romero.
 */
public class QuitButtonListener implements ButtonListener {
	private TimeCounter counter;

	@Override
	public void buttonPressed(Button b) {
		/* Start counting the time as soon as the button is pressed. */
		counter = new TimeCounter();
		counter.start();
	}

	@Override
	public void buttonReleased(Button b) {
		/* Stop counting as soon as the button is released. */
		if(counter != null) {
			counter.finish();
			counter = null;
		}
	}

	/**
	 * This {@link Thread} counts time in intervals of 100 milliseconds and stops the program if
	 * the counted time exceeds 3000 milliseconds.
	 * 
	 * @author Miguel Angel Astor Romero.*
	 */
	class TimeCounter extends Thread {
		private boolean done;
		private long timeMilisBefore;

		/**
		 * Creates a new {@link TimeCounter}
		 */
		public TimeCounter() {
			done = false;
		}

		@Override
		public void run() {
			long timeMilisNow;

			/* Get the time before counting. */
			timeMilisBefore = System.currentTimeMillis();

			/* Start counting. */
			while(!done) {
				/* Sleep for 100 milliseconds. and then check the time. */
				try { Thread.sleep(100); } catch (InterruptedException e) { }
				timeMilisNow = System.currentTimeMillis();

				/* If the time delta since counting started is greater than 3 seconds then force quit. */
				if(timeMilisNow - timeMilisBefore > 3000)
					System.exit(0);
			}
		}

		/**
		 * Stops the counting loop.
		 */
		public void finish() {
			done = true;
		}
	}
}
