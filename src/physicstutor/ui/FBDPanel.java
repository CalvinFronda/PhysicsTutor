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
package physicstutor.ui;

import javax.swing.JPanel;

import physicstutor.learning.FreeBodyDiagram;

import java.awt.*;

/**
 * Purpose: The reponsibility of FBDPanel is ...
 *
 * FBDPanel is-a ...
 * FBDPanel is ...
 */
public class FBDPanel extends JPanel
{
	private FreeBodyDiagram diagram;
	
	public FBDPanel()
	{
		setPreferredSize(new Dimension(420, 420));
		setBackground(Color.WHITE);
	}
	
	public void setDiagram(FreeBodyDiagram d)
	{
		this.diagram = d;
		repaint();
	}
	
}
