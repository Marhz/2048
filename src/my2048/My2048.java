package my2048;

import java.util.ArrayList;

public class My2048 {

	public final int size;
	private int winningScore;
	private boolean hasWon;
	private boolean hasWonAndContinues;
	private boolean hasChanged;
	private Tile[][] grid;
	private int score;

	public boolean hasChanged()
	{
		return hasChanged;
	}

	public void setHasChanged(boolean hasChanged)
	{
		this.hasChanged = hasChanged;
	}

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

	public void setHasWonAndContinuers(boolean hasWonAndContinues)
	{
		this.hasWonAndContinues = hasWonAndContinues;
	}

	public int getScore()
	{
		return this.score;
	}

	public My2048() {
		this.size = 3;
		this.grid = new Tile[this.size][this.size];
		this.setStartingGrid();
		this.winningScore = 20000;

	}

	private void setNewTile()
	{
		ArrayList<Coordinates> emptyTiles = this.getEmptyTiles();
		int rand = (int) (Math.random() * (emptyTiles.size()));
		int value = (Math.random() < 0.9) ? 2 : 4;
		Coordinates emptyTile = emptyTiles.get(rand);
		this.grid[emptyTile.getX()][emptyTile.getY()].setValue(value);
	}

	private ArrayList<Coordinates> getEmptyTiles()
	{
		ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
		for (int i = 0; i < this.size; i++) {
			for (int y = 0; y < this.size; y++) {
				if (this.grid[i][y].isEmpty()) {
					Coordinates tileCoordinates = new Coordinates(i, y);
					coordinates.add(tileCoordinates);
				}
			}
		}
		return coordinates;
	}

	public void output()
	{
		for (int i = 0; i < this.size; i++) {
			for (int y = 0; y < this.size; y++) {
				System.out.print(this.grid[i][y].getValue());
			}
			System.out.println();
		}
	}

	public void rotate(int rotations)
	{
		for (int i = 0; i < rotations; i++)
			this.rotateGrid();
	}

	private void rotateGrid()
	{
		Tile[][] newGrid = new Tile[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			newGrid[i] = this.getLine(i);
		}
		this.grid = newGrid;
	}

	private Tile[] getLine(int line)
	{
		Tile[] newLine = new Tile[this.size];
		for (int i = 0; i < this.size; i++) {
			newLine[i] = this.grid[(this.size - 1) - i][line];
		}
		return newLine;
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

	private boolean compareLines(Tile[] line1, Tile[] line2)
	{
		for (int i = 0; i < this.size; i++) {
			if (line1[i].getValue() != line2[i].getValue())
				return true;
		}
		return false;
	}

	private void clearMergedTiles()
	{
		for (int i = 0; i < size; i++) {
			for (int y = 0; y < size; y++)
				grid[i][y].setHasMerged(false);
		}
	}

	private Tile[] copyLine(Tile[] line)
	{
		Tile[] newLine = new Tile[size];
		for (int i = 0; i < size; i++) {
			newLine[i] = new Tile();
			newLine[i].setValue(line[i].getValue());
		}
		return newLine;
	}

	public Tile[] toSimpleArray()
	{
		Tile[] arrayGrid = new Tile[(size * size)];
		for (int i = 0; i < (size * size); i++) {
			arrayGrid[i] = new Tile();
			arrayGrid[i].setValue(grid[(i / size)][(i % size)].getValue());
		}
		return arrayGrid;
	}

	public void setStartingGrid()
	{
		this.score = 0;
		this.hasChanged = false;
		this.hasWon = false;
		this.hasWonAndContinues = false;
		for (int i = 0; i < this.size; i++) {
			for (int y = 0; y < this.size; y++) {
				this.grid[i][y] = new Tile();
			}
		}
		this.setNewTile();
		this.setNewTile();

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
}
