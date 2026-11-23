/**
 * Lead Author(s):
 * 
 * @author calvinfronda
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

import physicstutor.simulation.Equation;
import physicstutor.simulation.SimulationPanel;
import physicstutor.simulation.SimulationScenario;
import physicstutor.simulation.Simulator;
import physicstutor.simulation.Variable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

/**
 * Purpose: Shows the forces acting on an object depending on a scenario
 *
 * ExploreMode is-a LearningMode
 */
public class ExploreMode implements LearningMode
{
	
	/**
	 * Sliders only hold ints, so each slider runs 0..STEPS and is mapped onto
	 * [min, max].
	 */
	private static final int STEPS = 1000;
	private static final double DT = 1.0 / 60; // seconds per simulation step
	
	private SimulationScenario scenario;
	private final Simulator simulator = new Simulator(DT);
	private final SimulationPanel view = new SimulationPanel();
	
	/**
	 * Swing timer fires on the GUI thread ~60 times a second and drives the
	 * Simulator.
	 */
	private final Timer timer = new Timer(16, e -> simulator.tick());
	
	private final JPanel root = new JPanel(new BorderLayout());
	private final JPanel sliderPanel = new JPanel();
	private final JPanel valuesPanel = new JPanel();
	private final DefaultListModel<String> equationsModel = new DefaultListModel<>();
	private final JButton playButton = new JButton("Play");
	
	public ExploreMode()
	{
		simulator.addListener(view); // panel repaints each step
		simulator.addListener(time -> refreshValues()); // labels update each
														// step
		buildUI();
	}
	
	private void buildUI()
	{
		sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
		valuesPanel.setLayout(new BoxLayout(valuesPanel, BoxLayout.Y_AXIS));
		
		JPanel left = section("Adjust", sliderPanel);
		left.setPreferredSize(new Dimension(280, 0));
		
		// Animation + controls in the middle
		JButton stepButton = new JButton("Step");
		JButton resetButton = new JButton("Reset");
		
		playButton.addActionListener(e -> {
			if (simulator.isRunning()) simulator.pause();
			else simulator.start();
			updatePlayButton();
		});
		stepButton.addActionListener(e -> {
			simulator.pause();
			simulator.step();
			updatePlayButton();
		});
		resetButton.addActionListener(e -> {
			simulator.reset();
			updatePlayButton();
		});
		
		JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
		controls.add(playButton);
		controls.add(stepButton);
		controls.add(resetButton);
		
		JPanel center = new JPanel(new BorderLayout());
		center.add(view, BorderLayout.CENTER);
		center.add(controls, BorderLayout.SOUTH);
		
		JPanel right = new JPanel(new BorderLayout());
		right.setPreferredSize(new Dimension(300, 0));
		right.add(section("Live values", valuesPanel), BorderLayout.NORTH);
		right.add(
				section("Equations",
						new JScrollPane(new JList<>(equationsModel))),
				BorderLayout.CENTER);
		
		root.add(left, BorderLayout.WEST);
		root.add(center, BorderLayout.CENTER);
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
	
	/** One label + slider for each variable the student can change. */
	private void buildSliders()
	{
		sliderPanel.removeAll();
		
		for (Variable v : scenario.getKnowns())
		{
			JLabel label = new JLabel(sliderText(v));
			JSlider slider = new JSlider(0, STEPS, toSlider(v));
			
			slider.addChangeListener(e -> {
				v.setValue(fromSlider(v, slider.getValue()));
				label.setText(sliderText(v));
				scenario.reset(); // recalculates the unknowns
				refreshValues();
			});
			
			label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
			slider.setAlignmentX(JComponent.LEFT_ALIGNMENT);
			sliderPanel.add(label);
			sliderPanel.add(slider);
		}
		
		sliderPanel.revalidate();
		sliderPanel.repaint();
	}
	
	/** Update the calculated values and the equation results. */
	private void refreshValues()
	{
		if (scenario == null) return;
		
		valuesPanel.removeAll();
		valuesPanel.add(
				new JLabel(String.format("t = %.2f s", simulator.getTime())));
		valuesPanel.add(new JLabel(String.format("speed = %.2f m/s",
				scenario.getObject().getVelocity().magnitude())));
		for (Variable v : scenario.getUnknowns())
		{
			valuesPanel.add(new JLabel(v.toString()));
		}
		valuesPanel.revalidate();
		valuesPanel.repaint();
		
		equationsModel.clear();
		for (Equation e : scenario.getEquations())
		{
			equationsModel.addElement(e.getFormula() + "  =  "
					+ String.format("%.2f", e.evaluate()));
		}
	}
	
	private void updatePlayButton()
	{
		playButton.setText(simulator.isRunning() ? "Pause" : "Play");
	}
	
	private static String sliderText(Variable v)
	{
		return String.format("%s (%s): %.2f %s", v.getName(), v.getSymbol(),
				v.getValue(), v.getUnits());
	}
	
	private static int toSlider(Variable v)
	{
		return (int) Math.round((v.getValue() - v.getMin())
				/ (v.getMax() - v.getMin()) * STEPS);
	}
	
	private static double fromSlider(Variable v, int pos)
	{
		return v.getMin() + (v.getMax() - v.getMin()) * pos / STEPS;
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
		view.setScenario(scenario);
		buildSliders();
		simulator.setScenario(scenario); // also resets and refreshes the values
		updatePlayButton();
	}
	
	@Override
	public void activate()
	{
		timer.start();
		refreshValues();
	}
	
	@Override
	public void deactivate()
	{
		
		simulator.pause();
		updatePlayButton();
		timer.stop();
	}
}
