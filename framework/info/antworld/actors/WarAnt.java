package info.antworld.actors;

import info.antworld.level.Level;

public class WarAnt extends SmartAnt {

	private double hp;
	private double orig;
	private int warLevel;

	private int allies;

	public WarAnt(Level level, int id) {
		super(level, id);
		// TODO Auto-generated constructor stub
		hp = 3000;
		orig = hp;
		size = 20;
		warLevel = 0;
		allies = 0;
	}

	public boolean act()
	{
		hp += warLevel;

		if (hp < 0)
		{
			removeSelfFromLevel();
			return false;
		}
		if (hp > orig) hp = orig;

		if (getLevel().permitsWar) //no eating during battles
		{
			allies = 1;
			WarAnt enemy = findWarAnts(50);
			if (enemy != null)
			{	
				enemy.incrementHealth(-getLevel().getWorld().getRunner().getDamage(warLevel) * allies);
				if (enemy.getHP() <= 0) warLevel++;
				return true;
				//r = 200;
			}
		}

		Fungus takeover = findFungus(100);
		if (takeover != null)
		{
			try
			{
				if (!takeover.getColony().equals(getColony()))
				{
					takeover.setColony(getColony());
					takeover.setColor(r, g, b);
				}
			}
			catch (NullPointerException e)
			{
				
			}
		}

		//if (Math.random() < 0.005) r = 50;
		return super.act(); //function like a civi
	}

	public void attack(double x, double y)
	{
		double angle = getDegreesTo(x+(Math.random()*20-10),y+(Math.random()*20-10));
		generalDirection = angle;
		moveInDirection(angle, 6);
	}

	public boolean equalColor(Actor a)
	{
		if (r == 200 || a.getR() == 200) return true;
		return super.equalColor(a);
	}

	public WarAnt findWarAnts(double within)
	{
		for (int i = 0; i < getLevel().getOccupants().size(); i++)
		{
			if (getDist(getLevel().getOccupants().get(i)) < within && getLevel().getOccupants().get(i) instanceof WarAnt)
			{
				if (!getLevel().getOccupants().get(i).equals(this) && !sameColony(getLevel().getOccupants().get(i)))
					return (WarAnt) getLevel().getOccupants().get(i);
				else if (!getLevel().getOccupants().get(i).equals(this))
					allies++;
			}
		}
		return null;
	}

	public void incrementHealth(int value)
	{
		hp += value;
		//System.out.println("hp: " + hp);
	}

	public boolean sameColony(Actor a)
	{
		return colony.equals(((WarAnt) a).getColony());
	}

	public String getName()
	{
		return "WarAnt";
	}

	public double getHP() {
		return hp;
	}

	public double getOrig() {
		return orig;
	}

	public int getWarLevel() {
		return warLevel;
	}

}
