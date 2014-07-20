package info.antworld.archive;

import info.antworld.actors.Actor;
import info.antworld.level.Level;

public class Poison extends Actor {

	public Poison(Level<Actor> level, int id) {
		super(level, id);
		// TODO Auto-generated constructor stub
		r = 0;
		g = 100;
		b = 0;
		size = 4;
	}
	
	public boolean act()
	{
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			if (getDist(getLevel().getOccupants().get(i)) < 30)
				getLevel().getOccupants().get(i).removeSelfFromLevel();
		}
		return true;
	}

}
