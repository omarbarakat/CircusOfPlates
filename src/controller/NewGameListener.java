package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import view.GameFrame;

public class NewGameListener implements ActionListener {

	public NewGameListener() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFrame firstFrame = (JFrame) (((JButton) arg0.getSource()).getParent())
				.getParent().getParent().getParent().getParent();
		firstFrame.setVisible(false);
		try {
			GameFrame.getInstance().showFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
