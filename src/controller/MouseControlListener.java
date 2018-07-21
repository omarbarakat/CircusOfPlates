package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import model.Clown;

public class MouseControlListener implements MouseMotionListener {

	private Clown clown = null;

	public MouseControlListener(Clown c) {
		clown = c;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		clown.setLocation(e.getX() - clown.getIcon().getIconWidth() / 2,
				clown.getY());
		clown.updatePlatesPosition();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
}
