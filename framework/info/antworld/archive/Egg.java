package info.antworld.archive;

import info.antworld.actors.Actor;
import info.antworld.actors.Ant;
import info.antworld.level.Level;

/**
 * VERSION: PRISTINE
 * TODO: 
 */

public class Egg extends Actor{

	private int gen;
	private String type;
	
	public Egg(Level<Actor> level, int id, int gen, String type) {
		super(level, id);
		r = 100;
		g = 100;
		b = 0;
		size = 4;
		this.gen = gen;
		this.type = type;
		// TODO Auto-generated constructor stub
	}
	
	public boolean act()
	{
		gen--;
		if (gen <= 0)
		{
			new Ant(getLevel(), id).moveTo(x, y);
			removeSelfFromLevel();
			return false;
		}
		return true;
	}
	
	public String getName()
	{
		return "Egg";
	}
	
}
