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
 *
 *         Version: 2026-11-17
 */
package physicstutor.simulation;

import java.util.List;
import java.util.function.DoubleSupplier;

/**
 * Purpose: Manages name, formula, variables of different equations
 *
 */
public class Equation
{
	
	private final String name;
	private final String formula;
	private final List<Variable> variables;
	private final DoubleSupplier evaluator;
	
	public Equation(String name, String formula, List<Variable> variables,
			DoubleSupplier evaluator)
	{
		this.name = name;
		this.formula = formula;
		this.variables = List.copyOf(variables);
		this.evaluator = evaluator;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getFormula()
	{
		return formula;
	}
	
	public List<Variable> getVariables()
	{
		return variables;
	}
	
	/** Plug in the current variable values and return the result. */
	public double evaluate()
	{
		return evaluator.getAsDouble();
	}
	
	@Override
	public String toString()
	{
		return name + ":  " + formula;
	}
}
