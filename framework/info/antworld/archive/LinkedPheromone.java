package info.antworld.archive;

import info.antworld.actors.Actor;
import info.antworld.actors.Ant;
import info.antworld.actors.Pheromone;
import info.antworld.level.Level;

/**
 * VERSION: PRISTINE
 * TODO: 
 */

public class LinkedPheromone extends Pheromone {

	private Actor link;
	
	public LinkedPheromone(Level<Actor> level, int id, double x, double y, Ant creator, Actor link) {
		super(level, id, x, y, creator);
		this.link = link;
		// TODO Auto-generated constructor stub
	}

	public Actor getLink() {
		return link;
	}

	public void setLink(Actor link) {
		this.link = link;
	}

	public Actor getFinalLink()
	{
		if (link instanceof LinkedPheromone) return ((LinkedPheromone) link).getFinalLink();
		return link;
	}
	
}
