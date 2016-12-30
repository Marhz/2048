package my2048;

import java.util.ArrayList;

public class My2048 {
	protected int winningScore;
	public final int size;
	protected boolean hasWon;
	protected boolean hasChanged;
	protected Tile[][] grid;
	
	public boolean hasChanged() {
		return hasChanged;
	}
	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}
	public boolean hasWon() {
		return hasWon;
	}
	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}
	
	public My2048(){
		this.size = 4;
		this.grid = new Tile[this.size][this.size];
		this.setStartingGrid();
		this.winningScore = 2048;
		this.hasChanged = false;
		this.hasWon = false;
	}

	public void setNewTile()
	{
		ArrayList<Coordinates> emptyTiles = this.getEmptyTiles();
		int rand = (int)(Math.random() * (emptyTiles.size()));
		int value = (Math.random() < 0.9) ? 2 : 4;
		Coordinates emptyTile = emptyTiles.get(rand);
		this.grid[emptyTile.getX()][emptyTile.getY()].setValue(value);
	}
	protected ArrayList<Coordinates> getEmptyTiles()
	{
		ArrayList<Coordinates> coordinates = new ArrayList<Coordinates>();
		for(int i = 0; i < this.size; i++)
		{
			for(int y = 0; y < this.size; y++)
			{
				if(this.grid[i][y].isEmpty())
				{
					 Coordinates tileCoordinates = new Coordinates(i,y);
					 coordinates.add(tileCoordinates);
				}
			}
		}
		return coordinates;
	}
	public void output()
	{
		for(int i = 0; i < this.size; i++)
		{
			for(int y = 0; y < this.size; y++)
			{
				System.out.print(this.grid[i][y].getValue());
			}
			System.out.println();
		}
	}
	
	public void rotate(int rotations)
	{
		for(int i = 0; i < rotations; i++)
			this.rotateGrid();
	}
	protected void rotateGrid()
	{
		Tile[][] newGrid = new Tile[this.size][this.size];
		for(int i = 0; i < this.size; i++)
		{
			newGrid[i] = this.getLine(i);
		}
		this.grid =  newGrid;
	}
	
	protected Tile[] getLine(int line)
	{
		Tile[] newLine = new Tile[this.size];
		for(int i = 0; i < this.size; i++)
		{
			newLine[i] = this.grid[(this.size-1)-i][line];
		}
		return newLine;
	}
	
	public void moveGrid()
	{
		for(int i = 0; i < this.size; i++)
		{
			moveLine(grid[i]);
		}
		this.clearMergedTiles();
		if(this.hasChanged())
		{
			this.setNewTile();
			this.setHasChanged(false);
		}
	}
	
	protected Tile[] moveLine(Tile[] line)
	{
		Tile[] oldLine = copyLine(line);

		for(int i = 1; i < this.size; i++)
		{
			moveTile(line, i);
		}
		if(!this.hasChanged() && this.compareLines(line, oldLine))
			this.setHasChanged(true);
		return line;
	}

	protected Tile[] moveTile(Tile[] line, int position)
	{
		int i = position;
		while(i > 0 && line[i - 1].isEmpty())
		{
			i--;
		}
		if(i > 0 && line[position].getValue() == line[i-1].getValue() && !line[i-1].hasMerged())
		{
			line[i-1].setValue(line[position].getValue() * 2);
			line[position].setValue(0);
			line[i-1].setHasMerged(true);
			if(line[i-1].getValue() >= this.winningScore)
				this.setHasWon(true);
			return line;
		}
		if(i != position)
		{
			line[i].setValue(line[position].getValue());
			line[position].setValue(0);
		}
		return line;
	}
	protected boolean compareLines(Tile[] line1, Tile[] line2)
	{
		for(int i = 0; i < this.size; i++)
		{
			if(line1[i].getValue() != line2[i].getValue())
				return true;
		}
		return false;
	}
	protected void clearMergedTiles()
	{
		for(int i = 0;i < this.size;i++)
		{
			for(int y = 0; y < this.size; y++)
				this.grid[i][y].setHasMerged(false);
		}
	}
	protected Tile[] copyLine(Tile[] line)
	{
		Tile[] newLine = new Tile[this.size];
		for(int i = 0; i < this.size; i++)
		{
			newLine[i] = new Tile();
			newLine[i].setValue(line[i].getValue());
		}
		return newLine;
	}
	protected Tile[] toSimpleArray()
	{
		Tile[] arrayGrid = new Tile[this.size*this.size];
		for(int i = 0; i < (this.size*this.size); i++)
		{
			arrayGrid[i] = new Tile();
			arrayGrid[i].setValue(this.grid[i/this.size][i%this.size].getValue());
		}
		return arrayGrid;
	}
	public void setStartingGrid()
	{
		for(int i = 0; i < this.size; i++)
		{
			for(int y = 0; y < this.size; y++)
			{
				this.grid[i][y] = new Tile();
			}
		}
		this.setNewTile();
		this.setNewTile();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		My2048 game = new My2048();
		Panel panel = new Panel(game);
	}
}
