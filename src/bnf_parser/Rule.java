package bnf_parser;


public class Rule
{
	protected String collectorName;

	protected Callable[] callables;

	Rule(String collectorName, Callable... callables)
	{
		this.collectorName	= collectorName;
		this.callables	= callables;
	}

	String getCollectorName()
	{
		return collectorName;
	}

	Callable[] getCallables()
	{
		return callables;
	}
}
