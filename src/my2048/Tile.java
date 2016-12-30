package my2048;

import java.awt.Color;
public class Tile {
	protected int value;
	protected boolean hasMerged;
	
	public Tile()
	{
		this.value = 0;
		this.hasMerged = false;
	}
	
	public boolean hasMerged()
	{
		return this.hasMerged;
	}

	public void setHasMerged(boolean hasMerged)
	{
		this.hasMerged = hasMerged;
	}

	public int getValue()
	{
		return this.value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public boolean isEmpty()
	{
		return this.value == 0;
	}
	public int fontSize()
	{
		return (this.value > 1000) ? 18 : (this.value > 100) ? 20 : (this.value > 10) ? 22 : 24;
	}
	
	public Color background()
	{
		switch(this.value)
		{
		case 0 :
			
		}
	}
}

