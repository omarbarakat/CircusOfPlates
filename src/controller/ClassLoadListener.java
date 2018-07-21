package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import utils.ClassContainer;

public class ClassLoadListener implements ActionListener {

	private static ClassLoadListener instance;

	private JFileChooser fileChooser;

	private static FileFilter classFilter = new FileFilter() {
		@Override
		public String getDescription() {
			return ".class files";
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
				if (choiceExt.equals("class")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	};

	private ClassLoadListener() {
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(classFilter);
	}

	public static ClassLoadListener getInstance() {
		if (instance == null)
			instance = new ClassLoadListener();

		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = fileChooser.getSelectedFile();

			if (file.exists()) {
				try {
					if (ClassContainer.getInstance().getSupportedClasses()
							.get(file.getName().split("\\.")[0]) == null) {
						ClassContainer.getInstance().loadClass(file);
					}
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
