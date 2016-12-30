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
		return (this.value > 1000) ? 20 : (this.value > 100) ? 21 : (this.value > 10) ? 22 : 23;
	}
	
	public Color background()
	{
		switch(this.value)
		{
		case 2 :
			return Color.decode("#EEE4DA");
		case 4 :
			return Color.decode("#EDE0C8");
		case 8 :
			return Color.decode("#F2B179");
		case 16 :
			return Color.decode("#F59563");
		case 32 :
			return Color.decode("#F67C5F");
		case 64 :
			return Color.decode("#F65E3B");
		case 128 :
			return Color.decode("#EDCF72");
		case 246 :
			return Color.decode("#EDCC61");
		case 512 :
			return Color.decode("#EDC850");
		case 1024 :
			return Color.decode("#EDC53F");
		case 2048 :
			return Color.decode("#EDC22E");
		}
		return Color.decode("#EDC22E");
			
	}
}

