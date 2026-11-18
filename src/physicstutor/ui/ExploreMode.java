/**
 * Lead Author(s):
 * 
 * @author calvinfronda; student ID
 *
 *         References:
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *         <<Add more references here>>
 * 		
 *         Version: 2026-11-10
 */
package physicstutor.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

import physicstutor.simulation.SimulationScenario;

/**
 * Purpose: Shows the forces acting on an object depending on a scenario 
 *
 * ExploreMode is-a LearningMode
 */
public class ExploreMode implements LearningMode
{
	
	private final JPanel root = new JPanel(new BorderLayout());
	private final JPanel sliderPanel = new JPanel();
	private final JPanel valuesPanel = new JPanel();
	private final DefaultListModel<String> equationsModel = new DefaultListModel<>();
	
	private SimulationScenario scenario;
	
	public ExploreMode()
	{
		buildUI();
	}
	
	private void buildUI()
	{
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
		valuesPanel.setLayout(new BoxLayout(valuesPanel, BoxLayout.Y_AXIS));
		
		JPanel left = section("Adjust", sliderPanel);
		left.setPreferredSize(new Dimension(280, 0));
		
		JPanel right = new JPanel(new BorderLayout());
		right.setPreferredSize(new Dimension(300, 0));
		right.add(section("Live values", valuesPanel), BorderLayout.NORTH);
		right.add(
				section("Equations",
						new JScrollPane(new JList<>(equationsModel))),
				BorderLayout.CENTER);
		
		root.add(left, BorderLayout.WEST);
		root.add(right, BorderLayout.EAST);
	}
	
	private static JPanel section(String title, JComponent body)
	{
		JPanel p = new JPanel(new BorderLayout(0, 6));
		p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel label = new JLabel(title);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		p.add(label, BorderLayout.NORTH);
		p.add(body, BorderLayout.CENTER);
		return p;
	}
	
	@Override
	public String getName()
	{
		return "Explore";
	}
	
	@Override
	public JPanel getPanel()
	{
		return root;
	}
	
	@Override
	public void setScenario(SimulationScenario scenario)
	{
		this.scenario = scenario;
	}
	
	@Override
	public void activate()
	{
		return;
	}
	
	@Override
	public void deactivate()
	{
		/* nothing running yet */ }
}
