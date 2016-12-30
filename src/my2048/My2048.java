package my2048;

import java.util.ArrayList;

public class My2048 {
	protected static int size = 4;
	
	Tile[][] grid = new Tile[size][size];
	
	public My2048(){
		for(int i = 0; i < size; i++)
		{
			for(int y = 0; y < size; y++)
			{
				this.grid[i][y] = new Tile();
			}
		}
		this.setNewTile();
		this.setNewTile();
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
		for(int i = 0; i < size; i++)
		{
			for(int y = 0; y < size; y++)
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
		for(int i = 0; i < size; i++)
		{
			for(int y = 0; y < size; y++)
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
		Tile[][] newGrid = new Tile[size][size];
		for(int i = 0; i < size; i++)
		{
			newGrid[i] = this.getLine(i);
		}
		this.grid =  newGrid;
	}
	
	protected Tile[] getLine(int line)
	{
		Tile[] newLine = new Tile[size];
		for(int i = 0; i < size; i++)
		{
			newLine[i] = this.grid[(size-1)-i][line];
		}
		return newLine;
	}
	
	protected void moveGrid()
	{
		for(int i = 0; i < size; i++)
		{
			this.grid[i] = moveLine(this.grid[i]);
		}
		this.clearMergedTiles();
		this.setNewTile();
	}
	
	protected Tile[] moveLine(Tile[] line)
	{
		for(int i = 1; i < size; i++)
		{
			line = moveTile(line, i);
		}
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
			return line;
		}
		if(i != position)
		{
			line[i].setValue(line[position].getValue());
			line[position].setValue(0);
		}
		return line;
	}
	
	protected void clearMergedTiles()
	{
		for(int i = 0;i < size;i++)
		{
			for(int y = 0; y < size; y++)
				this.grid[i][y].setHasMerged(false);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		My2048 game = new My2048();
		game.output();
		System.out.println();
		game.moveGrid();
		game.output();
		System.out.println();
		game.moveGrid();
		game.output();
		System.out.println();
		game.rotate(3);
		game.moveGrid();
		game.rotate(1);
		game.output();
	}
}
