package model.plate;

import java.awt.Container;
import java.util.LinkedList;
import java.util.Queue;

import view.GameFrame;
import model.plate.factory.Plate;
import model.plate.factory.PlateFactory;

public class PlatePool {

	private static final int DOZEN_SIZE = 50;
	private static PlatePool instance = null;
	private Queue<Plate> plates;

	private PlatePool() {
		plates = new LinkedList<>();
	}

	public static PlatePool getInstance() {
		if (instance == null)
			instance = new PlatePool();

		return instance;
	}

	public synchronized Plate getNewPlate() {
		if (plates.isEmpty())
			instantiate(DOZEN_SIZE);
		try {
			GameFrame.getInstance().addToGameWindow(plates.peek());
		} catch (Exception e) {

		}
		return plates.poll();
	}

	public synchronized void releasePlate(Plate p) {
		Container cont = p.getParent();
		try {
			cont.remove(p);
			cont.repaint();
		} catch (Exception e) {
		}

		plates.offer(p);
	}

	public synchronized void instantiate(int n) {
		try {
			for (int i = 0; i < n; i++)
				plates.offer(PlateFactory.getInstance().getNewPlate());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
			while (plates.size() != 0)
				plates.remove();
	}

	public static void setInstance(PlatePool p) {
		instance = p;
		instance.update();
	}

	public Queue<Plate> getPlates() {
		return plates;
	}

	public void setPlates(Queue<Plate> plates) {
		this.plates = plates;
	}
}