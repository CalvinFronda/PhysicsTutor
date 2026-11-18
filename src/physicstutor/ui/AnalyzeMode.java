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
 * 
 *         Version: 2026-11-07
 */
package physicstutor.ui;

import physicstutor.simulation.Equation;
import physicstutor.simulation.SimulationScenario;
import physicstutor.learning.FreeBodyDiagram;
import physicstutor.physics.Force;
import physicstutor.simulation.Variable;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

/**
 * Purpose: Shows how an object move depending on the scenario
 *
 * AnalyzeMode is-a LearningMode
 */
public class AnalyzeMode implements LearningMode
{
	private SimulationScenario scenario;
	private FreeBodyDiagram diagram;
	private final FBDPanel view = new FBDPanel();
	private final JPanel root = new JPanel(new BorderLayout());
	
	// Table header
	private final DefaultTableModel forceModel = new DefaultTableModel(
			new String[] { "Force", "|F| (N)", "Fx (N)", "Fy (N)",
					"Direction (°)" },
			0)
	{
		@Override
		public boolean isCellEditable(int r, int c)
		{
			return false;
		}
	};
	
	private final DefaultListModel<String> knownsModel = new DefaultListModel<>();
	private final DefaultListModel<String> unknownsModel = new DefaultListModel<>();
	private final DefaultListModel<String> equationsModel = new DefaultListModel<>();
	
	public AnalyzeMode()
	{
		buildUI();
	}
	
	private void buildUI()
	{
		JPanel side = new JPanel();
		side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
		
		side.add(section("Forces", new JScrollPane(new JTable(forceModel))));
		
		JPanel vars = new JPanel(new GridLayout(1, 2, 8, 0));
		vars.add(section("Knowns", new JScrollPane(new JList<>(knownsModel))));
		vars.add(section("Unknowns",
				new JScrollPane(new JList<>(unknownsModel))));
		side.add(vars);
		
		side.add(section("Equations that apply",
				new JScrollPane(new JList<>(equationsModel))));
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, view,
				side);
		split.setResizeWeight(0.5);
		root.add(split, BorderLayout.CENTER);
		
	}
	
	private static JPanel section(String title, JComponent body)
	{
		JPanel p = new JPanel(new BorderLayout(0, 4));
		p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		JLabel label = new JLabel(title);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		p.add(label, BorderLayout.NORTH);
		p.add(body, BorderLayout.CENTER);
		return p;
	}
	
	/**
	 * 
	 * Purpose: rebuilds diagram if scenario is changed or if user wants to
	 * reset current scenario
	 */
	public void refresh()
	{
		if (scenario == null) return;
		
		diagram = new FreeBodyDiagram(scenario);
		view.setDiagram(diagram);
		
		forceModel.setRowCount(0);
		for (Force f : diagram.getForces())
		{
			forceModel.addRow(new Object[] {
					
					f.getLabel(),
					
					formatString(f.getMagnitude()), formatString(f.getVector().getX()),
					formatString(f.getVector().getY()),
					formatString(Math.toDegrees(f.getVector().angle())) });
		}
		

		
		knownsModel.clear();
		for (Variable v : scenario.getKnowns())
			knownsModel.addElement(v.toString());
		
		unknownsModel.clear();
		for (Variable v : scenario.getUnknowns())
			unknownsModel.addElement(v.getName() + "  (" + v.getSymbol() + ")");
		
		equationsModel.clear();
		for (Equation e : scenario.getEquations())
			equationsModel.addElement(e.toString());
	}
	
	private static String formatString(double d)
	{
		return String.format("%.2f", d);
	}
	
	@Override
	public JPanel getPanel()
	{
		return root;
	}
	
	@Override
	public String getName()
	{
		return "Analyze";
	}
	
	@Override
	public void setScenario(SimulationScenario scenario)
	{
		this.scenario = scenario;
		refresh();
	}
	
	@Override
	public void activate()
	{
		refresh();
	}
	
	@Override
	public void deactivate()
	{
		/* nothing running in this mode */ }
}
