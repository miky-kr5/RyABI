package ve.ucv.ciens.cicore.icaro.ryabi.utils;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

public class QuitButtonListener implements ButtonListener {
	TimeCounter counter;

	@Override
	public void buttonPressed(Button b) {
		counter = new TimeCounter();
		counter.start();
	}

	@Override
	public void buttonReleased(Button b) {
		if(counter != null) {
			counter.finish();
			counter = null;
		}
	}

	class TimeCounter extends Thread {
		private boolean done;
		private long timeMilisBefore;

		public TimeCounter() {
			done = false;
		}

		@Override
		public void run() {
			long timeMilisNow;

			timeMilisBefore = System.currentTimeMillis();
			while(!done) {
				try { Thread.sleep(100); } catch (InterruptedException e) { }
				timeMilisNow = System.currentTimeMillis();

				if(timeMilisNow - timeMilisBefore > 3000)
					System.exit(0);
			}
		}

		public void finish() {
			done = true;
		}
	}
}
