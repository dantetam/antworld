package info.antworld.actors;

import info.antworld.level.*;

public abstract class Actor {
	protected double x,y;
	protected int r,g,b;
	private ActorWorld world;
	private Level level;
	protected int size;
	protected int id;

	private boolean highlighted;
	
	public Actor(Level<Actor> level, int id)
	{
		this.setLevel(level);
		this.id = id;
		world = (ActorWorld) level.getWorld();
		level.addOccupant(this);
		x = Math.random()*world.getSizeX();
		y = Math.random()*world.getSizeY();
		highlighted = false;
	}

	public boolean act()
	{
		return true;
	}

	public double getDist(Actor other)
	{
		return Math.sqrt( Math.pow(other.getX() - x,2) + Math.pow(other.getY() - y,2) );
	}

	public double getDegreesTo(Actor other)
	{
		double degrees = Math.toDegrees(Math.atan( (other.getY()-y)/(other.getX()-x) ));
		degrees = Math.floor(degrees);

		//adjust for quadrants
		if (other.getY() > y)
		{
			if (other.getX() < x)
				return degrees + 90;
			return degrees;
		}
		if (other.getX() < x)
			return degrees + 180;
		return degrees + 270;
	}
	
	public double getDegreesTo(double otherX, double otherY)
	{
		double degrees = Math.toDegrees(Math.atan( (otherY-y)/(otherX-x) ));
		degrees = Math.floor(degrees);

		//adjust for quadrants
		if (otherY > y)
		{
			if (otherX < x)
				return degrees + 90;
			return degrees;
		}
		if (otherX < x)
			return degrees + 180;
		return degrees + 270;
	}

	public void moveInDirection(double angle, double d)
	{
		if (angle < 30) move(d,0);
		else if (angle < 45) move(d,d/2);
		else if (angle < 60) move(d,d);
		else if (angle < 90) move(d/2,d);
		else if (angle < 120) move(0,d);
		else if (angle < 135) move(-d/2,d);
		else if (angle < 150) move(-d,d);
		else if (angle < 180) move(-d,d/2);
		else if (angle < 210) move(-d,0);
		else if (angle < 225) move(-d,-d/2);
		else if (angle < 240) move(-d,-d);
		else if (angle < 270) move(-d/2,-d);
		else if (angle < 300) move(0,-d);
		else if (angle < 315) move(d/2,-d);
		else if (angle < 330) move(d,-d);
		else move(d,-d/2);
	}

	public void removeSelfFromLevel()
	{
		for (int i = 0; i < level.getOccupants().size(); i++)
		{
			if (level.getOccupants().get(i) == this)
			{
				level.getOccupants().remove(i);
				level = null;
				world = null;
				id = -1;
				size = 0;
				return;
			}
		}
	}

	public void moveTo(double x, double y)
	{
		this.x = x;
		this.y = y;
		//wrap();
	}

	public void move(double x, double y)
	{
		this.x += x;
		this.y += y;
		wrap();
	}

	public boolean wrap()
	{
		//System.out.println(level.getSizeX() + "," + level.getSizeY());
		if (x <= 0) 
		{
			x = 0;
			return true;
		}
		if (x >= level.getSizeX()) 
		{
			x = level.getSizeX();
			return true;
		}
		if (y <= 0) 
		{
			y = 0;
			return true;
		}
		if (y >= level.getSizeY()) 
		{
			y = level.getSizeY();
			return true;
		}
		return false;
	}
	
	public boolean equalColor(Actor a)
	{
		return r==a.getR() && g==a.getG() && b==a.getB();
	}

	public void setColor(int r, int g, int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public String getName()
	{
		return "Actor";
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public Level<Actor> getLevel() {
		return level;
	}

	public void setLevel(Level<Actor> level) {
		this.level = level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void toggleHighlighted() {
		highlighted = !highlighted;
	}

}
