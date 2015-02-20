package bnf_parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bnf_parser.collectors.Collector;
import bnf_parser.collectors.CollectorFactory;
import bnf_parser.collectors.CollectorNotFoundException;


public class Parser
{
	// PUBLIC CONSTANTS

	public final int INFINITY = Integer.MAX_VALUE;

	// PROTECTED PROPERTIES

	protected String filename;

	protected CollectorFactory collectorFactory;

	protected HashMap<String, Rule> rules;

	protected String TEMP_STR = "add_joueur(element : Joueur, xx : yy) : void";

	// PUBLIC CONSTRUCTORS

	public Parser(String filename, CollectorFactory collectorFactory)
	{
		init();

		this.filename			= filename;
		this.collectorFactory	= collectorFactory;
	}

	// PUBLIC METHODS

	// matchRule callables

	public Callable matchRule(String ruleName, int minOccurences, int maxOccurences)
	{
		return (new MatchRule(this, ruleName, minOccurences, maxOccurences));
	}

	public Callable matchRule(String ruleName)
	{
		return matchRule(ruleName, 1, 1);
	}

	public Callable matchRule_0_1(String ruleName)
	{
		return matchRule(ruleName, 0, 1);
	}

	public Callable matchRule_0_p(String ruleName)
	{
		return matchRule(ruleName, 0, INFINITY);
	}

	public Callable matchRule_1_p(String ruleName)
	{
		return matchRule(ruleName, 1, INFINITY);
	}

	// matchString callables

	public Callable matchString(String string, int minOccurences, int maxOccurences)
	{
		return (new MatchString(this, string, minOccurences, maxOccurences));
	}

	public Callable matchString(String string)
	{
		return matchString(string, 1, 1);
	}

	public Callable matchString_0_1(String string)
	{
		return matchString(string, 0, 1);

	}

	public Callable matchString_0_p(String string)
	{
		return matchString(string, 0, INFINITY);
	}

	public Callable matchString_1_p(String string)
	{
		return matchString(string, 1, INFINITY);
	}

	// matchPattern callables

	public Callable matchPattern(String pattern, int minOccurences, int maxOccurences)
	{
		return (new MatchPattern(this, pattern, minOccurences, maxOccurences));
	}

	public Callable matchPattern(String pattern)
	{
		return matchPattern(pattern, 1, 1);
	}

	public Callable matchPattern_0_1(String pattern)
	{
		return matchPattern(pattern, 0, 1);

	}

	public Callable matchPattern_0_p(String pattern)
	{
		return matchPattern(pattern, 0, INFINITY);
	}

	public Callable matchPattern_1_p(String pattern)
	{
		return matchPattern(pattern, 1, INFINITY);
	}

	//

	public void createRule(String ruleName, String collectorName, Callable... args) throws RuleAlreadyExistsException
	{
		if(rules.containsKey(ruleName))
		{
			throw new RuleAlreadyExistsException();
		}

		rules.put(ruleName, new Rule(collectorName, args));
	}

	public Collector evaluateRule(String ruleName) throws RuleNotFoundException, CollectorNotFoundException, ParsingFailedException
	{
		if(!rules.containsKey(ruleName))
		{
			throw new RuleNotFoundException();
		}
//System.out.println("Starting rule " + ruleName);
		String collectorName	= rules.get(ruleName).getCollectorName();
		Callable[] callables	= rules.get(ruleName).getCallables();
		Collector collector		= collectorFactory.createCollector(collectorName);

		for(Callable currentCallable : callables)
		{

			if(!currentCallable.parse())
			{
				//System.out.println("Failing rule " + ruleName);
				throw new ParsingFailedException();
			}

			if(null != collector)
			{
				for(Collector callableCollector	: currentCallable.getCollectors())
				{
					collector.addChild(ruleName, callableCollector, 0);
				}
			}
		}
		//System.out.println("Ending rule " + ruleName);
		return collector;
	}

	// PACKAGE METHODS

	String testString(String string)
	{
		return testPattern(Pattern.quote(string));
	}

	String testPattern(String pattern)
	{
		Pattern pattern1	= Pattern.compile("(^" + pattern + ")");
		Matcher matcher		= pattern1.matcher(TEMP_STR);

		while(matcher.find())
		{
			String group = matcher.group();

			TEMP_STR = TEMP_STR.substring(group.length());

			return group;

		}

		return null;
	}

	// PROTECTED METHODS

	// PRIVATE METHODS

	private void init()
	{
		rules = new HashMap<String, Rule>();
	}

}
