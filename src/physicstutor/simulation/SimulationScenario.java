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
import java.util.ArrayList;
import physicstutor.physics.Particle;

/**
 * Purpose: Base class for scenarios
 *
 */
public abstract class SimulationScenario
{
	protected final String name;
	protected Particle object;
	protected final List<Variable> variables = new ArrayList<>();
	
	protected SimulationScenario(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<Variable> getVariables()
	{
		return List.copyOf(variables);
	}
	
	/** Angle (radians) of the surface the object rests on, or NaN if there isn't one. */
	public double getSurfaceAngle()
	{
		return Double.NaN;
	}
	
	/** Inputs the student controls ("knowns" in Analyze mode). */
	public List<Variable> getKnowns()
	{
		return variables.stream().filter(Variable::isAdjustable).toList();
	}
	
	/** Equations that apply to this scenario. */
	public abstract List<Equation> getEquations();
	
	/** Advance the scenario by dt seconds. */
	public abstract void update(double dt);
	
	/** Values the scenario calculates ("unknowns" in Analyze mode). */
	public List<Variable> getUnknowns()
	{
		return variables.stream().filter(v -> !v.isAdjustable()).toList();
	}
	
	public abstract List<Force> getForces();
	
	public void reset()
	{
		return;
	}
	
	// so a JComboBox shows the name
	@Override
	public String toString()
	{
		return name;
	} 
}
