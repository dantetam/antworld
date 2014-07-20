package info.antworld.run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AntScriptCMD extends JPanel implements ActionListener {

	private Runner runner;
	private JFrame frame;

	public void addComponentsToPane(Container pane) {
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		addEventButton(addAButton("Compile", pane)).setActionCommand("Compile");
	}

	private JButton addAButton(String text, Container container) {
		JButton button = new JButton(text);
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		container.add(button);
		return button;
	}

	private JButton addEventButton(JButton button)
	{
		button.addActionListener(this);
		return button;
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private void createAndShowGUI() {
		//Create and set up the window.
		frame = new JFrame("AntScriptCMD");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the content pane.
		addComponentsToPane(frame.getContentPane());

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public AntScriptCMD(Runner runner) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		this.runner = runner;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			runner.getCompiler().compileCommand(e.getActionCommand().toLowerCase());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}