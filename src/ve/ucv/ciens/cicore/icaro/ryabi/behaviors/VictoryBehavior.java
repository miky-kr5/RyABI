package ve.ucv.ciens.cicore.icaro.ryabi.behaviors;

import java.io.File;

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.ArcRotateMoveController;
import ve.ucv.ciens.cicore.icaro.ryabi.utils.RobotStateSingleton;

public class VictoryBehavior extends BaseBehavior {
	private RobotStateSingleton state;
	private File                musicSample;

	public VictoryBehavior(ArcRotateMoveController pilot, UltrasonicSensor sonar, TouchSensor touch, LightSensor light,
			CompassHTSensor compass, float wheelDiameter, float trackWidth) {
		super(pilot, sonar, touch, light, compass, wheelDiameter, trackWidth);

		state = RobotStateSingleton.getInstance();
		musicSample = new File("victory.wav");
		if(!musicSample.exists())
			musicSample = null;
	}

	@Override
	public boolean takeControl() {
		return state.getState() == RobotStateSingleton.States.VICTORY;
	}

	@Override
	public void action() {
		if(musicSample != null) {
			int milis = Sound.playSample(musicSample);
			try { Thread.sleep(milis); } catch(InterruptedException ie) { }
		}
		state.setState(RobotStateSingleton.States.DONE);
	}

	@Override
	public void suppress() { }

}
