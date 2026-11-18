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
 * Purpose: Manages a vector. Can do basic arithmetic on a single vector
 *
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
	
	public static Vector2D fromPolar(double mag, double angle)
	{
		return new Vector2D(mag * Math.cos(angle), mag * Math.sin(angle));
	}
	
	public Vector2D add(Vector2D v)
	{
		return new Vector2D(x + v.x, y + v.y);
	}
	
	public Vector2D subtract(Vector2D v)
	{
		return new Vector2D(x - v.x, y - v.y);
	}
	
	public Vector2D scale(double k)
	{
		return new Vector2D(x * k, y * k);
	}
	
	public double dot(Vector2D v)
	{
		return x * v.x + y * v.y;
	}
	
	public double magnitude()
	{
		return Math.hypot(x, y);
	}
	
	public double angle()
	{
		return Math.atan2(y, x);
	}
	
	/** Unit vector in the same direction (ZERO stays ZERO). */
	public Vector2D normalize()
	{
		double m = magnitude();
		return m == 0 ? ZERO : scale(1.0 / m);
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
