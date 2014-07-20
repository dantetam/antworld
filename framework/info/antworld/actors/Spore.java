package info.antworld.actors;

import info.antworld.level.Level;

public class Spore extends Infection {

	private double generalDirection;

	public Spore(Level<Actor> level, int id) {
		super(level, id);
		// TODO Auto-generated constructor stub
		size = 5;
		generalDirection = Math.random()*360;
	}

	public boolean act()
	{		
		if (wrap())
		{
			removeSelfFromLevel();
			return false;
		}
		Fungus fungus = findFungus(40);
		if (fungus != null && Math.random() < 0.05)
		{
			replaceFungus(fungus);
			removeSelfFromLevel();
			return false;
		}
		moveInDirection(generalDirection, 6);
		return true;
	}

	public String getName()
	{
		return "Spore";
	}

}
