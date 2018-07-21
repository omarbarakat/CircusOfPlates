package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import model.plate.PlatePool;
import utils.ClassContainer;
import view.GameFrame;

public class GameSaver {

	private static GameSaver instance;

	private GameSaver() {

	}

	public static GameSaver getInstance() {
		if (instance == null)
			instance = new GameSaver();

		return instance;
	}

	public void saveGame(File toSave) {

		// Save Motion
		try {
			List<Serializable> toBeSeriallized = new LinkedList<>();

			GameFrame gfinstance = GameFrame.getInstance();

			toBeSeriallized.add(gfinstance.getLevel());

			toBeSeriallized.add(gfinstance.getClown1().getLH());
			toBeSeriallized.add(gfinstance.getClown1().getRH());
			toBeSeriallized.add(gfinstance.getClown1().getScore());

			toBeSeriallized.add(gfinstance.getClown2().getLH());
			toBeSeriallized.add(gfinstance.getClown2().getRH());
			toBeSeriallized.add(gfinstance.getClown2().getScore());

			toBeSeriallized.add(ClassContainer.getInstance().getSupportedClasses());

			toBeSeriallized.add(gfinstance.getMotion1());
			toBeSeriallized.add(gfinstance.getMotion2());
			toBeSeriallized.add(gfinstance.getMotion3());
			toBeSeriallized.add(gfinstance.getMotion4());

			FileOutputStream fileOut = new FileOutputStream(toSave);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(toBeSeriallized);
			out.close();
			fileOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
