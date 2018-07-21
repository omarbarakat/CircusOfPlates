package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import view.GameFrame;
import model.plate.PlatePool;
import model.plate.factory.Plate;

@SuppressWarnings("serial")
public class Clown extends JLabel {

	private Stack<Plate> RH;
	private Stack<Plate> LH;
	private Integer score;

	final private static String CLOWN_FILE_NAME = "clown.png";
	final private static int LH_FROM_CENTER = -270;
	final private static int RH_FROM_CENTER = -10;
	final public static int HANDS_HIGHT = 420;

	public Clown() throws Exception {
		RH = new Stack<Plate>();
		LH = new Stack<Plate>();
		score = 0;
		try {
			ImageIcon ic = new ImageIcon(CLOWN_FILE_NAME);
			setIcon(ic);
		} catch (Exception e) {
			throw e;
		}
	}

	public Clown(Stack<Plate> lh, Stack<Plate> rh, Integer sc) throws Exception {
		RH = new Stack<Plate>();
		LH = new Stack<Plate>();
		score = sc;

		for (Plate p : rh) {
			addToRH(p);
			GameFrame.getInstance().addToGameWindow(p);
		}
		
		for (Plate p : lh) {
			addToLH(p);
			GameFrame.getInstance().addToGameWindow(p);
		}

		try {
			ImageIcon ic = new ImageIcon(CLOWN_FILE_NAME);
			setIcon(ic);
		} catch (Exception e) {
			throw e;
		}
	}

	public synchronized void addToRH(Plate p) {
		if (RH.size() != 0)
			p.setLocation(
					(int) (RH.peek().getX() + RH.peek().getWidth() / 2 - p
							.getWidth() / 2), (int) (RH.peek().getY() - p
							.getHeight()));
		else
			p.setLocation((int) (RH_FROM_CENTER + getX() + getWidth() / 2 - p
					.getWidth() / 2), HANDS_HIGHT);

		RH.add(p);
		p.setMotionState(Plate.CAPTURED);

		if(RH.size() == 20)
			GameFrame.getInstance().endGame(this);
		if (RH.size() < 3)
			return;
		Color c = p.getForeground();
		for (int i = RH.size() - 1; i > RH.size() - 4; i--)
			if (!RH.get(i).getForeground().equals(c))
				return;
		remove3FromRH();
	}

	public synchronized void addToLH(Plate p) {
		if (LH.size() != 0)
			p.setLocation(
					(int) (LH.peek().getX() + LH.peek().getWidth() / 2 - p
							.getWidth() / 2), (int) (LH.peek().getY() - p
							.getHeight()));
		else
			p.setLocation((int) (LH_FROM_CENTER + getX() + getWidth() / 2 - p
					.getWidth() / 2), HANDS_HIGHT);

		LH.add(p);
		p.setMotionState(Plate.CAPTURED);

		if(LH.size() == 20)
			GameFrame.getInstance().endGame(this);
		if (LH.size() < 3)
			return;
		Color c = p.getForeground();
		for (int i = LH.size() - 1; i > LH.size() - 4; i--)
			if (!LH.get(i).getForeground().equals(c))
				return;
		remove3FromLH();
		if(LH.size() == 15){
			System.out.println("calling");
			GameFrame.getInstance().endGame(this);
		}
	}

	private void remove3FromRH() {
		PlatePool p = PlatePool.getInstance();
		if (p != null) {
			p.releasePlate(RH.pop());
			p.releasePlate(RH.pop());
			p.releasePlate(RH.pop());
		} else {
			RH.pop();
			RH.pop();
			RH.pop();
		}
		score++;
		GameFrame.getInstance().setScores();
	}

	private void remove3FromLH() {
		PlatePool p = PlatePool.getInstance();
		if (p != null) {
			p.releasePlate(LH.pop());
			p.releasePlate(LH.pop());
			p.releasePlate(LH.pop());
		} else {
			LH.pop();
			LH.pop();
			LH.pop();
		}
		score++;
		GameFrame.getInstance().setScores();
	}

	public Rectangle getRHCatchLimits() {
		int xRH = RH_FROM_CENTER + getX() + getWidth() / 2;
		if (RH.isEmpty())
			return new Rectangle(xRH - 5, HANDS_HIGHT - 5, 10, 10);
		else {
			Plate p = RH.peek();
			return new Rectangle((int) (p.getX()), p.getY() - 1, p.getWidth(),
					5);
		}
	}

	public Rectangle getLHCatchLimits() {
		int xLH = LH_FROM_CENTER + getX() + getWidth() / 2;
		if (LH.isEmpty())
			return new Rectangle(xLH - 5, HANDS_HIGHT - 5, 10, 10);
		else {
			Plate p = LH.peek();
			return new Rectangle((int) (p.getX()), p.getY() - 1, p.getWidth(),
					5);
		}
	}

	public void updatePlatesPosition() {
		int xLH = LH_FROM_CENTER + getX() + getWidth() / 2;
		int xRH = RH_FROM_CENTER + getX() + getWidth() / 2;
		Plate p;

		if (RH != null)
			for (int i = 0; i < RH.size(); i++) {
				p = RH.get(i);
				p.setLocation((int) (xRH - p.getBounds().getWidth() / 2),
						(int) p.getBounds().getY());
			}
		if (LH != null)
			for (int i = 0; i < LH.size(); i++) {
				p = LH.get(i);
				p.setLocation((int) (xLH - p.getBounds().getWidth() / 2),
						(int) p.getBounds().getY());
			}
	}

	public int getScore() {
		return score;
	}

	public Stack<Plate> getRH() {
		return RH;
	}

	public void setRH(Stack<Plate> rH) {
		RH = rH;
	}

	public Stack<Plate> getLH() {
		return LH;
	}

	public void setLH(Stack<Plate> lH) {
		LH = lH;
	}

}