package my2048;

import my2048Grid.Grid;
import my2048Grid.Tile;

public class My2048 extends Grid{

	private int winningScore;
	private boolean hasWon;
	private boolean hasWonAndContinues;
	private int score;

	public boolean hasWon()
	{
		return hasWon;
	}

	public boolean hasWonAndContinues()
	{
		return hasWonAndContinues;
	}

	public void setHasWonAndContinues(boolean hasWonAndContinues)
	{
		this.hasWonAndContinues = hasWonAndContinues;
	}

	public int getScore()
	{
		return this.score;
	}
	
	public My2048() {
		super(4);
		this.setStartingGrid();
		this.winningScore = 2048;
		new Panel(this);

	}
	
	public My2048(int size, int winningScore) {
		super(size);
		this.grid = new Tile[this.size][this.size];
		this.setStartingGrid();
		this.winningScore = winningScore;
		new Panel(this);

	}
	
	public void setNewGame()
	{
		this.score = 0;
		this.hasWon = false;
		this.hasWonAndContinues = false;
		super.setStartingGrid();
	}

	public boolean hasLost()
	{
		if ((hasWon && !hasWonAndContinues) || super.getEmptyTiles().size() > 0) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			for (int y = 0; y < size; y++) {
				if ((i < size-1 && grid[i][y].getValue() == grid[i + 1][y].getValue())
					|| ((y < size-1) && grid[i][y].getValue() == grid[i][y + 1].getValue())) {
					return false;
				}
			}
		}
		return true;
	}

	public void output()
	{
		for (int i = 0; i < this.size; i++) {
			for (int y = 0; y < this.size; y++) {
				System.out.print(this.grid[i][y].getValue()+" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void moveGrid()
	{
		int[] results = new int[2];
		
		for (int i = 0; i < this.size; i++) {
			results = super.moveLine(grid[i]);
			this.score += results[0];
			if(results[1] >= this.winningScore) this.hasWon = true;
		}
		System.out.println();
		this.clearMergedTiles();
		if (super.hasChanged()) {
			super.setNewTile();
			super.setHasChanged(false);
		}
	}
	
	public static void main(String[] args)
	{
		new My2048();
	}
}
