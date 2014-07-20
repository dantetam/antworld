package info.antworld.actors;

import java.awt.Rectangle;

import info.antworld.level.Level;

public class Obstacle extends Actor {

	private double sizeX, sizeY;
	
	public Obstacle(Level<Actor> level, int id, double sizeX, double sizeY) {
		super(level, id);
		// TODO Auto-generated constructor stub
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void checkWithin(Actor actor)
	{
		Rectangle a = new Rectangle((int)actor.getX(),(int)actor.getY(),actor.getSize(),actor.getSize());
		Rectangle b = new Rectangle((int)x,(int)y,(int)sizeX,(int)sizeY);
		if (a.intersects(b) && actor instanceof SmartAnt)
		{
			((SmartAnt)actor).turn();
		}
	}

	public boolean act()
	{
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			checkWithin(getLevel().getOccupants().get(i));
		}
		return true;
	}
	
	public double getSizeX() {
		return sizeX;
	}

	public double getSizeY() {
		return sizeY;
	}

	
}
