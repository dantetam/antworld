package info.antworld.actors;

import info.antworld.level.Level;

public class Pheromone extends Actor {

	private int gen;
	private Ant creator;
	
	public Pheromone(Level<Actor> level, int id, double x, double y, Ant creator) {
		super(level, id);
		this.x = x;
		this.y = y;
		r = 0;
		g = 255;
		b = 0;
		size = 2;
		gen = 100;
		this.creator = creator;
	}

	public Pheromone(Level activeLevel, int i) {
		super(activeLevel, i);
	}

	public boolean act()
	{
		gen--;
		if (gen <= 0) 
		{
			removeSelfFromLevel();
			return false;
		}
		return true; 
	}

	public Ant getCreator() {
		return creator;
	}
	
	public String getName()
	{
		return "Pheromone";
	}

}
