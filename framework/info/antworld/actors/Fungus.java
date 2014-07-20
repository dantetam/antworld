package info.antworld.actors;

import info.antworld.level.Level;

public class Fungus extends Food {

	private int health;
	private int gen;
	
	private String colony;

	public Fungus(Level<Actor> level, int id) {
		super(level, id);
		health = 500;
		gen = 0;
		// TODO Auto-generated constructor stub
		size = 20;
		r = 205;
		g = 130;
		b = 60;
		servings = 900;
		colony = "neither";
	}

	public boolean act()
	{
		gen++;
		health--;
		if (health <= 0 || servings <= 0) 
		{
			removeSelfFromLevel();
			return false;
		}
		if (Math.random() < 0.001) 
		{
			Fungus temp = new Fungus(getLevel(),34567);
			temp.moveTo(x + (Math.random()*60-30), y + (Math.random()*60-30));
			temp.setColony(getColony());
			temp.setColor(r, g, b);
		}
		return true;
	}

	public Fungus findOldestFungus(double within)
	{
		Fungus temp = this;
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			if (getDist(getLevel().getOccupants().get(i)) < within && getLevel().getOccupants().get(i) instanceof Fungus)
			{
				if (((Fungus) getLevel().getOccupants().get(i)).getGen() > temp.getGen())
				{
					temp = (Fungus)getLevel().getOccupants().get(i);
				}
			}
		}
		return temp;
	}
	
	public String getName()
	{
		return "Fungus";
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		if (health > 500) health = 500;
	}

	public void updateSize()
	{
		
	}

	public int getGen() {
		return gen;
	}
	
	public String getColony() {
		return colony;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

}
