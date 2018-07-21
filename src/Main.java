import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import controller.ClassLoadListener;
import controller.GameLoader;
import controller.GameLoadListener;
import controller.GameSaver;
import controller.GameSaveListener;
import model.plate.PlatePool;
import model.plate.factory.PlateFactory;
import utils.ClassContainer;
import utils.MyClassLoader;
import view.FirstFrame;

public class Main {

	public static void main(String[] args) {
		try {
			StartAllServices();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						FirstFrame.getInstance().showFrame();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void StartAllServices() throws Exception {
		startDynamicClassLoading();
		loadExistingClasses();
		startPlateFactory();
		startPlatePool();
		startGameSaving();
		startGameLoading();
	}

	private static void startDynamicClassLoading() {
		ClassLoadListener.getInstance();
		MyClassLoader.getInstance();
		ClassContainer.getInstance();
	}

	private static void startGameLoading() {
		GameLoadListener.getInstance();
		GameLoader.getInstance();
	}

	private static void startGameSaving() {
		GameSaveListener.getInstance();
		GameSaver.getInstance();
	}

	private static void startPlatePool() {
		PlatePool.getInstance();
	}

	private static void startPlateFactory() throws Exception {
		PlateFactory.getInstance();
	}

	private static void loadExistingClasses() throws Exception {
		File existingFiles[] = new File(Main.class.getResource(
				"existingPlates/").toURI()).listFiles();

		ClassContainer currentContainer = ClassContainer.getInstance();
		for (int i = 0; i < existingFiles.length; i++) {
			currentContainer.loadClass(existingFiles[i]);
		}
	}
}
