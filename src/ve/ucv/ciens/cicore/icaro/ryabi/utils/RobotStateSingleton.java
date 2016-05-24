package ve.ucv.ciens.cicore.icaro.ryabi.utils;

public final class RobotStateSingleton {
	private States state;

	private RobotStateSingleton() {
		state = States.WANDER;
	}

	private static class SingletonHolder {
		private static final RobotStateSingleton INSTANCE = new RobotStateSingleton();
	}

	public static RobotStateSingleton getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public synchronized void setState(States state) {
		this.state = state;
	}

	public States getState() {
		return this.state;
	}
}
