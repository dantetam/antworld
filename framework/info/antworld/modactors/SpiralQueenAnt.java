package info.antworld.modactors;

import info.antworld.actors.*;
import info.antworld.level.Level;

public class SpiralQueenAnt extends QueenAnt {

	private int gen;
	private int radius;
	
	public SpiralQueenAnt(Level<Actor> level, int id) {
		super(level, id);
		// TODO Auto-generated constructor stub
		radius = 50;
	}
	
	public boolean act()
	{
		gen++;
		gen = gen % 360;
		
		if (gen % 15 == 0)
		{
			double rgen = gen * Math.PI / 180;
			double xOffset = radius * Math.cos(rgen); 
			double yOffset = radius * Math.sin(rgen);
			
			if (gen > 90 && gen <= 180)
			{
				xOffset *= -1;
			}
			else if (gen > 180 && gen <= 270)
			{
				xOffset *= -1;
				yOffset *= -1;
			}
			else if (gen > 270)
			{
				yOffset *= -1;
			}
			
			System.out.println(xOffset + "" + yOffset);
			
			new SmartAnt(getLevel(),90211).moveTo(x + xOffset, y + yOffset);
		}
		
		return true;
	}

}
