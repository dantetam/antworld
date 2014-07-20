package info.antworld.actors;

import info.antworld.level.Level;

public class Food extends Actor {

	protected int servings;
	private int orig;
	
	public Food(Level<Actor> level, int id) {
		super(level, id);
		size = 10;
		r = 255;
		g = 0;
		b = 0;
		servings = 20;
		orig = servings;
	}

	public boolean act()
	{
		if (servings <= 0) 
		{
			removeSelfFromLevel();
			return false;
		}
		return true;
	}

	public void takeAServing()
	{
		updateSize();
		servings--;
	}
	
	public void updateSize()
	{
		size = (10*servings/orig);
	}
	
	public int getServings()
	{
		return servings;
	}
	
	public String getName()
	{
		return "Food";
	}

}
