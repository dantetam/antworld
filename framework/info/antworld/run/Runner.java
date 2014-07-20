package info.antworld.run;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.*; //glory to processing

import info.antworld.actors.*;
import info.antworld.level.*;
import info.antworld.modactors.*;

public class Runner extends PApplet {

	private final static int width = 600;
	private final static int height = 600;
	//public int id = 0;

	private int ants = 10;
	private int food = 50;

	public Level activeLevel;

	ActorWorld newWorld = new ActorWorld(width,height,1);

	public HashMap<String,Boolean> config = new HashMap<String,Boolean>();
	//public HashMap<String,Integer> stats = new HashMap<String,Integer>();
	public String mode;
	private String tiltMode;

	private ArrayList<String> console;
	private ArrayList<PImageContainer> images;
	private Compiler compiler;

	private int[] graphValues;
	private int[] infectionGraphValues;
	private long graphDebounce;

	private int obstacleX;
	private int obstacleY;

	public class PImageContainer {
		private PImage image;
		private float x,y;
		private int framesLeft;
		public PImageContainer(PImage image, int framesLeft, float x, float y) 
		{
			this.image = image;
			this.framesLeft = framesLeft;
			this.x = x;
			this.y = y;
		}
		public void show() 
		{
			image(image,x,y);
			framesLeft--;
		}
		public int getFramesLeft() {return framesLeft;}
	}

	private double[] levelMinDamage = new double[100];
	private double[] levelMaxDamage = new double[100];

	private boolean gPressed;

	private PImage gun, p1, p2, p3, p4;	

