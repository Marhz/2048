package my2048Grid;


import java.util.ArrayList;

public class Grid {
	protected Tile[][] grid;
	public final int size;
	protected boolean hasChanged;
	
	protected Grid(int size){
		this.size = size;
	}
	
	protected boolean hasChanged()
	{
		return hasChanged;
	}

	protected void setHasChanged(boolean hasChanged)
	{
		this.hasChanged = hasChanged;
	}
	
	public Tile[] toSimpleArray()
	{
		Tile[] arrayGrid = new Tile[(size * size)];
		for (int i = 0; i < (size * size); i++) {
			arrayGrid[i] = new Tile(grid[(i / size)][(i % size)].getValue());
		}
		return arrayGrid;
	}
	
	public void rotate(int rotations)
	{
		for (int i = 0; i < rotations; i++)
			this.rotateGrid();
	}
	
	protected ArrayList<Coordinates> getEmptyTiles()
	{
		ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
		for (int i = 0; i < this.size; i++) {
			for (int y = 0; y < this.size; y++) {
				if (this.grid[i][y].isEmpty()) {
					coordinates.add(new Coordinates(i, y));
				}
			}
		}
		return coordinates;
	}
	
	protected void setNewTile()
	{
		ArrayList<Coordinates> emptyTiles = this.getEmptyTiles();
		int rand = (int) (Math.random() * (emptyTiles.size()));
		int value = (Math.random() < 0.9) ? 2 : 4;
		Coordinates emptyTile = emptyTiles.get(rand);
		this.grid[emptyTile.getX()][emptyTile.getY()].setValue(value);
	}
	
	protected void setStartingGrid()
	{
		for (int i = 0; i < this.size; i++) {
			for (int y = 0; y < this.size; y++) {
				this.grid[i][y] = new Tile();
			}
		}
		this.setNewTile();
		this.setNewTile();
	}
	
	protected boolean compareLines(Tile[] line1, Tile[] line2)
	{
		for (int i = 0; i < this.size; i++) {
			if (line1[i].getValue() != line2[i].getValue())
				return true;
		}
		return false;
	}

	protected void clearMergedTiles()
	{
		for (int i = 0; i < size; i++) {
			for (int y = 0; y < size; y++)
				grid[i][y].setHasMerged(false);
		}
	}

	protected Tile[] copyLine(Tile[] line)
	{
		Tile[] newLine = new Tile[size];
		for (int i = 0; i < size; i++) {
			newLine[i] = new Tile(line[i].getValue());
		}
		return newLine;
	}
	
	protected void rotateGrid()
	{
		Tile[][] newGrid = new Tile[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			newGrid[i] = this.getLine(i);
		}
		this.grid = newGrid;
	}

	protected Tile[] getLine(int line)
	{
		Tile[] newLine = new Tile[this.size];
		for (int i = 0; i < this.size; i++) {
			newLine[i] = this.grid[(this.size - 1) - i][line];
		}
		return newLine;
	}
}
