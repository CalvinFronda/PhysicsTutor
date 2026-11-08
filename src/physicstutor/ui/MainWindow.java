/**
 * Lead Author(s):
 * 
 * @author Calvin Fronda;
 * 
 *
 *         References:
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *         Version: 2026-11-07
 */
package physicstutor.ui;

import physicstutor.simulation.SimulationScenario;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: The reponsibility of MainWindow is ...
 *
 * MainWindow is-a JFrame
 * MainWindow is the GUI for the application
 */
public class MainWindow extends JFrame
{

	private final List<LearningMode> modes = new ArrayList<>();
	private final List<SimulationScenario> scenarios = new ArrayList<>();
	private SimulationScenario active;
	
	private final JTabbedPane tabs = new JTabbedPane();
	private LearningMode currentMode;
	
	public MainWindow()
	{
		super("Physics Tutor");
		
		// Placeholder tabs. Later, each one becomes a LearningMode's panel.
		tabs.addTab("Explore",
				new JLabel("Explore mode coming soon", SwingConstants.CENTER));
		tabs.addTab("Analyze",
				new JLabel("Analyze mode coming soon", SwingConstants.CENTER));
		tabs.addTab("Practice",
				new JLabel("Practice mode coming soon", SwingConstants.CENTER));
		
		setLayout(new BorderLayout());
		add(tabs, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 650);
		setLocationRelativeTo(null);
	}
}
