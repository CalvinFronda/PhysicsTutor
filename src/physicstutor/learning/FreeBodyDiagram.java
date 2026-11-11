/**
 * Lead Author(s):
 * 
 * @author calvinfronda; student ID
 * 
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
package physicstutor.learning;

import physicstutor.physics.Force;
import physicstutor.simulation.SimulationScenario;
import java.util.List;

/**
 * Purpose: The reponsibility of FreeBodyDiagram is ...
 *
 * FreeBodyDiagram is-a ...
 * FreeBodyDiagram is ...
 */
public class FreeBodyDiagram
{
	private final List<Force> forces;
	
	public FreeBodyDiagram(SimulationScenario s)
	{
		this.forces = List.copyOf(s.getForces());
	}
	
	public List<Force> getForces()
	{
		return forces;
	}
	

	

	
}
