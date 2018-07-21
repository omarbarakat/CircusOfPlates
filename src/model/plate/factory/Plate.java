package model.plate.factory;

import javax.swing.JLabel;

public abstract class Plate extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1956789671828160583L;

	private short motionState;

	public static final short CAPTURED = 0;
	public static final short MOVING_RIGHT = 1;
	public static final short MOVING_LEFT = 2;
	public static final short FALLING = 3;

	public Plate() {
		setSize(121, 21);
		motionState = MOVING_RIGHT;
	}

	public short getMotionState() {
		return motionState;
	}

	public void setMotionState(short s) {
		motionState = s;
	}
}