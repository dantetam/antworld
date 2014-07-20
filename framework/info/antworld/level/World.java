package info.antworld.level;

import info.antworld.run.Runner;

import java.util.ArrayList;

/**
 * VERSION: PRISTINE
 * TODO: 
 */

public class World<T> {
	protected ArrayList<Level<T>> levels;
	protected int sizeX, sizeY;
	protected int activeLevel;
	
	private Runner runner;
	
	public World(int sizeX, int sizeY, int numLevels)
	{
		levels = new ArrayList<Level<T>>();
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		for (int i = 0; i < numLevels; i++)
		{
			addLevel();
		}
		
		activeLevel = 0;
	}
	
	/**
	 * Adds one more level to the list of levels.
	 */
	public void addLevel()
	{
		Level<T> newLevel = new Level<T>(this,sizeX,sizeY);
		levels.add(newLevel);
	}
	
	/**
	 * Gets a level at a certain height.
	 * 0 is the top level.
	 * 1 is the level below it.
	 * @param height the desired height to look at
	 * @return the level at the height
	 */
	public Level<T> getLevel(int height)
	{
		return levels.get(height);
	}

	public int getActiveLevel() {
		return activeLevel;
	}

	public void setActiveLevel(int activeLevel) {
		this.activeLevel = activeLevel;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public Runner getRunner() {
		return runner;
	}

	public void setRunner(Runner runner) {
		this.runner = runner;
	}
	
	
}
