package bnf_parser1;

import java.util.LinkedList;

import bnf_parser1.callables.Callable;
import bnf_parser1.callables.MatchPattern;
import bnf_parser1.callables.MatchString;


public class Rule1
{
	// PUBLIC STATIC CONSTANTS

	public final static int NO_COLLECTOR_OVERRIDE	= -1;

	public final static int INFINITY = Integer.MAX_VALUE;

	// PROTECTED PROPERTIES

	protected Parser parser;

	protected String collectorName;

	protected Callable[] callables;

	protected LinkedList<Callable> tempCallableList;

	protected int collectorOverrideIndex = NO_COLLECTOR_OVERRIDE;

	Rule1(Parser parser)//String collectorName, int collectorOverrideIndex, Callable... callables)
	{
		this.parser	= parser;
		tempCallableList	= new LinkedList<Callable>();
		//this.collectorName				= collectorName;
		//this.collectorPositionOverride	= collectorOverrideIndex;
		//this.callables					= callables;
	}

	public void setCollectorName(String collectorName)
	{
		this.collectorName	= collectorName;
	}

	public Rule1 matchString(String string, int minOccurences, int maxOccurences, boolean overrideCollector)
	{
		tempCallableList.add(new MatchString(parser, string, minOccurences, maxOccurences));

		return this;
	}

	public Rule1 matchString(String string, int minOccurences, int maxOccurences)
	{
		return matchString(string, minOccurences, maxOccurences, false);
	}

	public Rule1 matchPattern(String pattern, int minOccurences, int maxOccurences, boolean overrideCollector)
	{
		tempCallableList.add(new MatchPattern(parser, pattern, minOccurences, maxOccurences));

		return this;
	}

	public Rule1 matchPattern(String pattern, int minOccurences, int maxOccurences)
	{
		return matchPattern(pattern, minOccurences, maxOccurences, false);
	}

	public Rule1 build()
	{
		if(collectorOverrideIndex != NO_COLLECTOR_OVERRIDE)
		{
			collectorName	= null;
		}

		callables	= tempCallableList.toArray(new Callable[tempCallableList.size()]);

		tempCallableList.clear();

		tempCallableList	= null;

		return this;
	}

	// PROTECTED

	protected void overrideCollector(boolean overrideCollector)
	{
		if(overrideCollector)
		{
			collectorOverrideIndex	= getCallableIndex();
		}
	}

	protected int getCallableIndex()
	{
		return tempCallableList.size();
	}


	String getCollectorName()
	{
		return collectorName;
	}

	int getCollectorPositionOverride()
	{
		return collectorOverrideIndex;
	}

	Callable[] getCallables()
	{
		return callables;
	}
}
