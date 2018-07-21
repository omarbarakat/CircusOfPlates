package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import utils.GameLevel;
import view.GameFrame;

public class LevelChangeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String x = JOptionPane
				.showInputDialog("Input The Level Number 1 - 2 - 3");

		x = x.trim();
		System.out.println(x);

		try {
			int i = Integer.parseInt(x);

			if (i == 1) {
				GameFrame.getInstance().setLevel(GameLevel.LEVEL_ONE);
			} else if (i == 2) {
				GameFrame.getInstance().setLevel(GameLevel.LEVEL_TWO);
			} else if (i == 3) {
				GameFrame.getInstance().setLevel(GameLevel.LEVEL_THREE);
			} else {
				throw new Exception("Invalid Input");
			}
		} catch (Exception e) {
			JOptionPane.showInternalMessageDialog(null, e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
