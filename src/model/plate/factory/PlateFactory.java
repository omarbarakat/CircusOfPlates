package model.plate.factory;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.plate.PlatePool;

import utils.ClassContainer;
import utils.GameLevel;

public class PlateFactory {

	private static PlateFactory instance;
	private List<Color> colors;
	private GameLevel level;

	private PlateFactory() {
		colors = new LinkedList<Color>();
		level = GameLevel.LEVEL_ONE;
		fillColors();
	}

	private void fillColors() {
		switch (level) {
		// 3 colors
		case LEVEL_ONE: {
			call(3);
		}
			break;

		// 4 colors
		case LEVEL_TWO: {
			call(4);
		}
			break;

		// 5 colors
		case LEVEL_THREE: {
			call(5);
		}
			break;
		}
		PlatePool.getInstance().update();
	}

	private void call(int i) {
		if (colors.size() <= i) {
			Random randomizer = new Random(1000 * System.nanoTime());
			for (int j = 0; j <= i - colors.size(); j++) {
				Color randomColor = new Color(randomizer.nextFloat(),
						randomizer.nextFloat(), randomizer.nextFloat());
				if (!colors.contains(randomColor))
					colors.add(randomColor);
				else
					j--;
			}
		} else {
			for (int j = 0; j < colors.size() - i; j++) {
				colors.remove(colors.size() - j - 1);
			}
		}
	}

	public static PlateFactory getInstance() {
		if (instance == null)
			instance = new PlateFactory();
		return instance;
	}

	public Plate getNewPlate() {
		Random randomizer = new Random(System.nanoTime());

		int random = randomizer.nextInt();
		int i = (random > 0 ? random : -random)
				% ClassContainer.getInstance().getSupportedClasses().size();

		Object[] keys = ClassContainer.getInstance().getSupportedClasses()
				.keySet().toArray();

		String classKey = (String) keys[i];
		Class<?> toInstantiate = ClassContainer.getInstance()
				.getSupportedClasses().get(classKey);

		try {
			Plate instantiated = (Plate) toInstantiate.newInstance();

			random = randomizer.nextInt();
			instantiated.setForeground(colors.get((random >= 0 ? random
					: -random) % colors.size()));
			return instantiated;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setLevel(GameLevel level) {
		this.level = level;
		fillColors();
	}

	public static void setInstance(PlateFactory f) {
		instance = f;
		instance.fillColors();
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
}