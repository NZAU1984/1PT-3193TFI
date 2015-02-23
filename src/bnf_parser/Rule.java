package bnf_parser;

import callables.Callable;


public class Rule
{
	// PUBLIC STATIC CONSTANTS

	public final static int NO_COLLECTOR_POSITION_OVERRIDE	= -1;

	// PROTECTED PROPERTIES

	protected String collectorName;

	protected Callable[] callables;

	protected int collectorPositionOverride;

	Rule(String collectorName, int collectorPositionOverride, Callable... callables)
	{
		this.collectorName				= collectorName;
		this.collectorPositionOverride	= collectorPositionOverride;
		this.callables					= callables;
	}

	String getCollectorName()
	{
		return collectorName;
	}

	int getCollectorPositionOverride()
	{
		return collectorPositionOverride;
	}

	Callable[] getCallables()
	{
		return callables;
	}
}
