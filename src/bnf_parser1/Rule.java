package bnf_parser1;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import bnf_parser1.callables.Callable;
import bnf_parser1.callables.MatchAnyRuleOnce;
import bnf_parser1.callables.MatchPattern;
import bnf_parser1.callables.MatchRule;
import bnf_parser1.callables.MatchString;
import bnf_parser1.collectors.Collector;


public class Rule
{
	// PUBLIC STATIC CONSTANTS

	public final static int NO_COLLECTOR_OVERRIDE	= -1;

	public final static int INFINITY = Integer.MAX_VALUE;

	// PROTECTED PROPERTIES

	protected Class<?> collectorClass;

	protected ArrayList<CallableContainer> callables;

	protected int iteratorIndex	= -1;

	protected int collectorOverrideIndex = NO_COLLECTOR_OVERRIDE;

	// TODO remove
	// temp
	protected String name="";
	public Rule setName(String name)
	{
		this.name=name;
		return this;
	}
	public String getName()
	{
		return name;
	}
	//temp
	Rule()
	{
		collectorClass	= null;
		callables		= new ArrayList<CallableContainer>();
	}

	public Rule setCollector(Class<?> collectorClass) throws IncorrectCollectorException
	{
		if(!Collector.class.isAssignableFrom(collectorClass))
		{
			throw new IncorrectCollectorException();
		}

		this.collectorClass = collectorClass;

		return this;
	}

	public Rule matchString(String string, int minOccurences, int maxOccurences)
	{
		return matchString(string, minOccurences, maxOccurences, true);
	}

	public Rule matchStringWithoutCollecting(String string, int minOccurences, int maxOccurences)
	{
		return matchString(string, minOccurences, maxOccurences, false);
	}

	public Rule matchPattern(String pattern, int minOccurences, int maxOccurences)
	{
		return matchPattern(pattern, minOccurences, maxOccurences, true);
	}

	public Rule matchPatternWithoutCollecting(String pattern, int minOccurences, int maxOccurences)
	{
		return matchPattern(pattern, minOccurences, maxOccurences, false);
	}

	public Rule matchRule(Rule rule, int minOccurences, int maxOccurences)
	{
		callables.add(new CallableContainer(new MatchRule(rule, minOccurences, maxOccurences)));

		return this;
	}

	public Rule matchAnyRuleOnce(Rule... rules)
	{
		callables.add(new CallableContainer(new MatchAnyRuleOnce(rules)));

		return this;
	}

	public Rule overrideCollector() throws NoSubruleDefinedException
	{
		collectorOverrideIndex	= getPreviousCallableIndex();

		return this;
	}

	public Rule setIndex(int index) throws NoSubruleDefinedException
	{
		callables.get(getPreviousCallableIndex()).index = index;

		return this;
	}

	// PACKAGE

	Collector createCollector()
	{
		if(null == collectorClass)
		{
			return null;
		}

		try
		{
			return (Collector) collectorClass.getConstructor().newInstance();
		}
		catch(Exception e)
		{
			return null;
		}
	}

	void resetIterator()
	{
		iteratorIndex	= -1;
	}

	boolean hasNext()
	{
		return (!callables.isEmpty() && (iteratorIndex < (callables.size() - 1)));
	}

	Callable next() throws NoSuchElementException
	{
		if(!hasNext())
		{
			throw new NoSuchElementException();
		}

		return callables.get(++iteratorIndex).callable;
	}

	boolean noCollectorOverriding()
	{
		return collectorOverrideIndex == NO_COLLECTOR_OVERRIDE;
	}

	boolean doOverrideCollector()
	{
		return (iteratorIndex == collectorOverrideIndex);
	}

	int getIndex()
	{
		return callables.get(iteratorIndex).index;
	}


	// PROTECTED

	protected Rule matchString(String string, int minOccurences, int maxOccurences, boolean collectString)
	{
		callables.add(new CallableContainer(new MatchString(string, minOccurences, maxOccurences, collectString)));

		return this;
	}

	protected Rule matchPattern(String pattern, int minOccurences, int maxOccurences, boolean collectString)
	{
		callables.add(new CallableContainer(new MatchPattern(pattern, minOccurences, maxOccurences, collectString)));

		return this;
	}

	protected int getPreviousCallableIndex() throws NoSubruleDefinedException
	{
		if(0 == callables.size())
		{
			throw new NoSubruleDefinedException();
		}

		return callables.size() - 1;
	}

	protected class CallableContainer
	{
		protected Callable callable;
		protected int index = -1;

		protected CallableContainer(Callable callable)
		{
			this.callable	= callable;
		}
	}
}
