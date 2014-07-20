package info.antworld.archive;

import info.antworld.actors.Actor;
import info.antworld.actors.Ant;
import info.antworld.level.Level;

/**
 * VERSION: PRISTINE
 * TODO: 
 */

public class Termite extends Ant {

	public Termite(Level<Actor> level, int id) {
		super(level, id);
		food = 200;
		r = 0;
		g = 0;
		b = 255;
		size = 10;
		// TODO Auto-generated constructor stub
	}
	
	public boolean act()
	{
		food--;

		if (food <= 0)
		{
			removeSelfFromLevel(); 
			return false;
		}
		
		Ant ant = findAnts(100);
		if (ant != null)
		{
			ant.removeSelfFromLevel();
			food += 50;
		}
		
		this.move(Math.random()*10 - 5, Math.random()*10 - 5);
		return true;
	}
	
	public String getName()
	{
		return "Termite";
	}

	public Ant findAnts(double within)
	{
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			if (getDist(getLevel().getOccupants().get(i)) < within && getLevel().getOccupants().get(i) instanceof Ant)
			{
				return (Ant) getLevel().getOccupants().get(i);
			}
		}
		return null;
	}
	
}
