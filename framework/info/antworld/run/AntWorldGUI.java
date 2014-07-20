package info.antworld.run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AntWorldGUI extends JPanel implements ActionListener {

	private Runner runner;
	private JFrame frame;

	public void addComponentsToPane(Container pane) {
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		addEventButton(addAButton("Clear All", pane, KeyEvent.VK_E)).setActionCommand("Clear All");
		//addEventButton(addAButton("Clear Food", pane)).setActionCommand("Clear Food");
		addEventButton(addAButton("Clear Ants", pane)).setActionCommand("Clear Ants");
		//addEventButton(addAButton("Clear Queens", pane)).setActionCommand("Clear Queens");
		addEventButton(addAButton("Clear Fungus", pane)).setActionCommand("Clear Fungus");
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		addEventButton(addAButton("Spawn Food Mode", pane)).setActionCommand("Spawn Food Mode");
		addEventButton(addAButton("Spawn Smart Ant Mode", pane)).setActionCommand("Spawn Smart Ant Mode");
		addEventButton(addAButton("Spawn Queen Ant Mode", pane)).setActionCommand("Spawn Queen Ant Mode");
		addEventButton(addAButton("Spawn War Ant Mode", pane, KeyEvent.VK_W)).setActionCommand("Spawn War Ant Mode");
		addEventButton(addAButton("Spawn Infection Mode", pane, KeyEvent.VK_I)).setActionCommand("Spawn Infection Mode");
		addEventButton(addAButton("Spawn Fungus Mode", pane, KeyEvent.VK_F)).setActionCommand("Spawn Fungus Mode");
		addEventButton(addAButton("Spawn Obstacle Mode", pane)).setActionCommand("Spawn Obstacle Mode");
		addEventButton(addAButton("Highlight Mode", pane)).setActionCommand("Highlight Mode");		
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		//addEventButton(addAButton("Gun Mode", pane)).setActionCommand("Gun Mode");
		addEventButton(addAButton("Pause", pane)).setActionCommand("Pause");
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		addEventButton(addAButton("Very Slow", pane)).setActionCommand("Very Slow");
		addEventButton(addAButton("Slow", pane)).setActionCommand("Slow");
		addEventButton(addAButton("Medium", pane)).setActionCommand("Medium");
		addEventButton(addAButton("Fast", pane)).setActionCommand("Fast");
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		addEventButton(addAButton("Toggle Generate Ants", pane)).setActionCommand("Toggle Generate Ants");
		addEventButton(addAButton("Toggle Generate SmartAnts", pane)).setActionCommand("Toggle Generate SmartAnts");
		addEventButton(addAButton("Toggle Generate QueenAnts", pane)).setActionCommand("Toggle Generate QueenAnts");
		addEventButton(addAButton("Toggle Generate Food", pane)).setActionCommand("Toggle Generate Food");
		addEventButton(addAButton("Toggle Generate Pheromones", pane)).setActionCommand("Toggle Generate Pheromones");
		addEventButton(addAButton("Toggle Starvation", pane, KeyEvent.VK_S)).setActionCommand("Toggle Starvation");		
		//pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		//addEventButton(addAButton("Toggle ESM", pane)).setActionCommand("Toggle ESM");
		//addEventButton(addAButton("Toggle Networked Agriculture", pane)).setActionCommand("Toggle Networked Agriculture");
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		addEventButton(addAButton("Tilt Left", pane)).setActionCommand("Tilt Left");
		addEventButton(addAButton("Tilt Right", pane)).setActionCommand("Tilt Right");
		addEventButton(addAButton("Tilt Forward", pane)).setActionCommand("Tilt Forward");
		addEventButton(addAButton("Tilt Backward", pane)).setActionCommand("Tilt Backward");
		addEventButton(addAButton("Tilt Level", pane)).setActionCommand("Tilt Level");
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		addEventButton(addAButton("Toggle War Ant Color", pane, KeyEvent.VK_T)).setActionCommand("Toggle War Ant Color");
	}

	private JButton addAButton(String text, Container container) {
		JButton button = new JButton(text);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		//button.setSize(new Dimension(200,20));
		container.add(button);
		return button;
	}
	
	private JButton addAButton(String text, Container container, int key)
	{
		JButton button = addAButton(text,container);
		button.setMnemonic(key);
		return button;
	}

	private JButton addEventButton(JButton button)
	{
		button.addActionListener(this);
		return button;
	}

	private void createAndShowGUI() {
		frame = new JFrame("AntWorldGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponentsToPane(frame.getContentPane());

		frame.pack();
		frame.setVisible(true);
		
		//updateButtons();
	}

	public AntWorldGUI(Runner runner) {
		this.runner = runner;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		runner.doCommand(e.getActionCommand());
		runner.messageToConsole(e.getActionCommand());
		try
		{
		updateButtons();
		} catch (NullPointerException f) {}
	}

	public void updateButtons()
	{
		for (int i = 0; i < frame.getContentPane().getComponents().length; i++)
		{
			if (frame.getContentPane().getComponents()[i] instanceof AbstractButton)
			{
				AbstractButton temp = ((AbstractButton)frame.getContentPane().getComponents()[i]);
				if ( temp.getText().contains("Toggle") )
				{
					if (runner.config.get(temp.getText()))
						temp.setBackground(new Color(0,0,0));
					else
						temp.setBackground(new Color(255,255,255));
				}
				else if ( temp.getText().contains("Mode") )
				{
					if (temp.getText().equals(runner.mode))
						temp.setBackground(new Color(0,0,0));
					else
						temp.setBackground(new Color(255,255,255));
				}
			}
		}
	}
}