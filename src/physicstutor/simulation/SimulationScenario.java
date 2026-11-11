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
 *         Version: 2026-10-31
 */
package physicstutor.simulation;

import physicstutor.physics.Force;
import java.util.List;

/**
 * Purpose: The reponsibility of SimulationScenario is ...
 *
 * SimulationScenario is-a ...
 * SimulationScenario is ...
 */
public abstract class SimulationScenario
{
	protected final String name;
	
	protected SimulationScenario(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public abstract List<Force> getForces();
	
	public void reset()
	{
		return;
	}
}
