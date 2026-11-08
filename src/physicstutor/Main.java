/**
 * Lead Author(s):
 * 
 * @author Calvin Fronda;
 *
 *
 *         References:
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *
 *         Version: 2026-11-07
 */
package physicstutor;

import physicstutor.ui.MainWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Purpose: The reponsibility of Main is ...
 *
 * Main is-a ...
 * Main is ...
 */
public class Main
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() -> {
			try
			{
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			}
			catch (Exception ignored)
			{
				System.out.println("something wrong");
			}
			
			new MainWindow().setVisible(true);
		});
	}
}