	public void setup()
	{
		size(width,height);
		frameRate(50);
		cursor(CROSS);
		newWorld.setRunner(this);
		new QueenAnt(newWorld.getLevel(newWorld.getActiveLevel()), 1008).moveTo(width/2,height/2);
		//new QueenAnt(newWorld.getLevel(newWorld.getActiveLevel()), 1008).moveTo(400,400);
		//new QueenAnt(newWorld.getLevel(newWorld.getActiveLevel()), 1008).moveTo(600,600);
		activeLevel = newWorld.getLevel(newWorld.getActiveLevel());

		for (int i = 0; i < ants; i++)
		{
			//new Ant(newWorld.getLevel(newWorld.getActiveLevel()), i);
		}
		for (int i = 0; i < ants; i++)
		{
			//new SmartAnt(newWorld.getLevel(newWorld.getActiveLevel()), i);
		}
		/*
		for (int i = ants; i < ants + food; i++)
		{
			new Food(newWorld.getLevel(newWorld.getActiveLevel()), i);
		}*/

		new Obstacle(activeLevel,420420,100,100);

		gun = loadImage("oth4.gif");
		p1 = loadImage("1.jpg");
		p2 = loadImage("2.jpg");
		p3 = loadImage("3.jpg");
		p4 = loadImage("4.jpg");

		console = new ArrayList<String>();
		images = new ArrayList<PImageContainer>();

		compiler = new Compiler(this);

		obstacleX = -1;
		obstacleY = -1;

		for (int i = 0; i < levelMinDamage.length; i++)
		{
			levelMinDamage[i] = i*5 + 50;
			levelMaxDamage[i] = i*10 + 50;
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

		mode = "";
		tiltMode = "Tilt Level";

		graphValues = new int[20];
		infectionGraphValues = new int[20];
		graphDebounce = System.currentTimeMillis();

		gPressed = false;

		config.put("Toggle Generate Ants", false);
		config.put("Toggle Generate SmartAnts", false);
		config.put("Toggle Generate QueenAnts", false);
		config.put("Toggle Generate Food", false);
		config.put("Toggle Generate Pheromones", false);
		config.put("Toggle Generate Termites", false);
		config.put("Toggle ESM", false);
		config.put("Toggle Networked Agriculture", true);
		config.put("Toggle War Ant Color", false); 
		config.put("Toggle Starvation", false);
		//false creates black ants with green bars, true creates white ants with red bars 

		/*		stats.put("Ant", 0);
		stats.put("Food", 0);
		stats.put("Fungus", 0);
		stats.put("Infection", 0);
		stats.put("Pheromone", 0);
		stats.put("QueenAnt", 0);
		stats.put("SmartAnt", 0);
		stats.put("WarAnt", 0);*/

		//frameRate(20);
	}

	public boolean acting = true;
	public void draw()
	{
		background(0);
		//showLevel(newWorld);
		if (activeLevel.getOccupants().size() > 0)
			step(newWorld);
		showStats();
		if (Math.random() < 0.05 && config.get("Toggle Generate Food"))
			new Food(activeLevel, 887877);
		else if (Math.random() < 0.05 && config.get("Toggle Generate Ants"))
			new Ant(activeLevel, 887877);
		else if (Math.random() < 0.05 && config.get("Toggle Generate SmartAnts"))
			new SmartAnt(activeLevel, 887877);
		else if (Math.random() < 0.05 && config.get("Toggle Generate QueenAnts"))
			new QueenAnt(activeLevel, 887877);
		else if (Math.random() < 0.05 && config.get("Toggle Generate Pheromones"))
			new Pheromone(activeLevel, 887877);
		// if (Math.random() > 0.998) new QueenAnt(newWorld.getLevel(newWorld.getActiveLevel()), 887877);
		tilt();
		showConsole();
		if (gPressed) showGraph();
		for (int i = images.size() - 1; i >= 0; i--) 
		{
			images.get(i).show();
			if (images.get(i).getFramesLeft() <= 0) {images.remove(i);}
		}
	}

	public void keyPressed()
	{
		if (key == 'g' || key == 'G') gPressed = true;
	}

	public void keyReleased()
	{
		if (key == 'g' || key == 'G') gPressed = false;
	}

	public void createAndShowGUI()
	{
		AntWorldGUI gui = new AntWorldGUI(this);
		//AntScriptCMD cmd = new AntScriptCMD(this);
	}

	public void bleed(double x, double y)
	{
		if (Math.random() < 0.25) images.add(new PImageContainer(p1,50,(float)x,(float)y));
		else if (Math.random() < 0.5) images.add(new PImageContainer(p2,50,(float)x,(float)y));
		else if (Math.random() < 0.75) images.add(new PImageContainer(p3,50,(float)x,(float)y));
		else images.add(new PImageContainer(p4,50,(float)x,(float)y));
	}

	public void doCommand(String command)
	{
		if (command.equals("")) return;

		System.out.println(command);

		if (command.contains("Mode"))
		{
			mode = command;
			//print(mode);
		}
		else if (command.contains("Toggle"))
		{
			config.put(command, !config.get(command)); 
		}
		else if (command.contains("Tilt"))
		{
			tiltMode = command;
		}

		if (command.equals("Clear All"))
		{
			activeLevel.getOccupants().clear();
		}
		else if (command.equals("Clear Food"))
		{
			for (int i = activeLevel.getOccupants().size() - 1; i >= 0; i--)
			{
				if (activeLevel.getOccupants().get(i) instanceof Food)
					activeLevel.getOccupants().remove(i);
			}
		}
		else if (command.equals("Clear Ants"))
		{
			for (int i = activeLevel.getOccupants().size() - 1; i >= 0; i--)
			{
				if (activeLevel.getOccupants().get(i) instanceof Ant)
					activeLevel.getOccupants().remove(i);
			}
		}
		else if (command.equals("Clear Queens"))
		{
			for (int i = activeLevel.getOccupants().size() - 1; i >= 0; i--)
			{
				if (activeLevel.getOccupants().get(i) instanceof QueenAnt)
					activeLevel.getOccupants().remove(i);
			}
		}
		else if (command.equals("Clear Fungus"))
		{
			for (int i = activeLevel.getOccupants().size() - 1; i >= 0; i--)
			{
				if (activeLevel.getOccupants().get(i) instanceof Fungus)
					activeLevel.getOccupants().remove(i);
			}
		}
		else if (command.equals("Toggle ESM") && !config.get("Toggle ESM"))
		{
			activeLevel.getOccupants().clear();
		}
		else if (command.equals("Pause"))
		{
			acting = !acting;
		}
		else if (command.equals("Very Slow"))
		{
			frameRate(5);
		}
		else if (command.equals("Slow"))
		{
			frameRate(20);
		}
		else if (command.equals("Medium"))
		{
			frameRate(50);
		}
		else if (command.equals("Fast"))
		{
			frameRate(100);
		}
		else if (command.equals("Spawn Obstacle Mode"))
		{
			obstacleX = -1;
			obstacleY = -1;
		}
	}

	public void tilt()
	{
		if (tiltMode.equals("Tilt Left")) 
		{
			for (int i = activeLevel.getOccupants().size() - 1; i >= 0; i--)
			{
				((Actor) activeLevel.getOccupants().get(i)).move(-2,0);
			}
		}
		else if (tiltMode.equals("Tilt Right")) 
		{
			for (int i = activeLevel.getOccupants().size() - 1; i >= 0; i--)
			{
				((Actor) activeLevel.getOccupants().get(i)).move(2,0);
			}
		}
		else if (tiltMode.equals("Tilt Forward")) 
		{
			for (int i = activeLevel.getOccupants().size() - 1; i >= 0; i--)
			{
				((Actor) activeLevel.getOccupants().get(i)).move(0,-2);
			}
		}
		else if (tiltMode.equals("Tilt Backward")) 
		{
			for (int i = activeLevel.getOccupants().size() - 1; i >= 0; i--)
			{
				((Actor) activeLevel.getOccupants().get(i)).move(0,2);
			}
		}
	}

	private void showStats()
	{
		int Ant = 0;
		int Food = 0;
		double Fungus = 0;
		double Infection = 0;
		int Pheromone = 0;
		int QueenAnt = 0;
		int SmartAnt = 0;
		int WarAnt = 0;
		double C1Fungi = 0;
		double C2Fungi = 0;
		double C1WarAnts = 0;
		double C2WarAnts = 0;

		for (int i = 0; i < activeLevel.getOccupants().size(); i++)
		{
			String cn = activeLevel.getOccupants().get(i).getClass().getSimpleName();
			if (cn.equals("Ant")) Ant++;
			else if (cn.equals("Food")) Food++;
			else if (cn.equals("Fungus")) 
			{
				Fungus++;
				try
				{
					if (((Fungus) activeLevel.getOccupants().get(i)).getColony().equals("colony1")) C1Fungi++;
					else if (((Fungus) activeLevel.getOccupants().get(i)).getColony().equals("colony2")) C2Fungi++;
				} 
				catch (NullPointerException e) 
				{
					println(activeLevel.getOccupants().get(i));
				}
			}
			else if (cn.equals("Infection")) Infection++;
			else if (cn.equals("Pheromone")) Pheromone++;
			else if (cn.equals("QueenAnt")) QueenAnt++;
			else if (cn.equals("SmartAnt")) SmartAnt++;
			else if (cn.equals("WarAnt")) 
			{
				WarAnt++;
				if (((WarAnt) activeLevel.getOccupants().get(i)).getColony().equals("colony1")) C1WarAnts++;
				else if (((WarAnt) activeLevel.getOccupants().get(i)).getColony().equals("colony2")) C2WarAnts++;
			}
		}

		fill(255);

		text("Ants: " + Ant,width*9/10,0);
		text("Food: " + Food,width*9/10,20);
		text("Fungi: " + Fungus,width*9/10,40);
		text("Infection: " + Infection,width*9/10,60);
		text("Pheromones: " + Pheromone,width*9/10,80);
		text("Queen Ants: " + QueenAnt,width*9/10,100);
		text("Smart Ants: " + SmartAnt,width*9/10,120);
		text("War Ants: " + WarAnt,width*9/10,140);

		if (Fungus == 0)
		{
			text("No fungi",250,height-20);
		}
		else
		{
			//text("C1Fungi(green bars, gray): " + (Math.floor(C1Fungi/Fungus*100*100)/100) + "%, C2Fungi(red bars, white): " + (Math.floor(C2Fungi/Fungus*100*100)/100) + "%",250,height-20);
			try
			{
				text((float)(Infection/(Infection+Fungus))*100 + "% of fungi are infected.",250,height-20);
			}
			catch (ArithmeticException e) {}
		}

		if (System.currentTimeMillis() - graphDebounce > 200)
		{
			graphDebounce = System.currentTimeMillis();
			int numAnts = Ant + SmartAnt + QueenAnt + WarAnt;
			for (int i = 0; i < graphValues.length - 1; i++)
			{
				graphValues[i] = graphValues[i + 1];
			}
			graphValues[graphValues.length - 1] = numAnts;

			for (int i = 0; i < infectionGraphValues.length - 1; i++)
			{
				infectionGraphValues[i] = infectionGraphValues[i + 1];
			}
			if (Infection > 0 && Fungus == 0)
				infectionGraphValues[infectionGraphValues.length - 1] = 100;
			else if (Infection == 0 && Fungus > 0)
				infectionGraphValues[infectionGraphValues.length - 1] = 0;
			else 
				infectionGraphValues[infectionGraphValues.length - 1] = (int) ((Infection/(Infection+Fungus))*100);
		}
	}

	private void showConsole()
	{
		String lastCommand = "";
		int posY = 300;
		int rep = 0;

		int tempSize;
		if (console.size() > 20) 
			tempSize = 20;
		else
			tempSize = console.size();

		for (int i = 0; i < tempSize; i++)
		{
			if (!console.get(i).equals(lastCommand))
			{
				if (rep == 0)
				{
					fill(255,255,255);
					text(lastCommand,(float)(width*0.9),(float)posY);
					//println("a");
					rep = 0;
				}
				else if (rep > 0)
				{
					fill(255,255,255);
					text(lastCommand + " x" + (rep+1),(float)(width*0.9),(float)posY);
					//println("b");
					rep = 0;
				}
				lastCommand = console.get(i);
				posY += 15;
			}
			else
			{
				rep++;
			}
		}
	}

	public void showGraph()
	{
		stroke(0,0,255);
		for (int i = 0; i < infectionGraphValues.length - 1; i++)
		{
			point(width/2 + 10*i + 400, height*9/10 - infectionGraphValues[i]*2);
			line(width/2 + 10*i + 400, height*9/10 - infectionGraphValues[i]*2, width/2 + 10*(i+1) + 400, height*9/10 - infectionGraphValues[i+1]*2);
		}
		stroke(255,255,255);
		for (int i = 0; i < graphValues.length - 1; i++)
		{
			point(width/2 + 10*i + 400, height*9/10 - graphValues[i]*2);
			line(width/2 + 10*i + 400, height*9/10 - graphValues[i]*2, width/2 + 10*(i+1) + 400, height*9/10 - graphValues[i+1]*2);
		}

		text("0 _", width/2 + 370, height*9/10);
		text("20 _", width/2 + 370, height*9/10 - 40);
		text("40 _", width/2 + 370, height*9/10 - 80);
		text("60 _", width/2 + 370, height*9/10 - 120);
		text("80 _", width/2 + 370, height*9/10 - 160);
		text("100 _", width/2 + 370, height*9/10 - 200);
	}

	/*	private void showStats() {
		int posY = 0;
		for (Map.Entry<String, Integer> entry : stats.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			fill(255,255,255);
			text(key + ": " + value, width*9/10, posY);
			posY += 20;
		}
	}

	private void countAndUpdateStats() {
		for (int i = 0; i < activeLevel.getOccupants().size(); i++)
		{
			Actor countThis = (Actor)activeLevel.getOccupants().get(i);
			if (!countThis.getName().equals("Actor"))
			{
				println(countThis.getName());
				if (stats.get(countThis.getName()) != null)
				{
					try
					{
					int orig = stats.get(countThis.getName());
					stats.put(countThis.getName(), orig + 1);
					} catch (NullPointerException e)
					{
						println("This item is erroring: " + countThis.getName());
					}
				}
			}
		}
		for (String key : stats.keySet())
		{
			stats.put(key, 0);
		}
		//println("counted stats");
	}*/

	public void mousePressed()
	{
		messageToConsole("Mouse pressed");
		if (mode.equals("Spawn Food Mode"))
			new Food(activeLevel, 1008).moveTo(mouseX, mouseY);
		else if (mode.equals("Spawn Smart Ant Mode"))
			new SmartAnt(activeLevel, 1008).moveTo(mouseX, mouseY);
		else if (mode.equals("Spawn Queen Ant Mode"))
			new QueenAnt(activeLevel, 1008).moveTo(mouseX, mouseY);
		else if (mode.equals("Spawn Infection Mode"))
			new Infection(activeLevel, 1008).moveTo(mouseX, mouseY);
		else if (mode.equals("Spawn War Ant Mode"))
		{  
			WarAnt temp = new WarAnt(activeLevel, 1008);
			temp.moveTo(mouseX, mouseY);
			if (config.get("Toggle War Ant Color"))
			{
				temp.setColor(255, 255, 255);
				temp.setColony("colony1");
			} 
			else
			{
				temp.setColor(50, 50, 50);
				temp.setColony("colony2");
			}
		}
		else if (mode.equals("Spawn Fungus Mode"))
			new Fungus(activeLevel, 1008).moveTo(mouseX, mouseY);
		else if (mode.equals("Spawn Obstacle Mode"))
		{
			if (obstacleX == -1 && obstacleY == -1)
			{
				obstacleX = mouseX;
				obstacleY = mouseY;
			}
			else
			{
				new Obstacle(activeLevel, 444447, Math.abs(obstacleX - mouseX), Math.abs(obstacleY - mouseY)).moveTo(Math.min(obstacleX, mouseX),Math.min(obstacleY, mouseY));
				obstacleX = -1;
				obstacleY = -1;
			}
		}
		else if (mode.equals("Highlight Mode"))
		{
			for (int i = 0; i < activeLevel.getOccupants().size(); i++)
			{
				float theDist = dist((float)mouseX,(float)mouseY,(float)((Actor) activeLevel.getOccupants().get(i)).getX(),(float)((Actor) activeLevel.getOccupants().get(i)).getY());
				//println(theDist);
				if (theDist < 40)
				{
					((Actor) activeLevel.getOccupants().get(i)).toggleHighlighted();
				}
			}
		}
		else if (mode.equals("Gun Mode"))
		{
			images.add(new PImageContainer(gun,150,mouseX-30,mouseY-40));
			for (int i = 0; i < activeLevel.getOccupants().size(); i++)
			{
				float theDist = dist((float)mouseX,(float)mouseY,(float)((Actor) activeLevel.getOccupants().get(i)).getX(),(float)((Actor) activeLevel.getOccupants().get(i)).getY());
				//println(theDist);
				if (theDist < 40)
				{
					//println("shot");
					((Actor) activeLevel.getOccupants().get(i)).removeSelfFromLevel();
				}
			}
		}
	}

	/**
	 * Makes all of the actors in a certain level of a certain world act.
	 * Renders all of them.
	 * @param world the world that is being looked at
	 */
	public void step(World world)
	{
		ArrayList<Actor> activeOccupants = world.getLevel(world.getActiveLevel()).getOccupants();
		//for (int i = activeOccupants.size() - 1; i >= 0; i--)
		for (int i = 0; i < activeOccupants.size(); i++)
		{
			if (activeOccupants.get(i).isHighlighted())
			{
				fill(50,50,200);
				stroke(0);
				ellipse((float)activeOccupants.get(i).getX(),(float)activeOccupants.get(i).getY(),40,40);
			}
		}
		for (int i = 0; i < activeOccupants.size(); i++)
		{
			if (!config.get("Toggle ESM"))
			{
				stroke(255);
				fill(activeOccupants.get(i).getR(),activeOccupants.get(i).getG(),activeOccupants.get(i).getB());
			}
			else 
			{
				stroke(0);
				fill((float)Math.random()*255,(float)Math.random()*255,(float)Math.random()*255);
				activeOccupants.get(i).setSize((int) (Math.random()*15));
			}

			if (!(activeOccupants.get(i) instanceof Obstacle))
			{
				if (activeOccupants.get(i) instanceof Ant && config.get("Toggle Starvation"))
					((Ant) activeOccupants.get(i)).setFood(901);
				if (!(activeOccupants.get(i) instanceof SmartAnt))
					rect((float)activeOccupants.get(i).getX(),(float)activeOccupants.get(i).getY(), (float)activeOccupants.get(i).getSize(), (float)activeOccupants.get(i).getSize());
				if (activeOccupants.get(i) instanceof SmartAnt)
				{
					//text(((SmartAnt) activeOccupants.get(i)).getGen() + "," + ((SmartAnt) activeOccupants.get(i)).getFood(), (float)activeOccupants.get(i).getX()+5, (float)activeOccupants.get(i).getY()-5);

					SmartAnt sa = (SmartAnt) activeOccupants.get(i);
					float x = (float) sa.getX();
					float y = (float) sa.getY();

					rect(x,y,3,3);

					if (sa.getGeneralDirection() < 45)
					{
						rect(x+3,y,3,3);
						rect(x+6,y,3,3);
					}
					else if (sa.getGeneralDirection() < 90)
					{
						rect(x+3,y-3,3,3);
						rect(x+6,y-6,3,3);
					}
					else if (sa.getGeneralDirection() < 135)
					{
						rect(x,y-3,3,3);
						rect(x,y-6,3,3);
					}
					else if (sa.getGeneralDirection() < 180)
					{
						rect(x-3,y-3,3,3);
						rect(x-6,y-6,3,3);
					}
					else if (sa.getGeneralDirection() < 225)
					{
						rect(x-3,y,3,3);
						rect(x-6,y,3,3);
					}
					else if (sa.getGeneralDirection() < 270)
					{
						rect(x-3,y+3,3,3);
						rect(x-6,y+6,3,3);
					}
					else if (sa.getGeneralDirection() < 315)
					{
						rect(x,y+3,3,3);
						rect(x,y+6,3,3);
					}
					else
					{
						rect(x+3,y+3,3,3);
						rect(x+6,y+6,3,3);
					}

				}
				if (activeOccupants.get(i) instanceof WarAnt)
				{
					WarAnt temp = ((WarAnt) activeOccupants.get(i));
					double percent = temp.getHP()/temp.getOrig();
					//println(percent);

					if (temp.getColony().equals("colony1"))
						fill(0,255,0);
					else if (temp.getColony().equals("colony2"))
						fill(255,0,0);
					else
						fill(255,255,255);

					rect((float)temp.getX() + 5, (float)temp.getY() - 10, 15*(float)percent, (float)5);
					text(((WarAnt) activeOccupants.get(i)).getWarLevel(),(float)temp.getX() + 15, (float)temp.getY() - 10 );
				}
				/*if (activeOccupants.get(i) instanceof Fungus)
				{
					fill(activeOccupants.get(i).getR(),activeOccupants.get(i).getG(),activeOccupants.get(i).getB());
					if (((Fungus) activeOccupants.get(i)).getColony().equals("colony1"))
						text("C1",(float)activeOccupants.get(i).getX(),(float)activeOccupants.get(i).getY() - 5);
					else if (((Fungus) activeOccupants.get(i)).getColony().equals("colony2"))
						text("C2",(float)activeOccupants.get(i).getX(),(float)activeOccupants.get(i).getY() - 5);
					else
						text("N",(float)activeOccupants.get(i).getX(),(float)activeOccupants.get(i).getY() - 5);
				}*/
			} 
			else
			{
				rect((float)activeOccupants.get(i).getX(),(float)activeOccupants.get(i).getY(), (float)((Obstacle) activeOccupants.get(i)).getSizeX(), (float)((Obstacle) activeOccupants.get(i)).getSizeY());
			}
			if (acting)
				if (!activeOccupants.get(i).act()) i--;
		}
	}

	public void messageToConsole(String actionCommand) {
		console.add(0, actionCommand);
	}

	public Compiler getCompiler() {
		return compiler;
	}

	public int getDamage(int level)
	{
		if (level >= levelMinDamage.length) return 2000;
		double min = levelMinDamage[level];
		double max = levelMaxDamage[level];
		return (int)((Math.random()*max-min) + min);
	}

}
