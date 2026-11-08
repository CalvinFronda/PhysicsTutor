/**
 * Lead Author(s):
 * 
 * @author calvinfronda; student ID
 * @author Full name; student ID
 *         <<Add additional lead authors here>>
 * 		
 *         Other Contributors:
 *         Full name; student ID or contact information if not in class
 *         <<Add additional contributors (mentors, tutors, friends) here, with
 *         contact information>>
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
	
}
