package my2048Grid;


import java.util.ArrayList;

public class Grid {
	protected Tile[][] grid;
	public final int size;
	private boolean hasChanged;
	
	protected Grid(int size){
		this.size = size;
		this.grid = new Tile[this.size][this.size];
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
	
	private boolean compareLines(Tile[] line1, Tile[] line2)
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

	private Tile[] copyLine(Tile[] line)
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

	private Tile[] getLine(int line)
	{
		Tile[] newLine = new Tile[this.size];
		for (int i = 0; i < this.size; i++) {
			newLine[i] = this.grid[(this.size - 1) - i][line];
		}
		return newLine;
	}
	
	protected int[] moveLine(Tile[] line)
	{
		Tile[] oldLine = copyLine(line);
		int score = 0, maxValue = 0, value = 0;
		
		for (int i = 1; i < this.size; i++) {
			if(line[i].getValue() > 0){
				value = moveTile(line, i);
				score += value;
				if (value > maxValue) maxValue = value;
				
			}
		}
		if (!this.hasChanged() && this.compareLines(line, oldLine))
			this.setHasChanged(true);
		return new int[]{score, maxValue};
	}

	private int moveTile(Tile[] line, int position)
	{
		int i = position;
		while (i > 0 && line[i - 1].isEmpty()) {
			i--;
		}
		if (i > 0 && line[position].getValue() == line[i - 1].getValue() && !line[i - 1].hasMerged()) {
			line[i - 1].setValue(line[position].getValue() * 2);
			line[position].setValue(0);
			line[i - 1].setHasMerged(true);
			return line[i - 1].getValue();
		}
		if (i != position) {
			line[i].setValue(line[position].getValue());
			line[position].setValue(0);
		}
		return 0;
	}
}
