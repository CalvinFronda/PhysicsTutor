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
 *         Version: 2026-11-22
 */
package physicstutor.simulation;

/**
 * 
 * Purpose: Listens for changes in SimulationPanel
 *
 */
public interface SimulationListener
{
	void onStep(double time);
}
