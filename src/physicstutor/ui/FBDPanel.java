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
 *         Oracle
 *         AffineTransform
 *         https://docs.oracle.com/javase/8/docs/api/java/awt/geom/AffineTransform.html
 * 
 *         Version: 2026-11-10
 */
package physicstutor.ui;

import physicstutor.learning.FreeBodyDiagram;
import physicstutor.physics.Force;
import physicstutor.physics.ForceType;
import physicstutor.physics.Vector2D;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Purpose: The FBDPanel draws a FreeBodyDiagram: a box with one arrow pointing
 * to where the force is acting.
 * 
 * FBDPanel is-a JPanel
 */
public class FBDPanel extends JPanel
{
	
	private FreeBodyDiagram diagram;
	
	public FBDPanel()
	{
		setPreferredSize(new Dimension(420, 420));
		setBackground(Color.WHITE);
	}
	
	public void setDiagram(FreeBodyDiagram diagram)
	{
		this.diagram = diagram;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		int cx = getWidth() / 2;
		int cy = getHeight() / 2;
		
		if (diagram == null || diagram.getForces().isEmpty())
		{
			g2.setColor(Color.GRAY);
			g2.drawString("No forces to show", cx - 50, cy);
			g2.dispose();
			return;
		}
		
		// The longest arrow fills ~38% of the smaller panel dimension.
		double maxLen = Math.min(getWidth(), getHeight()) * 0.38;
		double pixelsPerNewton = maxLen
				/ Math.max(diagram.getMaxMagnitude(), 1e-9);
		
		// The object and the dashed surface line if it rests on one
		AffineTransform saved = g2.getTransform();
		g2.translate(cx, cy);
		if (diagram.hasSurface())
		{
			g2.rotate(-diagram.getSurfaceAngle()); // screen y points down, so
													// negate to tilt up-right
			
			Stroke dashed = new BasicStroke(2f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10f, new float[] { 6f, 6f }, 0f);
			g2.setStroke(dashed);
			g2.setColor(Color.GRAY);
			int len = getWidth() + getHeight(); // long enough to cross the
												// whole panel
			g2.drawLine(-len, 18, len, 18); // 18 = half the block, so the line
											// runs under it
		}
		g2.setStroke(new BasicStroke(1.5f)); // object box
		g2.setColor(Color.LIGHT_GRAY); // background color of object
		g2.fillRect(-18, -18, 36, 36);
		g2.setColor(Color.DARK_GRAY); // border around object
		g2.drawRect(-18, -18, 36, 36);
		g2.setTransform(saved); // arrows are drawn unrotated
		
		g2.setFont(getFont().deriveFont(Font.PLAIN, 13f));
		for (Force f : diagram.getForces())
		{
			if (f.getMagnitude() < 1e-6) continue;
			drawArrow(g2, cx, cy, f.getVector().scale(pixelsPerNewton),
					colorFor(f.getType()), null);
			drawLabel(g2, cx, cy, f.getVector().scale(pixelsPerNewton),
					f.getLabel(), colorFor(f.getType()));
		}
		
		// Net force as a dashed arrow
		Vector2D net = diagram.getNetForce();
		if (diagram.getForces().size() > 1 && net.magnitude() > 1e-6)
		{
			Stroke dashed = new BasicStroke(2f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10f, new float[] { 6f, 5f }, 0f);
			drawArrow(g2, cx, cy, net.scale(pixelsPerNewton), Color.BLACK,
					dashed);
			drawLabel(g2, cx, cy, net.scale(pixelsPerNewton), "Net",
					Color.BLACK);
		}
		
		g2.dispose();
	}
	
	/**
	 * Draws an arrow from (cx, cy). Physics y points up, screen y points down,
	 * so y is flipped.
	 */
	private void drawArrow(Graphics2D g2, int cx, int cy, Vector2D v,
			Color color, Stroke stroke)
	{
		final double HEAD_LENGTH = 12;
		
		double ex = cx + v.getX();
		double ey = cy - v.getY();
		double angle = Math.atan2(ey - cy, ex - cx);
		double length = Math.hypot(ex - cx, ey - cy);
		
		// Stop the line where the arrowhead begins so it doesn't poke through
		// the tip
		double lineLength = Math.max(0, length - HEAD_LENGTH);
		double lx = cx + Math.cos(angle) * lineLength;
		double ly = cy + Math.sin(angle) * lineLength;
		
		g2.setColor(color);
		g2.setStroke(stroke != null ? stroke
				: new BasicStroke(3f, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER));
		g2.draw(new Line2D.Double(cx, cy, lx, ly));
		
		// Arrowhead: tip at (0, 0), base HEAD_LENGTH behind it
		Path2D head = new Path2D.Double();
		head.moveTo(0, 0);
		head.lineTo(-HEAD_LENGTH, -6);
		head.lineTo(-HEAD_LENGTH, 6);
		head.closePath();
		
		AffineTransform at = AffineTransform.getTranslateInstance(ex, ey);
		at.rotate(angle);
		g2.fill(at.createTransformedShape(head));
	}
	
	private void drawLabel(Graphics2D g2, int cx, int cy, Vector2D v,
			String text, Color color)
	{
		Vector2D offset = v.normalize().scale(18);
		int x = (int) (cx + v.getX() + offset.getX());
		int y = (int) (cy - v.getY() - offset.getY());
		int w = g2.getFontMetrics().stringWidth(text);
		g2.setColor(color);
		g2.drawString(text, x - w / 2, y + 5);
	}
	
	private static Color colorFor(ForceType type)
	{
		return switch (type)
		{
			case GRAVITY -> Color.BLUE;
			case NORMAL -> Color.RED;
			case FRICTION -> Color.GRAY;
			case TENSION -> Color.GREEN;
			case APPLIED -> Color.BLACK;
		};
	}
	
}
