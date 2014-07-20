package info.antworld.level;

import java.util.ArrayList;
//import info.antworld.util.*;

/**
 * VERSION: PRISTINE
 * TODO: 
 */

public class Level<T> {
	private World<T> world;
	private ArrayList<T> occupants;
	private int sizeX, sizeY;
	
	public boolean permitsWar;

	public Level(World<T> world, int sizeX, int sizeY)
	{
		this.world = world;
		occupants = new ArrayList<T>();
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		//System.out.println(sizeX + "," + sizeY);
		permitsWar = true;
	}

	public void togglePermitsWar() {
		permitsWar = !permitsWar;
	}
	
	public World<T> getWorld() {
		return world;
	}
	public void setWorld(World<T> world) {
		this.world = world;
	} 
	public void addOccupant(T occupant)
	{
		occupants.add(occupant);
	}
	
	public ArrayList<T> getOccupants()
	{
		return occupants;
	}
	
	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	
}
