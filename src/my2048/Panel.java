package my2048;

import java.awt.GridLayout;

import javax.swing.*;

import my2048Grid.Tile;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel {

	private My2048 game;
	private JFrame frame = new JFrame();
	private JLabel[] items;
	private JPanel panel;

	public Panel(My2048 game) {
		this.game = game;
		frame.setTitle("2048");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setSize(500, 400);
		this.setFrame();
	}

	private JPanel getPanel()
	{
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel itemsPanel = getItems(new JPanel());
		JLabel bottomBar = new JLabel("<html>Score : " + game.getScore() + "	Echap : recommencer");
		panel.add(itemsPanel);
		panel.add(bottomBar);
		return panel;
	}

	private JPanel getItems(JPanel itemsPanel)
	{
		items = new JLabel[game.size * game.size];
		itemsPanel.setLayout(new GridLayout(game.size, game.size));
		Tile values[] = game.toSimpleArray();
		for (int i = 0; i < (game.size * game.size); i++) {
			items[i] = new JLabel("", SwingConstants.CENTER);
			if (values[i].getValue() > 0)
				items[i].setText("" + values[i].getValue());
			items[i].setFont(new Font("Arial", 1, values[i].fontSize()));
			items[i].setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff"), 5));
			items[i].setOpaque(true);
			items[i].setBackground(values[i].background());
		}
		for (int i = 0; i < items.length; i++)
			itemsPanel.add(items[i]);
		return itemsPanel;
	}

	private JPanel setFrame()
	{
		panel = this.getPanel();
		frame.setContentPane(this.panel);
		frame.setVisible(true);
		panel.repaint();
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		panel.addKeyListener(gameKeyAdapter());

		return panel;
	}

	private KeyAdapter gameKeyAdapter()
	{
		return new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e)
			{
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE: {
					game.setNewGame();
					break;
				}
				case KeyEvent.VK_LEFT: {
					game.moveGrid();
					break;
				}
				case KeyEvent.VK_RIGHT: {
					game.rotate(2);
					game.moveGrid();
					game.rotate(2);
					break;
				}
				case KeyEvent.VK_DOWN: {
					game.rotate(1);
					game.moveGrid();
					game.rotate(3);
					break;
				}
				case KeyEvent.VK_UP: {
					game.rotate(3);
					game.moveGrid();
					game.rotate(1);
					break;
				}
				}
				if ((game.hasWon() && !game.hasWonAndContinues()) || game.hasLost()) {
					endGame();
				} else {
					// game.output();
					setFrame();
				}

			}
		};
	}

	private void endGame()
	{
		JPanel overlay = new JPanel(new GridLayout(1, 1));
		String text = new String(
				!game.hasLost() ? "<html><h1>Bravo vous avez gagné!</h1>Score : " + game.getScore()
				+ "<br/>Appuyez sur echap pour recommencer ou espace pour continuer.</html>"
				: "<html><h1>Perdu!</h1>Score : " + game.getScore()
				+ "<br/>Appuyez sur echap pour recommencer.</html>");
		overlay.setBackground(Color.decode(!game.hasLost() ? "#0a9924" : "#11d6d2"));
		overlay.setSize(frame.getSize());
		overlay.add(new JLabel(text, SwingConstants.CENTER));
		frame.setContentPane(overlay);
		frame.repaint();
		overlay.setFocusable(true);
		overlay.requestFocusInWindow();
		overlay.addKeyListener(endGameKeyAdapter());
	}
	
	private KeyAdapter endGameKeyAdapter()
	{
		return new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					game.setNewGame();
					setFrame();
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE && !game.hasLost()) {
					game.setHasWonAndContinues(true);
					setFrame();
				}
			}
		};
	}
	
}
