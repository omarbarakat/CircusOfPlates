package model;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Shelf extends JLabel {

	private boolean moveDirection;
	private static final int THICKNESS = 5;
	public static final boolean RIGHT_MOTION = false;
	public static final boolean LEFT_MOTION = true;

	public Shelf(boolean direction) {
		moveDirection = direction;
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		if (moveDirection == RIGHT_MOTION)
			super.setBounds(0, y, width, THICKNESS);
		else if (getParent() != null)
			super.setBounds(getParent().getWidth() - width, y, width, THICKNESS);

		setOpaque(true);
	}

	public boolean getMoveDirection() {
		return moveDirection;
	}
}
