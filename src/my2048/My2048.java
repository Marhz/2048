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

	public void setHasWon(boolean hasWon)
	{
		this.hasWon = hasWon;
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
		this.grid = new Tile[this.size][this.size];
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
		if (getEmptyTiles().size() > 0) {
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
		for (int i = 0; i < this.size; i++) {
			moveLine(grid[i]);
		}
		this.clearMergedTiles();
		if (this.hasChanged()) {
			this.setNewTile();
			this.setHasChanged(false);
		}
	}

	private Tile[] moveLine(Tile[] line)
	{
		Tile[] oldLine = copyLine(line);

		for (int i = 1; i < this.size; i++) {
			moveTile(line, i);
		}
		if (!this.hasChanged() && this.compareLines(line, oldLine))
			this.setHasChanged(true);
		return line;
	}

	private Tile[] moveTile(Tile[] line, int position)
	{
		int i = position;
		while (i > 0 && line[i - 1].isEmpty()) {
			i--;
		}
		if (i > 0 && line[position].getValue() == line[i - 1].getValue() && !line[i - 1].hasMerged()) {
			line[i - 1].setValue(line[position].getValue() * 2);
			line[position].setValue(0);
			line[i - 1].setHasMerged(true);
			this.score += line[i - 1].getValue();
			if (line[i - 1].getValue() >= this.winningScore)
				this.setHasWon(true);
			return line;
		}
		if (i != position) {
			line[i].setValue(line[position].getValue());
			line[position].setValue(0);
		}
		return line;
	}
	
	public static void main(String[] args)
	{
		new My2048(6,2048);
	}
}
