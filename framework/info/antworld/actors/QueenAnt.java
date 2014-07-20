package info.antworld.actors;

import info.antworld.level.Level;

public class QueenAnt extends Ant {

	private int db;
	//private int gen;
	private String colony;

	private double generalDirection;
	
	public QueenAnt(Level<Actor> level, int id) {
		super(level, id);
		db = 0;
		// TODO Auto-generated constructor stub
		food = 9001;
		r = 255;
		g = 0;
		b = 255;
		size = 15;
		//gen = 9001;
		generalDirection = Math.random()*360;
	}

	public boolean act()
	{
		food--;

		if (food <= 0)
		{
			removeSelfFromLevel(); 
			return false;
		}
		
		db++;
		if (db >= 30) 
		{
			db = 0;
			SmartAnt temp = new SmartAnt(getLevel(), 9001);
			temp.moveTo(x, y);
			temp.setColony(getColony());
		}
		
		moveInDirection(generalDirection, 2);
		generalDirection += Math.random()*70 - 35;
		
		/*
		if (x > 2*getLevel().getSizeX() || y > 2*getLevel().getSizeY() || x < -2*getLevel().getSizeX() || y < -2*getLevel().getSizeY())
		{
			removeSelfFromLevel();
			return false;
		}
		*/
		
		return true;
	}
	
	public String getName()
	{
		return "QueenAnt";
	}
	
	public void moveTo(double x, double y)
	{
		super.moveTo(x, y);
		//wrap();
	}

	public void move(double x, double y)
	{
		super.move(x, y);
		//wrap();
	}
	
	public String getColony() {
		return colony;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}
	
}
