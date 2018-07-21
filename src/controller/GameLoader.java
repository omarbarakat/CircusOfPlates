package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import utils.ClassContainer;
import utils.GameLevel;
import view.GameFrame;
import model.Clown;
import model.plate.PlatePool;
import model.plate.factory.Plate;
import model.plate.factory.PlateFactory;

public class GameLoader {

	private static GameLoader instance;

	private GameLoader() {

	}

	public static GameLoader getInstance() {
		if (instance == null)
			instance = new GameLoader();

		return instance;
	}

	@SuppressWarnings("unchecked")
	public void loadGame(File toLoad) {

		try {
			FileInputStream fileIn = new FileInputStream(toLoad);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			List<Object> loaded = (List<Object>) in.readObject();

			// ===============================================================

			GameLevel lvl = (GameLevel) loaded.get(0);

			Stack<Plate> c1lh = (Stack<Plate>) loaded.get(1);
			Stack<Plate> c1rh = (Stack<Plate>) loaded.get(2);
			Integer c1Sc = (Integer) loaded.get(3);

			Stack<Plate> c2lh = (Stack<Plate>) loaded.get(4);
			Stack<Plate> c2rh = (Stack<Plate>) loaded.get(5);
			Integer c2Sc = (Integer) loaded.get(6);

			Hashtable<String, Class<?>> sc = (Hashtable<String, Class<?>>) loaded
					.get(7);

			PlatesHorizontalMotion m1 = (PlatesHorizontalMotion) loaded.get(8);
			PlatesHorizontalMotion m2 = (PlatesHorizontalMotion) loaded.get(9);
			PlatesHorizontalMotion m3 = (PlatesHorizontalMotion) loaded.get(10);
			PlatesHorizontalMotion m4 = (PlatesHorizontalMotion) loaded.get(11);

			// ===============================================================
			GameFrame gfinstance = GameFrame.getInstance();

			gfinstance.setLevel(lvl);

			gfinstance.setClown1(new Clown(c1lh, c1rh, c1Sc));
			gfinstance.setClown2(new Clown(c2lh, c2rh, c2Sc));

			gfinstance.setMotion1(m1);
			gfinstance.setMotion2(m2);
			gfinstance.setMotion3(m3);
			gfinstance.setMotion4(m4);

			ClassContainer.getInstance().setSupportedClasses(sc);
			PlateFactory.getInstance().setLevel(lvl);

			gfinstance.showFrame();

			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
}
