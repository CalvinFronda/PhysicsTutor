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
 *
 *         Version: 2026-11-10
 */
package physicstutor.physics;

/**
 * Purpose: The reponsibility of Vector2D is ...
 *
 * Vector2D is-a ...
 * Vector2D is ...
 */
public final class Vector2D
{
	
	public static final Vector2D ZERO = new Vector2D(0, 0);
	
	private final double x;
	private final double y;
	
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	
	
	public double getX()
	{
		return x;
		
	}
	
	public double getY()
	{
		return y;
	}
	
}
