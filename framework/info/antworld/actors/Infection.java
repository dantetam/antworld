package info.antworld.actors;

import info.antworld.level.Level;

public class Infection extends Actor {

	private int health;
	private int latency;
	
	public Infection(Level<Actor> level, int id) {
		super(level, id);
		// TODO Auto-generated constructor stub
		size = 20;
		r = 0;
		g = 0;
		b = 200;
		health = 500;
		latency = 2000;
	}
	
	public boolean act()
	{
		health--;
		latency--;
		
		if (health <= 0)
		{
			removeSelfFromLevel(true);
			return false;
		}
		
		if (latency <= 0) return true;
		Fungus fungus = findFungus(100);
		if (fungus != null)
		{
			if (Math.random() < 0.005)
			{
				replaceFungus(fungus);
				health += 50;
			}
		}
		return true;
	}
	
	public String getName()
	{
		return "Infection";
	}
	
	public void removeSelfFromLevel(boolean diff)
	{
		if (findNumInfection(150) > 10 & Math.random() < 0.25)
		{
			new Spore(getLevel(),2223).moveTo(x, y);
		}
		super.removeSelfFromLevel();
	}
	
	public void replaceFungus(Fungus fungus)
	{
		new Infection(getLevel(),9999404).moveTo(fungus.getX(), fungus.getY());
		fungus.removeSelfFromLevel();
	}
	
	public Fungus findFungus(double within)
	{
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			if (getDist(getLevel().getOccupants().get(i)) < within && getLevel().getOccupants().get(i) instanceof Fungus)
			{
				return (Fungus) getLevel().getOccupants().get(i);
			}
		}
		return null;
	}
	
	public int findNumInfection(double within)
	{
		int temp = 0;
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			if (getDist(getLevel().getOccupants().get(i)) < within && getLevel().getOccupants().get(i) instanceof Infection)
			{
				temp++;
			}
		}
		return temp;
	}
	
}
