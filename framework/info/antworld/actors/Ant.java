package info.antworld.actors;

import info.antworld.level.Level;

public class Ant extends Actor {

	protected int food;

	public Ant(Level<Actor> level, int id) {
		super(level, id);
		food = 250;
		r = 0;
		g = 0;
		b = 0;
		size = 3;
	}

	public boolean act()
	{
		food--;
		Pheromone nearestPhero = findPheromones(200);
		boolean phero = (nearestPhero != null);
		
		if (food <= 0)
		{
			removeSelfFromLevel(); //arraylist trap
			return false;
		}

		Food nearestFood = findFood(60);
		if (nearestFood != null)
		{
			nearestFood.takeAServing();
			food += 5;
			
			if (!phero)
			{
				new Pheromone(getLevel(), 373391, x, y, this);
			}
			return true;
		}
		
		if (phero && nearestPhero.getCreator() != this)
		{
			double angle = getDegreesTo(nearestPhero);
			System.out.println(angle);
			moveInDirection(angle, 2);
			
			return true;
		}
		this.move(Math.random()*4 - 2, Math.random()*4 - 2);
		return true;
	}

	public void removeSelfFromLevel()
	{
		//getLevel().getWorld().getRunner().bleed(x,y);
		super.removeSelfFromLevel();
	}
	
	public String getName()
	{
		return "Ant";
	}
	
	public Pheromone findPheromones(double within)
	{
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			if (getDist(getLevel().getOccupants().get(i)) < within && getLevel().getOccupants().get(i) instanceof Pheromone)
			{
				return (Pheromone) getLevel().getOccupants().get(i);
			}
		}
		return null;
	}

	public int getFood() {
		return food;
	}
	
	public void setFood(int food) {
		this.food = food;
	}
	
	public Food findFood(double within)
	{
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			if (getDist(getLevel().getOccupants().get(i)) < within && getLevel().getOccupants().get(i) instanceof Food)
			{
				return (Food) getLevel().getOccupants().get(i);
			}
		}
		return null;
	}

}
