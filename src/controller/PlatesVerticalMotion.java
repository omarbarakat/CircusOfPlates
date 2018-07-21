package controller;

import java.io.Serializable;
import java.util.Vector;

import model.Clown;
import model.plate.PlatePool;
import model.plate.factory.Plate;

public class PlatesVerticalMotion implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7295721872652930044L;

	private Vector<Plate> plates;
	private final int Y_SPEED = 2; // 50 units per second
	private final int X_SPEED = 1; // 50 units per second
	private Clown[] clowns;

	public PlatesVerticalMotion(Clown[] c) {
		plates = new Vector<Plate>();
		clowns = c;
	}

	public void addFallingPlate(Plate p) {
		plates.add(p);
	}

	@Override
	public void run() {
		Plate p;
		PlatePool platePool = PlatePool.getInstance();

		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while (true) {
			for (int i = 0; i < plates.size(); i++) {
				p = plates.get(i);
				p.setLocation((p.getMotionState() == Plate.MOVING_RIGHT)?
						p.getX() + X_SPEED : p.getX() - X_SPEED
							, p.getY() + (int) Y_SPEED);
				if (p.getParent() == null
						|| p.getY() > p.getParent().getHeight()) {
					platePool.releasePlate(p);
					plates.remove(i);
				} else {
					for (int j = 0; j < clowns.length; j++) {
						if (p.getBounds().intersects(
								clowns[j].getLHCatchLimits())) {
							clowns[j].addToLH(p);
							plates.remove(i);
							break;
						} else if (p.getBounds().intersects(
								clowns[j].getRHCatchLimits())) {
							clowns[j].addToRH(p);
							plates.remove(i);
							break;
						}
					}
				}
			}

			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
				// do nothing
			}
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}

}
