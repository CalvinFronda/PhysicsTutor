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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import physicstutor.physics.Vector2D;
import physicstutor.simulation.InclinedPlane;

/** Checks that the free-body diagram adds the forces up correctly. */
public class FreeBodyDiagramTest
{
	private static final double DELTA = 0.01;

	@Test
	void netForceEqualsMassTimesAcceleration()
	{
		InclinedPlane ramp = new InclinedPlane();
		FreeBodyDiagram fbd = new FreeBodyDiagram(ramp);

		// F_net = m·a = 5 × 3.21 = 16.03 N
		assertEquals(16.03, fbd.getNetForce().magnitude(), DELTA);
	}

	@Test
	void netForcePointsDownTheSlope()
	{
		InclinedPlane ramp = new InclinedPlane();
		FreeBodyDiagram fbd = new FreeBodyDiagram(ramp);
		Vector2D net = fbd.getNetForce();

		// The ramp rises to the right, so "down the slope" is 30° below the −x axis: 210°
		assertEquals(210.0, Math.toDegrees(net.angle()) + 360, DELTA);
	}

	@Test
	void diagramKnowsTheSurfaceAngle()
	{
		FreeBodyDiagram fbd = new FreeBodyDiagram(new InclinedPlane());

		assertEquals(30.0, Math.toDegrees(fbd.getSurfaceAngle()), DELTA);
	}
}
