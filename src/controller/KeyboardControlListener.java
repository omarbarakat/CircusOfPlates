package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Clown;

public class KeyboardControlListener implements KeyListener {

	private Clown clown = null;

	public KeyboardControlListener(Clown c) {
		clown = c;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_RIGHT)
			clown.setLocation(clown.getX() < 18 * 70 ? clown.getX() + 70
					: 18 * 70, clown.getY());
		else if (event.getKeyCode() == KeyEvent.VK_LEFT)
			clown.setLocation(clown.getX() > -170 ? clown.getX() - 70 : -255,
					clown.getY());
		else
			return;
		clown.updatePlatesPosition();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
