package my2048;
import java.awt.GridLayout;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel{
	protected My2048 game;
	protected JFrame frame = new JFrame();
	protected JLabel[] items = new JLabel[game.size*game.size];
	protected JPanel panel;
	
	public Panel(My2048 game)
	{
		this.game = game;
		
		this.setFrame();
		
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
			items[i].setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff"), 5));
			if(values[i].getValue() > 0)
				items[i].setOpaque(true);
			items[i].setBackground(values[i].background());
		}
		for (int i = 0; i < items.length; i++)
			itemsPanel.add(items[i]);
		return itemsPanel;
	}
	
	public JPanel setFrame()
	{
		frame.setTitle("2048");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setSize(500,400);
		panel = this.getItems();
		frame.setContentPane(this.panel);
		frame.setVisible(true);
		panel.repaint();
		this.panel.addKeyListener(new KeyAdapter(){
		      @Override
		      public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		          //resetGame();
		        }
//		        if (!canMove()) {
//		          myLose = true;
//		        }

//		        if (!myWin && !myLose) {
		          switch (e.getKeyCode()) {
		            case KeyEvent.VK_LEFT:
		            {
		            	game.moveGrid();
		            	game.output();
		            	setFrame();
		            	break;
		            }
		            case KeyEvent.VK_RIGHT:
		            {
		            	game.rotate(2);
		            	game.moveGrid();
		            	game.rotate(2);
		            	game.output();
		            	setFrame();
		            	break;
		            }
		            case KeyEvent.VK_DOWN:
		            {
		            	game.rotate(1);
		            	game.moveGrid();
		            	game.rotate(3);
		            	game.output();
		            	setFrame();
		            	break;
		            }
		            case KeyEvent.VK_UP:
		            {
		            	game.rotate(3);
		            	game.moveGrid();
		            	game.rotate(1);
		            	game.output();
		            	setFrame();
		            	break;
		            }
		          }
//		        }

//		        if (!myWin && !canMove()) {
//		          myLose = true;
//		        }

		      }
		    });
		panel.setFocusable(true);
		panel.requestFocusInWindow();
		return panel;
	}
}
