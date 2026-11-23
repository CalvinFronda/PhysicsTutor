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
 *         Version: 2026-11-22
 */

package physicstutor.simulation;

import physicstutor.physics.Vector2D;
import physicstutor.simulation.InclinedPlane;
import physicstutor.simulation.SimulationListener;
import physicstutor.simulation.SimulationScenario;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Purpose: The reponsibility of SimulationPanel is the UI component for the
 * Explore mode.
 *
 * SimulationPanel is-a JPanel, SimulationListener
 */
public class SimulationPanel extends JPanel implements SimulationListener
{
	private static final int MARGIN = 40;
	
	private SimulationScenario scenario;
	private double pixelsPerMeter = 80;
	
	public SimulationPanel()
	{
		setPreferredSize(new Dimension(500, 400));
		setBackground(Color.WHITE);
	}
	
	public void setScenario(SimulationScenario s)
	{
		this.scenario = s;
		repaint();
	}
	
	@Override
	public void onStep(double time)
	{
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (scenario == null) return;
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Ground
		g2.setColor(new Color(0x94A3B8));
		g2.setStroke(new BasicStroke(2f));
		g2.drawLine(0, getHeight() - MARGIN, getWidth(), getHeight() - MARGIN);
		
		if (scenario instanceof InclinedPlane ramp)
		{
			drawRamp(g2, ramp);
		}
		else
		{
			// TODO: drawing for ProjectileMotion and CircularMotion
			Point p = toScreen(scenario.getObject().getPosition());
			g2.setColor(new Color(0x2563EB));
			g2.fillOval(p.x - 8, p.y - 8, 16, 16);
		}
		
		g2.dispose();
	}
	
	private void drawRamp(Graphics2D g2, InclinedPlane ramp)
	{
		double theta = ramp.getAngleRadians();
		double width = InclinedPlane.RAMP_WIDTH;
		double height = width * Math.tan(theta);
		
		// Scale so the whole ramp fits in the panel
		double fitX = (getWidth() - 2.0 * MARGIN) / (width + 0.5);
		double fitY = (getHeight() - 2.0 * MARGIN) / Math.max(height + 0.5, 1);
		pixelsPerMeter = Math.min(fitX, fitY);
		
		// Ramp triangle
		Point a = toScreen(new Vector2D(0, 0));
		Point b = toScreen(new Vector2D(width, 0));
		Point c = toScreen(new Vector2D(width, height));
		Path2D tri = new Path2D.Double();
		tri.moveTo(a.x, a.y);
		tri.lineTo(b.x, b.y);
		tri.lineTo(c.x, c.y);
		tri.closePath();
		g2.setColor(new Color(0xE2E8F0));
		g2.fill(tri);
		g2.setColor(new Color(0x475569));
		g2.draw(tri);
		
		// Block: a square sitting on the slope, tilted to match it
		double side = 0.5 * pixelsPerMeter;
		Point p = toScreen(scenario.getObject().getPosition());
		AffineTransform at = AffineTransform.getTranslateInstance(p.x, p.y);
		at.rotate(-theta); // screen y is flipped
		Rectangle2D block = new Rectangle2D.Double(-side / 2, -side, side,
				side);
		g2.setColor(new Color(0x2563EB));
		g2.fill(at.createTransformedShape(block));
		g2.setColor(new Color(0x1E3A8A));
		g2.draw(at.createTransformedShape(block));
	}
	
	/**
	 * World meters (y up) to screen pixels (y down), origin at the bottom-left
	 * margin.
	 */
	private Point toScreen(Vector2D p)
	{
		int x = (int) Math.round(MARGIN + p.getX() * pixelsPerMeter);
		int y = (int) Math
				.round(getHeight() - MARGIN - p.getY() * pixelsPerMeter);
		return new Point(x, y);
	}
}
