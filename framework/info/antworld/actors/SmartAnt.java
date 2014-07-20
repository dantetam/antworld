package info.antworld.actors;

import java.util.ArrayList;

import info.antworld.level.Level;

public class SmartAnt extends Ant {

	private ArrayList<Pheromone> visited;
	protected double generalDirection;
	private int gen;
	
	protected String colony;

	public SmartAnt(Level<Actor> level, int id) {
		super(level, id);
		visited = new ArrayList<Pheromone>();
		generalDirection = Math.random()*360;
		food = 350;
		gen = 0;
		r = 50;
		g = 50;
		b = 50;
		size = 5;
		colony = "colony1";
		// TODO Auto-generated constructor stub
	}

	public int getGen() {
		return gen;
	}
	
	public boolean wrap()
	{
		if (super.wrap()) turn();
		return true;
	}

	public void turn()
	{
		generalDirection += 90;
		generalDirection = generalDirection % 360;
	}
	
	public boolean act()
	{	
		gen++;
		//size = food/50;
		
		food--;
		Pheromone nearestPhero = findPheromones(100);
		boolean phero = (nearestPhero != null);

		if (food <= 0)
		{
			removeSelfFromLevel(); 
			return false;
		}

		if (Math.random() < 0.005 && food > 1000) //&& findFungus(200) == null) 
		{
			Fungus temp = new Fungus(getLevel(),34567);
			temp.moveTo(x + (Math.random()*60-30), y + (Math.random()*60-30));
			temp.setColony(getColony());
			temp.setColor(r, g, b);
			food = 200;
		}
		
		Food nearestFood = findFood(50);
		if (nearestFood != null)
		{
			if (food < 1500) //eat only if necessary
			{
				nearestFood.takeAServing();
				food += 8;
				if (nearestFood instanceof Fungus)
					((Fungus) nearestFood).setHealth(((Fungus) nearestFood).getHealth() + 50);
			}

			if (!phero)
			{
				Pheromone temp = new Pheromone(getLevel(), 373392, x, y, this);
				visited.add(temp);
			}
			
			if (nearestFood.getServings() < 10 && nearestPhero != null) nearestPhero.removeSelfFromLevel();
			
			return true;
		}

		if (phero && nearestPhero.getCreator() != this)
		{
			double angle = getDegreesTo(nearestPhero);
			moveInDirection(angle, 4);

			return true;
		}
		moveInDirection(generalDirection, 4);
		generalDirection += Math.random()*10 - 5;
		System.out.println(this.x + " " + this.y);
		return true;
	}
	
	public String getName()
	{
		return "SmartAnt";
	}
	
	public double getGeneralDirection() {
		return generalDirection;
	}
	
	public String getColony() {
		return colony;
	}

	public void setColony(String colony) {
		this.colony = colony;
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



}
