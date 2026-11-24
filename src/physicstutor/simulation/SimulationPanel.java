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
import java.awt.Stroke;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

/**
 * Purpose: The reponsibility of SimulationPanel is the UI component for the
 * Explore mode.
 *
 * SimulationPanel is-a JPanel that is SimulationListener 
 * 
 */
public class SimulationPanel extends JPanel implements SimulationListener
{
	private static final int MARGIN = 40;
	
	private SimulationScenario scenario;
	private double pixelsPerMeter = 80;
	private double originX, originY; // screen pixel where world (0, 0) is drawn
	
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
		
		// Scenarios hold no Swing code, so the panel decides how each one
		// looks.
		if (scenario instanceof InclinedPlane ramp)
		{
			drawRamp(g2, ramp);
		}
		else if (scenario instanceof CircularMotion circle)
		{
			drawCircle(g2, circle);
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
		useBottomLeftOrigin();
		drawGround(g2);
		
		// Ramp triangle
		Point a = toScreen(new Vector2D(0, 0));
		Point b = toScreen(new Vector2D(width, 0));
		Point c = toScreen(new Vector2D(width, height));
		Path2D tri = new Path2D.Double();
		tri.moveTo(a.x, a.y);
		tri.lineTo(b.x, b.y);
		tri.lineTo(c.x, c.y);
		tri.closePath();
		
		g2.setColor(Color.LIGHT_GRAY);
		g2.fill(tri);
		g2.setColor(Color.GRAY);
		g2.draw(tri);
		
		// Block: a square sitting on the slope, tilted to match it
		double side = 0.5 * pixelsPerMeter;
		Point p = toScreen(scenario.getObject().getPosition());
		AffineTransform at = AffineTransform.getTranslateInstance(p.x, p.y);
		at.rotate(-theta); // screen y is flipped
		Rectangle2D block = new Rectangle2D.Double(-side / 2, -side, side,
				side);
		
		g2.setColor(Color.BLUE);
		g2.fill(at.createTransformedShape(block));
		g2.setColor(Color.BLACK);
		g2.draw(at.createTransformedShape(block));
	}
	
	private void drawCircle(Graphics2D g2, CircularMotion circle)
	{
		double r = circle.getRadius();
		
		// Top-down view: center the circle in the panel, no ground
		pixelsPerMeter = (Math.min(getWidth(), getHeight()) - 2.0 * MARGIN)
				/ (2 * r * 1.15);
		originX = getWidth() / 2.0;
		originY = getHeight() / 2.0;
		
		// Path of the ball
		int rp = (int) Math.round(r * pixelsPerMeter);
		int cx = (int) originX;
		int cy = (int) originY;
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(dashed());
		g2.drawOval(cx - rp, cy - rp, 2 * rp, 2 * rp);
		
		// String from the center to the ball
		Point p = toScreen(scenario.getObject().getPosition());
		
		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(2f));
		g2.drawLine(cx, cy, p.x, p.y);
		
		// Center pin
		g2.fillOval(cx - 5, cy - 5, 10, 10);
		
		drawBall(g2, p);
	}
	
	private void drawGround(Graphics2D g2)
	{
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(2f));
		int y = (int) Math.round(originY);
		g2.drawLine(0, y, getWidth(), y);
	}
	
	private void drawBall(Graphics2D g2, Point p)
	{
		g2.setStroke(new BasicStroke(1.5f));
		g2.setColor(Color.BLUE);
		g2.fillOval(p.x - 9, p.y - 9, 18, 18);
		g2.setColor(Color.GRAY);
		g2.drawOval(p.x - 9, p.y - 9, 18, 18);
	}
	
	private static Stroke dashed()
	{
		return new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
				10f, new float[] { 6f, 6f }, 0f);
	}
	
	/** Ramp and projectile: world (0, 0) sits at the bottom-left margin. */
	private void useBottomLeftOrigin()
	{
		originX = MARGIN;
		originY = getHeight() - MARGIN;
	}
	
	/**
	 * World meters (y up) to screen pixels (y down), origin at the bottom-left
	 * margin.
	 */
	/** World meters (y up) to screen pixels (y down). */
	private Point toScreen(Vector2D p)
	{
		int x = (int) Math.round(originX + p.getX() * pixelsPerMeter);
		int y = (int) Math.round(originY - p.getY() * pixelsPerMeter);
		return new Point(x, y);
	}
}
