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
package physicstutor.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import physicstutor.physics.Force;
import physicstutor.physics.ForceType;

/**
 * Checks InclinedPlane against values worked out by hand.
 * Defaults: m = 5 kg, angle = 30, μ = 0.20, g = 9.81 m/s²
 */
public class InclinedPlaneTest
{
	private static final double DELTA = 0.01;   // doubles rarely match exactly

	private InclinedPlane ramp;

	@BeforeEach
	void setUp()
	{
		ramp = new InclinedPlane();   // fresh scenario for every test
	}

	@Test
	void weightIsMassTimesGravity()
	{
		// 5 × 9.81 = 49.05 N
		assertEquals(49.05, forceOfType(ForceType.GRAVITY).getMagnitude(), DELTA);
	}

	@Test
	void normalForceIsMgCosTheta()
	{
		// 5 × 9.81 × cos 30 = 42.48 N
		assertEquals(42.48, forceOfType(ForceType.NORMAL).getMagnitude(), DELTA);
	}

	@Test
	void frictionIsMuTimesNormalWhenSliding()
	{
		// tan 30 = 0.58 > μ = 0.20, so it slides: f = 0.20 × 42.48 = 8.50 N
		assertTrue(ramp.isSliding(), "block should slide at 30 with μ = 0.20");
		assertEquals(8.50, forceOfType(ForceType.FRICTION).getMagnitude(), DELTA);
	}

	@Test
	void accelerationDownTheSlope()
	{
		// 9.81 × (sin 30  − 0.20 × cos 30) = 3.21 m/s²
		assertEquals(3.21, ramp.getAcceleration(), DELTA);
	}

	@Test
	void blockStaysPutWhenFrictionIsHigh()
	{
		// μ = 0.80 > tan 30  = 0.58, so static friction holds it
		setVariable("μ", 0.80);

		assertFalse(ramp.isSliding(), "block should not slide when μ > tan θ");
		assertEquals(0.0, ramp.getAcceleration(), DELTA);
		// static friction exactly balances m·g·sin θ = 24.53 N
		assertEquals(24.53, forceOfType(ForceType.FRICTION).getMagnitude(), DELTA);
	}

	@Test
	void flatGroundHasNormalEqualToWeight()
	{
		// θ = 0: N = m·g = 49.05 N and nothing slides
		setVariable("θ", 0.0);

		assertEquals(49.05, forceOfType(ForceType.NORMAL).getMagnitude(), DELTA);
		assertEquals(0.0, ramp.getAcceleration(), DELTA);
	}

	// helpers

	/** Finds the force of the given type in the scenario's force list. */
	private Force forceOfType(ForceType type)
	{
		for (Force f : ramp.getForces())
		{
			if (f.getType() == type)
			{
				return f;
			}
		}
		throw new AssertionError("No " + type + " force found");
	}

	/** Sets a known variable by its symbol, e.g. "μ" or "θ". */
	private void setVariable(String symbol, double value)
	{
		for (Variable v : ramp.getVariables())
		{
			if (v.getSymbol().equals(symbol))
			{
				v.setValue(value);
				return;
			}
		}
		throw new AssertionError("No variable with symbol " + symbol);
	}
}
