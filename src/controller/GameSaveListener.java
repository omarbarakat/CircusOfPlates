package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GameSaveListener implements ActionListener {

	private static GameSaveListener instance;

	private JFileChooser fileChooser;

	private GameSaveListener() {
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	public static GameSaveListener getInstance() {
		if (instance == null)
			instance = new GameSaveListener();

		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int returnVal = fileChooser.showSaveDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = fileChooser.getSelectedFile();

			String fileName = file.getName();
			int i = fileName.indexOf('.');
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "Input the right format");
				fileChooser.setVisible(false);
				return;
			}
			String choiceExt = fileName.substring(i + 1);
			if (choiceExt.equalsIgnoreCase("srl")) {
				try {
					file.getParentFile().mkdirs();
					file.createNewFile();
					GameSaver.getInstance().saveGame(file);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}
		}
	}

}
