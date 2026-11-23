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

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Simulator manages the state for explore mode
 *
 */
public class Simulator
{
	private SimulationScenario scenario;
	private double time = 0;
	private final double dt;
	private boolean running = false;
	private final List<SimulationListener> listeners = new ArrayList<>();
	
	public Simulator(double dt)
	{
		this.dt = dt;
	}
	
	public void setScenario(SimulationScenario s)
	{
		this.scenario = s;
		reset();
	}
	
	public void start()
	{
		running = true;
	}
	
	public void pause()
	{
		running = false;
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	public double getTime()
	{
		return time;
	}
	
	/** Stop, rewind the clock and put the scenario back at its start. */
	public void reset()
	{
		running = false;
		time = 0;
		if (scenario != null) scenario.reset();
		notifyListeners();
	}
	
	/** Advance exactly one time step (used by the Step button). */
	public void step()
	{
		if (scenario == null) return;
		scenario.update(dt);
		time += dt;
		notifyListeners();
	}
	
	/** Called by the timer every frame; only moves when playing. */
	public void tick()
	{
		if (running) step();
	}
	
	public void addListener(SimulationListener l)
	{
		listeners.add(l);
	}
	
	private void notifyListeners()
	{
		for (SimulationListener l : listeners)
		{
			l.onStep(time);
		}
	}
}
