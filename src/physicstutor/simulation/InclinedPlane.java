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
package physicstutor.simulation;

import physicstutor.physics.Force;
import physicstutor.physics.ForceType;
import physicstutor.physics.Particle;
import physicstutor.physics.Vector2D;

import java.util.List;

/**
 * Purpose: Incline Plane scenario. Manages forces that act on an object on an
 * incline
 * 
 * InclinedPlane is-a SimulationScenario
 *
 */
public class InclinedPlane extends SimulationScenario
{
	// Knowns (adjustable)
	private final Variable mass = new Variable("Mass", "m", "kg", 5.0, 0.5,
			20.0, true);
	private final Variable angle = new Variable("Ramp angle", "θ", "°", 30.0,
			0.0, 60.0, true);
	private final Variable mu = new Variable("Coefficient of friction", "μ", "",
			0.20, 0.0, 1.0, true);
	private final Variable gravity = new Variable("Gravity", "g", "m/s²", 9.81,
			1.62, 24.79, true);
	
	// Unknowns (calculated)
	// 1e6 evaluates to one million as a double. useful for max values
	private final Variable normal = new Variable("Normal force", "N", "N", 0, 0,
			1e6, false);
	private final Variable friction = new Variable("Friction force", "f", "N",
			0, 0, 1e6, false);
	private final Variable accel = new Variable("Acceleration", "a", "m/s²", 0,
			0, 1e6, false);
	
	public InclinedPlane()
	{
		super("Inclined Plane");
		variables.addAll(
				List.of(mass, angle, mu, gravity, normal, friction, accel));
		object = new Particle(mass.getValue(), topOfRamp());
		recompute();
	}
	
	// Physics
	
	/** Horizontal length of the ramp in meters. */
	public static final double RAMP_WIDTH = 4.0;
	
	private double theta()
	{
		return Math.toRadians(angle.getValue());
	}
	
	public double getAngleRadians()
	{
		return theta();
	}
	
	/**
	 * Where the block starts: the top corner of the ramp for the current angle.
	 */
	private Vector2D topOfRamp()
	{
		double x = RAMP_WIDTH - 0.3; // so the block sits on the slope
		return new Vector2D(x, x * Math.tan(theta()));
	}
	
	/** Unit vector pointing down the slope. */
	private Vector2D downSlope()
	{
		return new Vector2D(-Math.cos(theta()), -Math.sin(theta()));
	}
	
	/** Unit vector perpendicular to the surface, pointing away from it. */
	private Vector2D surfaceNormal()
	{
		return new Vector2D(-Math.sin(theta()), Math.cos(theta()));
	}
	
	public double getNormalForce()
	{
		return mass.getValue() * gravity.getValue() * Math.cos(theta());
	}
	
	/**
	 * True when gravity along the slope is greater than maximum static friction
	 * (tan > μ).
	 */
	public boolean isSliding()
	{
		return Math.tan(theta()) > mu.getValue();
	}
	
	private double getFrictionMagnitude()
	{
		double alongSlope = mass.getValue() * gravity.getValue()
				* Math.sin(theta());
		
		return isSliding() ? mu.getValue() * getNormalForce() : alongSlope;
	}
	
	public double getAcceleration()
	{
		if (!isSliding()) return 0;
		return gravity.getValue()
				* (Math.sin(theta()) - mu.getValue() * Math.cos(theta()));
	}
	
	/** Refresh the calculated (unknown) variables from the current knowns. */
	private void recompute()
	{
		object.setMass(mass.getValue());
		normal.setValue(getNormalForce());
		friction.setValue(getFrictionMagnitude());
		accel.setValue(getAcceleration());
	}
	
	// Overrides below are for the SimulationScenario
	
	@Override
	public double getSurfaceAngle()
	{
		return theta();
	}
	
	// SimulationScenario
	
	@Override
	public List<Force> getForces()
	{
		recompute();
		double m = mass.getValue();
		double g = gravity.getValue();
		return List.of(
				new Force("Weight (mg)", new Vector2D(0, -m * g),
						ForceType.GRAVITY),
				new Force("Normal (N)", surfaceNormal().scale(getNormalForce()),
						ForceType.NORMAL),
				new Force("Friction (f)",
						downSlope().scale(-getFrictionMagnitude()),
						ForceType.FRICTION));
	}
	
	@Override
	public List<Equation> getEquations()
	{
		return List.of(
				new Equation("Normal force", "N = m·g·cos θ",
						List.of(mass, gravity, angle), this::getNormalForce),
				new Equation("Kinetic friction", "f = μ·N", List.of(mu, normal),
						() -> mu.getValue() * getNormalForce()),
				new Equation("Newton's 2nd law along the slope",
						"a = g(sin θ − μ cos θ)", List.of(gravity, angle, mu),
						this::getAcceleration));
	}
	
	@Override
	public void update(double dt)
	{
		if (object.getPosition().getY() <= 0) return; // stop when we reach the
														// bottom
		object.clearForces();
		for (Force f : getForces())
		{
			object.applyForce(f);
		}
		object.update(dt);
	}
	
	@Override
	public void reset()
	{
		object = new Particle(mass.getValue(), topOfRamp());
		recompute();
	}
	
}
