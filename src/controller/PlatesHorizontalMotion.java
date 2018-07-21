package controller;

import java.io.Serializable;
import java.util.Vector;

import model.Clown;
import model.Shelf;
import model.plate.PlatePool;
import model.plate.factory.Plate;

public class PlatesHorizontalMotion implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7757108001694900090L;

	private Vector<Plate> plates;
	private final Integer SPEED = 2; // 50 units per second
	private Integer shelfEnd;
	private Integer height;
	private Boolean moveDirection;
	private PlatesVerticalMotion fallingMotion;

	private static final Boolean RIGHT_MOTION = false;
	private static final Boolean LEFT_MOTION = true;
	private final Integer SEPARAING_TIME = 1500; // 50 units per second

	public PlatesHorizontalMotion(Shelf s, Clown[] clowns) {
		plates = new Vector<Plate>();
		moveDirection = s.getMoveDirection();
		height = s.getY();
		shelfEnd = (moveDirection == RIGHT_MOTION) ? s.getWidth() : s.getX();
		fallingMotion = new PlatesVerticalMotion(clowns);
	}

	@Override
	public void run() {
		Plate p;
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		PlatePool platePool = PlatePool.getInstance();
		int timeSinceLastRelease = 0;

		Thread th = new Thread(fallingMotion);
		th.start();

		while (true) {
			if (timeSinceLastRelease == 0) {
				p = platePool.getNewPlate();
				if (moveDirection == RIGHT_MOTION) {
					p.setLocation(-p.getWidth(), height - p.getHeight());
					p.setMotionState(Plate.MOVING_RIGHT);
				} else if (p.getParent() != null) {
					p.setLocation(p.getParent().getWidth() + p.getWidth(),
							height - p.getHeight());
					p.setMotionState(Plate.MOVING_LEFT);
				}
				plates.add(p);
			}

			for (int i = 0; i < plates.size(); i++) {
				p = plates.get(i);
				if (moveDirection == LEFT_MOTION)
					p.setLocation(p.getX() - (int) SPEED, p.getY());
				else
					p.setLocation(p.getX() + (int) SPEED, p.getY());

				if (moveDirection == LEFT_MOTION
						&& p.getX() < shelfEnd - p.getWidth()
						|| moveDirection == RIGHT_MOTION && p.getX() > shelfEnd) {
					plates.remove(i);
					if (fallingMotion != null)
						fallingMotion.addFallingPlate(p);
					else
						platePool.releasePlate(p);
				}
			}

			try {
				Thread.sleep(20);
				timeSinceLastRelease += 20;
				timeSinceLastRelease %= SEPARAING_TIME;
			} catch (InterruptedException ex) {
				// do nothing
			}
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}

}
