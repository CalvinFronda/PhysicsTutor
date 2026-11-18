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
 *         Version: 2026-11-10
 */
package physicstutor.physics;

/**
 * Purpose: Shows a labeled force acting on an object
 *
 */
public class Force
{
	private final String label;
	private final Vector2D vector;
	
	private final ForceType type;
	
	public Force(String label, Vector2D vector, ForceType type)
	{
		this.label = label;
		this.vector = vector;
		this.type = type;
		
	}
	
	public double getMagnitude()
	{
		return vector.magnitude();
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public Vector2D getVector()
	{
		return vector;
	}
	
	public ForceType getType()
	{
		return type;
	}
	
}
