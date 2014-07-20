package info.antworld.modactors;

import info.antworld.actors.*;
import info.antworld.level.Level;

public class XQueenAnt extends QueenAnt {

	private int gen;
	
	public XQueenAnt(Level<Actor> level, int id) {
		super(level, id);
		// TODO Auto-generated constructor stub
		gen = 0;
	}
	
	public boolean act()
	{
		gen++;
		
		if (gen == 100)
		{
			new SmartAnt(getLevel(),90210).moveTo(x - 50, y - 50);
			new SmartAnt(getLevel(),90210).moveTo(x - 50, y + 50);
			new SmartAnt(getLevel(),90210).moveTo(x + 50, y - 50);
			new SmartAnt(getLevel(),90210).moveTo(x + 50, y + 50);
			new SmartAnt(getLevel(),90210).moveTo(x - 100, y - 100);
			new SmartAnt(getLevel(),90210).moveTo(x - 100, y + 100);
			new SmartAnt(getLevel(),90210).moveTo(x + 100, y - 100);
			new SmartAnt(getLevel(),90210).moveTo(x + 100, y + 100);
			new SmartAnt(getLevel(),90210).moveTo(x - 150, y - 150);
			new SmartAnt(getLevel(),90210).moveTo(x - 150, y + 150);
			new SmartAnt(getLevel(),90210).moveTo(x + 150, y - 150);
			new SmartAnt(getLevel(),90210).moveTo(x + 150, y + 150);
			new SmartAnt(getLevel(),90210).moveTo(x - 200, y - 200);
			new SmartAnt(getLevel(),90210).moveTo(x - 200, y + 200);
			new SmartAnt(getLevel(),90210).moveTo(x + 200, y - 200);
			new SmartAnt(getLevel(),90210).moveTo(x + 200, y + 200);
		}
		
		return true;
	}
	
}
