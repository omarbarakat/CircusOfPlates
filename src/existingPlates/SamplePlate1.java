package existingPlates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import model.plate.factory.Plate;

public class SamplePlate1 extends Plate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3534611177710204723L;

	final private Integer[][] VERTICES = { { 0, 20, 100, 120 },
			{ 0, 20, 20, 0 } };

	public SamplePlate1() {
		setSize(121, 21);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getForeground());

		int[][] vrts = new int[2][4];

		for (int i = 0; i < vrts.length; i++) {
			for (int j = 0; j < vrts[i].length; j++) {
				vrts[i][j] = VERTICES[i][j];
			}
		}

		g2d.fillPolygon(vrts[0], vrts[1], vrts[0].length);
		g2d.setColor(Color.black);
		g2d.drawPolygon(vrts[0], vrts[1], vrts[0].length);
		super.paintComponent(g2d);
	}
}
