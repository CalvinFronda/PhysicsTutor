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

/**
 * Purpose: manages different types of variables used throughout the
 * application.
 *
 */
public class Variable
{
	private final String name;
	private final String symbol;
	private final String units;
	private double value;
	private final double min;
	private final double max;
	private final boolean adjustable;
	
	public Variable(String name, String symbol, String units, double value,
			double min, double max, boolean adjustable)
	{
		this.name = name;
		this.symbol = symbol;
		this.units = units;
		this.min = min;
		this.max = max;
		this.adjustable = adjustable;
		setValue(value);
	}
	
	public double getValue()
	{
		return value;
	}
	
	/** Clamps to [min, max] so sliders and question values stay in range. */
	public void setValue(double v)
	{
		this.value = Math.max(min, Math.min(max, v));
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getSymbol()
	{
		return symbol;
	}
	
	public String getUnits()
	{
		return units;
	}
	
	public double getMin()
	{
		return min;
	}
	
	public double getMax()
	{
		return max;
	}
	
	public boolean isAdjustable()
	{
		return adjustable;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s = %.2f %s", symbol, value, units);
	}
}
