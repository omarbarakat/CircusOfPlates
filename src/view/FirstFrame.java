package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import controller.GameLoadListener;
import controller.NewGameListener;

@SuppressWarnings("serial")
public class FirstFrame extends JFrame {

	private static FirstFrame instance;

	private JPanel panel;
	private JButton newGame;
	private JButton loadGame;

	private FirstFrame() {
		super("Circus Of Plates");
	}

	public static FirstFrame getInstance() {
		if (instance == null)
			instance = new FirstFrame();

		return instance;
	}

	public void showFrame() {
		newGame = new JButton("Start A New Game!");
		newGame.addActionListener(new NewGameListener());

		loadGame = new JButton("Load An Existing Game!");
		loadGame.addActionListener(GameLoadListener.getInstance());

		panel = new JPanel();
		panel.add(newGame);
		panel.add(loadGame);
		add(panel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
