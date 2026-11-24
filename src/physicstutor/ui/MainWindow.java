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
import physicstutor.simulation.*;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
		
		scenarios.add(new InclinedPlane());
		scenarios.add(new CircularMotion());
		
		modes.add(new AnalyzeMode());
		modes.add(new ExploreMode());
		for (LearningMode m : modes)
		{
			tabs.addTab(m.getName(), m.getPanel());
		}
		tabs.addChangeListener(
				e -> showMode(modes.get(tabs.getSelectedIndex())));
		
		JComboBox<SimulationScenario> picker = new JComboBox<>(
				scenarios.toArray(new SimulationScenario[0]));
		picker.addActionListener(e -> setScenario(
				(SimulationScenario) picker.getSelectedItem()));
		
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		top.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));
		top.add(new JLabel("Scenario:"));
		top.add(picker);
		
		setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		add(tabs, BorderLayout.CENTER);
		
		setScenario(scenarios.get(0));
		showMode(modes.get(0));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 650);
		setLocationRelativeTo(null);
		
	}
	
	public void setScenario(SimulationScenario s)
	{
		if (s == null || s == active) return;
		active = s;
		active.reset();
		for (LearningMode m : modes)
		{
			m.setScenario(s);
		}
	}
	
	public void showMode(LearningMode m)
	{
		if (m == currentMode) return;
		if (currentMode != null) currentMode.deactivate();
		currentMode = m;
		currentMode.activate();
	}
	
}
