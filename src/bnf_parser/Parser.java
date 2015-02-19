package bnf_parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser
{
	// PUBLIC CONSTANTS

	public final int INFINITY = -1;

	// PROTECTED PROPERTIES

	protected String filename;

	protected HashMap<String, Rule> rules;

	protected String TEMP_STR = "terre";

	// PUBLIC CONSTRUCTORS

	public Parser(String filename)
	{
		init();

		this.filename	= filename;
	}

	// PUBLIC METHODS

	public BnfParserCallable1 matchRuleOnce(String ruleName)
	{
		return null;
	}

	public BnfParserCallable1 matchString(String string, int minOccurences, int maxOccurences)
	{
		return (new MatchString(this, string, minOccurences, maxOccurences));
	}

	public BnfParserCallable1 matchString(String string)
	{
		return (new MatchString(this, string, 1, 1));
	}

	public BnfParserCallable1 matchString_0_1(String string)
	{
		return (new MatchString(this, string, 0, 1));
	}

	public BnfParserCallable1 matchString_0_p(String string)
	{
		return (new MatchString(this, string, 0, INFINITY));
	}

	public BnfParserCallable1 matchString_1_p(String string)
	{
		return (new MatchString(this, string, 1, INFINITY));
	}

	public void createRule(String ruleName, BnfParserCallable1... args) throws RuleAlreadyExistsException
	{
		if(rules.containsKey(ruleName))
		{
			throw new RuleAlreadyExistsException();
		}

		rules.put(ruleName, new Rule(args));
	}

	public boolean evaluateRule(String ruleName) throws RuleNotFoundException
	{
		if(!rules.containsKey(ruleName))
		{
			throw new RuleNotFoundException();
		}

		BnfParserCallable1[] callables = rules.get(ruleName).subRules;

		for(BnfParserCallable1 currentCallable : callables)
		{
			if(!currentCallable.parse())
			{
				return false;
			}
		}

		return true;
	}

	// PACKAGE METHODS

	String testString(String string)
	{
		return testPattern("^" + Pattern.quote(string));
	}

	String testPattern(String pattern)
	{
		Pattern pattern1 = Pattern.compile("(" + pattern + ")");
		Matcher matcher = pattern1.matcher(TEMP_STR);

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
