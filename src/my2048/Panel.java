package my2048;
import java.awt.GridLayout;

import javax.swing.*;
import java.awt.Font;
public class Panel{
	protected My2048 game;
	protected JFrame frame = new JFrame();
	protected JLabel[] items = new JLabel[game.size*game.size];
	
	public Panel(My2048 game)
	{
		this.game = game;
		frame.setTitle("2048");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setContentPane(getItems());
		frame.setVisible(true);
		frame.pack();
	}
	
	protected JPanel getItems()
	{
		JPanel itemsPanel = new JPanel();
		itemsPanel.setLayout(new GridLayout(game.size, game.size));
		Tile values[] = game.toSimpleArray();
		for (int i = 0; i < (game.size*game.size); i++)
		{
			items[i] = new JLabel("",SwingConstants.CENTER);
			if(values[i].getValue() > 0)
			items[i].setText(""+values[i].getValue());
			items[i].setFont(new Font("Arial",1, values[i].fontSize()));
			if(values[i].getValue() > 0)
				items[i].setOpaque(true);
			items[i].setBackground(values[i].background());
		}
		for (int i = 0; i < items.length; i++)
			itemsPanel.add(items[i]);
		return itemsPanel;
	}
}
