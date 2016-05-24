package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import lejos.nxt.Sound;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.FeatureDetectorsHandler;
import ve.ucv.ciens.cicore.icaro.ryabi.sensors.SensorEventsQueue;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.States;

@SuppressWarnings("unused")
public class VictoryBehavior extends BaseBehavior {
	private static final int C = 262;
	private static final int D = 287;
	private static final int E = 320;
	private static final int F = 349;
	private static final int G = 392;
	private static final int A = 440;
	private static final int B = 494;

	private RobotStateSingleton     state;
	private SensorEventsQueue       queue;
	private FeatureDetectorsHandler detectorHandler;

	public VictoryBehavior() {
		super(null, null, null, null, null, 0.0f, 0.0f);
		state = RobotStateSingleton.getInstance();
		this.queue = SensorEventsQueue.getInstance();
		this.detectorHandler = FeatureDetectorsHandler.getInstance();
	}

	@Override
	public boolean takeControl() {
		if(state.getState() == States.VICTORY) {
			detectorHandler.disableAllDetectors();
			return true;
		}

		if(queue.hasNextLightSensorEvent()) {
			state.setState(States.VICTORY);
			detectorHandler.disableAllDetectors();

			while(queue.hasNextLightSensorEvent())
				queue.getNextLightSensorEvent();

			return true;

		}

		return false;
	}

	@Override
	public void action() {
		playToneDuration(B, 250);
		playToneDuration(A, 250);
		playToneDuration(G, 500);
		playToneDuration(B, 250);
		playToneDuration(A, 250);
		playToneDuration(G, 500);
		playToneDuration(G, 125);
		playToneDuration(G, 125);
		playToneDuration(G, 125);
		playToneDuration(G, 125);
		playToneDuration(A, 125);
		playToneDuration(A, 125);
		playToneDuration(A, 125);
		playToneDuration(A, 125);
		playToneDuration(B, 250);
		playToneDuration(A, 250);
		playToneDuration(G, 500);
		state.setState(States.DONE);
	}

	@Override
	public void suppress() { }


	private void playToneDuration(int tone, int milis) {
		Sound.playTone(tone, milis);
		try { Thread.sleep(milis); } catch(InterruptedException ie) {}
	}
}
