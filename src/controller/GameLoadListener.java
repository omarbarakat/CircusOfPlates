package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class GameLoadListener implements ActionListener {

	private static GameLoadListener instance;

	private JFileChooser fileChooser;

	private static FileFilter dataFilter = new FileFilter() {
		@Override
		public String getDescription() {
			return ".srl files";
		}

		@Override
		public boolean accept(File file) {

			if (file.isDirectory()) {
				return true;
			}

			String fileName = file.getName();
			int i = fileName.indexOf('.');

			String choiceExt = fileName.substring(i + 1);

			if (choiceExt != null) {
				if (choiceExt.equals("srl")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	};

	private GameLoadListener() {
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(dataFilter);
	}

	public static GameLoadListener getInstance() {
		if (instance == null)
			instance = new GameLoadListener();

		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		JFrame firstFrame = (JFrame) (((JButton) arg0.getSource()).getParent())
				.getParent().getParent().getParent().getParent();

		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = fileChooser.getSelectedFile();

			if (file.exists()) {
				try {
					GameLoader.getInstance().loadGame(file);
					firstFrame.setVisible(false);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Error: File Not Found!");
			}
		}
	}

}
