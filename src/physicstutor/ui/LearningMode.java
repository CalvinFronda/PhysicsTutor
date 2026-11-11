/**
 * Lead Author(s):
 * 
 * @author Calvin Fronda;
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

import physicstutor.simulation.SimulationScenario;

import javax.swing.JPanel;

/**
 * Purpose:
 *
 * LearningMode is an interface that is used by specific learning modes
 */
public interface LearningMode
{
	/** Label shown on the tab. */
	String getName();
	
	JPanel getPanel();
	
	void setScenario(SimulationScenario scenario);
	
	void activate();
	
	void deactivate();
	
}
