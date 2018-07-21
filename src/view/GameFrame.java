package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utils.GameLevel;
import controller.ClassLoadListener;
import controller.GameSaveListener;
import controller.LevelChangeListener;
import controller.PlatesHorizontalMotion;
import model.Clown;
import model.Shelf;
import model.plate.factory.PlateFactory;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

	private static GameFrame instance;

	private JPanel backgroundPanel;
	private GridBagConstraints co;
	private JLayeredPane layers;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem saveItem;
	private JMenuItem loadItem;
	private JMenuItem changeLevel;

	private GameLevel level;

	// player1 : uses mouse
	private Clown clown1;
	private JLabel clown1Score;

	// player2 : uses keyboard
	private Clown clown2;
	private JLabel clown2Score;

	private Shelf shelf1;
	private Shelf shelf2;
	private Shelf shelf3;
	private Shelf shelf4;

	private PlatesHorizontalMotion motion1 = null;
	private PlatesHorizontalMotion motion2 = null;
	private PlatesHorizontalMotion motion3 = null;
	private PlatesHorizontalMotion motion4 = null;

	private Thread t1;
	private Thread t2;
	private Thread t3;
	private Thread t4;

	private GameFrame() {
		super("Circus Of Plates");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public static GameFrame getInstance() {
		if (instance == null)
			instance = new GameFrame();

		return instance;
	}

	public void showFrame() throws Exception {

		if (level == null)
			level = GameLevel.LEVEL_ONE;

		prepareMainFrame();
		prepareMainPanel();
		createAndShowClowns();
		createAndShowShelves();
		showMainFrame();
		startMoving();
	}

	public void setScores() {
		clown1Score.setText("Mouse: " + clown1.getScore());
		clown2Score.setText("Keys: " + clown2.getScore());
	}

	public void startMoving() {

		if (motion1 == null)
			motion1 = new PlatesHorizontalMotion(shelf1, new Clown[] { clown1,
					clown2 });
		t1 = new Thread(motion1);
		t1.start();

		if (motion2 == null)
			motion2 = new PlatesHorizontalMotion(shelf2, new Clown[] { clown1,
					clown2 });
		t2 = new Thread(motion2);
		t2.start();

		if (motion3 == null)
			motion3 = new PlatesHorizontalMotion(shelf3, new Clown[] { clown1,
					clown2 });
		t3 = new Thread(motion3);
		t3.start();

		if (motion4 == null)
			motion4 = new PlatesHorizontalMotion(shelf4, new Clown[] { clown1,
					clown2 });
		t4 = new Thread(motion4);
		t4.start();

	}

	private void createAndShowShelves() {

		shelf1 = new Shelf(Shelf.LEFT_MOTION);
		layers.add(shelf1);
		shelf1.setBackground(Color.blue);
		shelf1.setBounds(100, 60, 500, 60);

		shelf2 = new Shelf(Shelf.RIGHT_MOTION);
		layers.add(shelf2);
		shelf2.setBackground(Color.blue);
		shelf2.setBounds(100, 60, 500, 60);

		shelf3 = new Shelf(Shelf.LEFT_MOTION);
		layers.add(shelf3);
		shelf3.setBackground(Color.blue);
		shelf3.setBounds(100, 110, 100, 60);

		shelf4 = new Shelf(Shelf.RIGHT_MOTION);
		layers.add(shelf4);
		shelf4.setBackground(Color.blue);
		shelf4.setBounds(100, 110, 100, 60);
	}

	public void endGame(Clown c) {
		JOptionPane.showConfirmDialog(this, c == clown2 ? "Mouse Wins" : "Keys Wins");
		setVisible(false);
	}

	private void createAndShowClowns() throws Exception {
		if (clown1 == null)
			clown1 = new Clown();

		clown1.setBounds(100, 400, 600, 400);
		layers.add(clown1);
		addMouseMotionListener(new controller.MouseControlListener(clown1));

		System.out.println(clown1.getLH().size());

		if (clown2 == null)
			clown2 = new Clown();

		clown2.setBounds(100, 400, 600, 400);
		layers.add(clown2);
		addKeyListener(new controller.KeyboardControlListener(clown2));

		co.gridwidth = 1;
		co.gridheight = 1;

		clown1Score = new JLabel("Mouse: " + clown1.getScore());
		co.gridx = 0;
		co.gridy = 0;
		backgroundPanel.add(clown1Score, co);

		clown2Score = new JLabel("Keys: " + clown2.getScore());
		co.gridx = 500;
		co.gridy = 0;
		backgroundPanel.add(clown2Score, co);

		co.gridx = 250;
		co.gridy = 0;
		backgroundPanel.add(new JLabel("     "), co);
	}

	private void showMainFrame() {
		// setting the final state of the JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private void prepareMainFrame() {

		// setting the menu bar
		menubar = new JMenuBar();

		menu = new JMenu("Menu");
		menubar.add(menu);

		saveItem = new JMenuItem("Save Game");
		saveItem.addActionListener(GameSaveListener.getInstance());
		menu.add(saveItem);

		loadItem = new JMenuItem("Load Plug-in");
		loadItem.addActionListener(ClassLoadListener.getInstance());
		menu.add(loadItem);

		changeLevel = new JMenuItem("Change The Level");
		changeLevel.addActionListener(new LevelChangeListener());
		menu.add(changeLevel);

		setJMenuBar(menubar);
	}

	private void prepareMainPanel() {
		// setting layout
		co = new GridBagConstraints();
		co.fill = GridBagConstraints.HORIZONTAL;

		// setting background panel
		backgroundPanel = new JPanel(new GridBagLayout());

		// setting the layered panes
		layers = new JLayeredPane();
		layers.setOpaque(true);
		layers.setBackground(Color.lightGray);
		layers.setPreferredSize(new Dimension(1300, 650));
		layers.setBounds(0, 0, 1300, 650);

		co.gridx = 0;
		co.gridy = 5;
		co.gridwidth = 1000;
		co.gridheight = 1000;

		backgroundPanel.add(layers, co);
		add(backgroundPanel);
	}

	public synchronized void addToGameWindow(Component c) {
		if (c != null && c.getParent() != layers)
			layers.add(c);
	}

	public GameLevel getLevel() {
		return level;
	}

	public void setLevel(GameLevel level) {
		this.level = level;
		try {
			PlateFactory.getInstance().setLevel(level);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Clown getClown1() {
		return clown1;
	}

	public Clown getClown2() {
		return clown2;
	}

	public Shelf getShelf1() {
		return shelf1;
	}

	public void setShelf1(Shelf shelf1) {
		this.shelf1 = shelf1;
	}

	public Shelf getShelf2() {
		return shelf2;
	}

	public void setShelf2(Shelf shelf2) {
		this.shelf2 = shelf2;
	}

	public Shelf getShelf3() {
		return shelf3;
	}

	public void setShelf3(Shelf shelf3) {
		this.shelf3 = shelf3;
	}

	public Shelf getShelf4() {
		return shelf4;
	}

	public void setShelf4(Shelf shelf4) {
		this.shelf4 = shelf4;
	}

	public PlatesHorizontalMotion getMotion1() {
		return motion1;
	}

	public void setMotion1(PlatesHorizontalMotion motion1) {
		this.motion1 = motion1;
	}

	public PlatesHorizontalMotion getMotion2() {
		return motion2;
	}

	public void setMotion2(PlatesHorizontalMotion motion2) {
		this.motion2 = motion2;
	}

	public PlatesHorizontalMotion getMotion3() {
		return motion3;
	}

	public void setMotion3(PlatesHorizontalMotion motion3) {
		this.motion3 = motion3;
	}

	public PlatesHorizontalMotion getMotion4() {
		return motion4;
	}

	public void setMotion4(PlatesHorizontalMotion motion4) {
		this.motion4 = motion4;
	}

	public void setClown1(Clown clown1) {
		this.clown1 = clown1;
		System.out.println("Here is one");
	}

	public void setClown2(Clown clown2) {
		this.clown2 = clown2;
	}

	public void updateScores() {
		clown1Score.setText("" + clown1.getScore());
		clown2Score.setText("" + clown2.getScore());
	}

	public JLabel getClown1Score() {
		return clown1Score;
	}

	public void setClown1Score(JLabel clown1Score) {
		this.clown1Score = clown1Score;
	}

	public JLabel getClown2Score() {
		return clown2Score;
	}

	public void setClown2Score(JLabel clown2Score) {
		this.clown2Score = clown2Score;
	}
}