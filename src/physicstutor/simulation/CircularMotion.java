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
 *         Version: 2026-11-23
 */
package physicstutor.simulation;

import java.util.List;

import physicstutor.physics.Force;
import physicstutor.physics.ForceType;
import physicstutor.physics.Particle;
import physicstutor.physics.Vector2D;

/**
 * Purpose: Circular Motion scenario. Manages forces that act on an object going
 * in a circle
 *
 * CircularMotion is-a SimulationScenario
 */
public class CircularMotion extends SimulationScenario
{
	// Knowns (adjustable)
	private final Variable mass = new Variable("Mass", "m", "kg", 0.5, 0.1, 5.0,
			true);
	private final Variable radius = new Variable("Radius", "r", "m", 2.0, 0.5,
			5.0, true);
	private final Variable speed = new Variable("Speed", "v", "m/s", 4.0, 1.0,
			15.0, true);
	
	// Unknowns (calculated)
	private final Variable centripetal = new Variable(
			"Centripetal acceleration", "a_c", "m/s²", 0, 0, 1e6, false);
	private final Variable tension = new Variable("Tension", "F_T", "N", 0, 0,
			1e6, false);
	private final Variable omega = new Variable("Angular velocity", "ω",
			"rad/s", 0, 0, 1e6, false);
	private final Variable period = new Variable("Period", "P", "s", 0, 0, 1e6,
			false);
	
	private double phase = 0; // current angle around the circle, radians
	
	public CircularMotion()
	{
		super("Circular Motion");
		variables.addAll(List.of(mass, radius, speed, centripetal, tension,
				omega, period));
		reset();
	}
	
	// ---------- Physics ----------
	
	/** a_c = v² / r */
	public double getCentripetalAccel()
	{
		return speed.getValue() * speed.getValue() / radius.getValue();
	}
	
	/** F_T = m·v² / r (the tension supplies all of the centripetal force) */
	public double getTension()
	{
		return mass.getValue() * getCentripetalAccel();
	}
	
	/** ω = v / r */
	public double getAngularVelocity()
	{
		return speed.getValue() / radius.getValue();
	}
	
	/** P = 2πr / v */
	public double getPeriod()
	{
		return 2 * Math.PI * radius.getValue() / speed.getValue();
	}
	
	public double getRadius()
	{
		return radius.getValue();
	}
	
	private void recompute()
	{
		object.setMass(mass.getValue());
		centripetal.setValue(getCentripetalAccel());
		tension.setValue(getTension());
		omega.setValue(getAngularVelocity());
		period.setValue(getPeriod());
	}
	
	/**
	 * Put the ball on the circle at the current phase, moving
	 * counter-clockwise.
	 */
	private void placeOnCircle()
	{
		double r = radius.getValue();
		double v = speed.getValue();
		object.setPosition(
				new Vector2D(r * Math.cos(phase), r * Math.sin(phase)));
		object.setVelocity(
				new Vector2D(-v * Math.sin(phase), v * Math.cos(phase)));
	}
	
	// ---------- SimulationScenario ----------
	
	@Override
	public List<Force> getForces()
	{
		// Points from the ball back toward the center
		Vector2D towardCenter = object.getPosition().scale(-1).normalize();
		return List.of(new Force("Tension (F_T)",
				towardCenter.scale(getTension()), ForceType.TENSION));
	}
	
	@Override
	public List<Equation> getEquations()
	{
		return List.of(
				new Equation("Centripetal acceleration", "a_c = v² / r",
						List.of(speed, radius), this::getCentripetalAccel),
				new Equation("Newton's 2nd law toward the center",
						"F_T = m·v² / r", List.of(mass, speed, radius),
						this::getTension),
				new Equation("Angular velocity", "ω = v / r",
						List.of(speed, radius), this::getAngularVelocity),
				new Equation("Period", "P = 2πr / v", List.of(speed, radius),
						this::getPeriod));
	}
	
	/**
	 * Uniform circular motion has an exact solution, so we move the ball
	 * along the circle by ω·dt each step. (Stepping with F = ma would
	 * slowly drift off the circle because of rounding in each step.)
	 */
	@Override
	public void update(double dt)
	{
		phase += getAngularVelocity() * dt;
		placeOnCircle();
		object.clearForces();
		for (Force f : getForces())
		{
			object.applyForce(f);
		}
	}
	
	@Override
	public void reset()
	{
		phase = 0;
		object = new Particle(mass.getValue(),
				new Vector2D(radius.getValue(), 0));
		placeOnCircle();
		recompute();
	}
	
}
