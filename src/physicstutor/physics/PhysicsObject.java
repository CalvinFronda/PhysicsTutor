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
 *         Version: 2026-11-17
 */
package physicstutor.physics;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Stores the state (mass, position, velocity, acceleration) of a
 * Physics Object
 * 
 * 
 */
public abstract class PhysicsObject
{
	protected double mass;
	protected Vector2D position;
	protected Vector2D velocity;
	protected Vector2D acceleration;
	protected final List<Force> forces = new ArrayList<>();
	
	protected PhysicsObject(double mass, Vector2D position)
	{
		this.mass = mass;
		this.position = position;
		this.velocity = Vector2D.ZERO;
		this.acceleration = Vector2D.ZERO;
	}
	
	public void applyForce(Force f)
	{
		forces.add(f);
	}
	
	public void clearForces()
	{
		forces.clear();
	}
	
	/** Vector sum of every force currently applied. */
	public Vector2D getNetForce()
	{
		Vector2D net = Vector2D.ZERO;
		for (Force f : forces)
		{
			net = net.add(f.getVector());
		}
		return net;
	}
	
	/**
	 * Advance the object by dt seconds. Subclasses choose the integration step.
	 */
	public abstract void update(double dt);
	
	public void setPosition(Vector2D position)
	{
		this.position = position;
	}
	
	public void setVelocity(Vector2D velocity)
	{
		this.velocity = velocity;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public void setMass(double mass)
	{
		this.mass = mass;
	}
	
	public Vector2D getPosition()
	{
		return position;
	}
	
	public Vector2D getVelocity()
	{
		return velocity;
	}
	
	public Vector2D getAcceleration()
	{
		return acceleration;
	}
	
	public List<Force> getForces()
	{
		return List.copyOf(forces);
	}
}
