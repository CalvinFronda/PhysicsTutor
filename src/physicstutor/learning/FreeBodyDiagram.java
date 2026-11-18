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
import physicstutor.physics.Vector2D;
import physicstutor.simulation.SimulationScenario;

import java.util.List;

/**
 * Purpose: Data model for a free-body diagram
 *
 */
public class FreeBodyDiagram
{
	private final List<Force> forces;
	private final double surfaceAngle; // radians, or NaN if there's no surface
	
	public FreeBodyDiagram(SimulationScenario s)
	{
		this.forces = List.copyOf(s.getForces());
		this.surfaceAngle = s.getSurfaceAngle();
	}
	
	public List<Force> getForces()
	{
		return forces;
	}
	
	public Vector2D getNetForce()
	{
		Vector2D net = Vector2D.ZERO;
		for (Force f : forces)
		{
			net = net.add(f.getVector());
		}
		return net;
	}
	
	public boolean hasSurface()
	{
		return !Double.isNaN(surfaceAngle);
	}
	
	public double getSurfaceAngle()
	{
		return surfaceAngle;
	}
	
	/** Largest single force magnitude, used by FBDPanel to scale arrows. */
	public double getMaxMagnitude()
	{
		double max = 0;
		for (Force f : forces)
		{
			max = Math.max(max, f.getMagnitude());
		}
		return max;
	}
	
}
