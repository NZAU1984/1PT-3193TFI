package bnf_parser1;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bnf_parser1.callables.Callable;
import bnf_parser1.callables.CallableContainsMoreThanOneCollectorException;
import bnf_parser1.collectors.Collector;
import bnf_parser1.collectors.CollectorFactory;
import bnf_parser1.collectors.CollectorNotFoundException;


public class Parser
{
	// PUBLIC CONSTANTS

	public final static int INFINITY = Integer.MAX_VALUE;

	// PROTECTED PROPERTIES

	protected FileInputStream fileInputStream;

	protected CharBuffer charBuffer;

	protected int charBufferPosition	= 0;

	protected CollectorFactory collectorFactory;

	protected HashMap<String, Rule> rules;

	protected HashMap<String, Rule1> rules1;

	// PUBLIC CONSTRUCTORS

	public Parser(String filename, String charset, CollectorFactory collectorFactory) throws IOException
	{
		init();

		this.collectorFactory	= collectorFactory;

		// http://www.java-tips.org/java-se-tips/java.util.regex/how-to-apply-regular-expressions-on-the-contents-of-a.html
		fileInputStream			= new FileInputStream(filename);
        FileChannel fileChannel	= fileInputStream.getChannel();
        ByteBuffer byteBuffer	= fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, (int) fileChannel.size());
        charBuffer				= Charset.forName(charset).newDecoder().decode(byteBuffer);
	}

	// PUBLIC METHODS

	public void close()
	{
		try
		{
			fileInputStream.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		finally
		{
			fileInputStream	= null;
			charBuffer		= null;
		}
	}

	// matchRule bnf_parser.callables
/*
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

	// matchString bnf_parser.callables

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

	// matchPattern bnf_parser.callables

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
*/
	// Rule creators

	public void createRule(String ruleName, Callable... args) throws RuleAlreadyExistsException
	{
		createRule(ruleName, null, Rule.NO_COLLECTOR_POSITION_OVERRIDE, args);
	}

	public void createRule(String ruleName, String collectorName, Callable... args) throws RuleAlreadyExistsException
	{
		createRule(ruleName, collectorName, Rule.NO_COLLECTOR_POSITION_OVERRIDE, args);
	}

	public void createRule(String ruleName, int collectorPositionOverride, Callable... args)
		throws RuleAlreadyExistsException
	{
		createRule(ruleName, null, collectorPositionOverride, args);
	}

	protected void createRule(String ruleName, String collectorName,  int collectorPositionOverride, Callable... args)
		throws RuleAlreadyExistsException
	{
		if(rules.containsKey(ruleName))
		{
			throw new RuleAlreadyExistsException();
		}

		rules.put(ruleName, new Rule(collectorName, collectorPositionOverride, args));
	}

	public Rule1 createRule1(String ruleName) throws RuleAlreadyExistsException
	{
		if(rules1.containsKey(ruleName))
		{
			throw new RuleAlreadyExistsException();
		}

		Rule1 rule	= new Rule1(this);

		rules1.put(ruleName, rule);

		return rule;
	}

	/**
	 * Evaluates the specified rule. In case of success, returns a new collector whose type was specified when creating
	 * the rule, including null if none was specified. Throws a 'ParsingFailedException' if any part of the rule
	 * fails parsing.
	 * @param ruleName The name of the rule to be evaluated.
	 * @return
	 * @throws RuleNotFoundException
	 * @throws CollectorNotFoundException
	 * @throws ParsingFailedException
	 * @throws CallableContainsMoreThanOneCollectorException
	 */
	public Collector evaluateRule(String ruleName)
		throws RuleNotFoundException, CollectorNotFoundException, ParsingFailedException,
			CallableContainsMoreThanOneCollectorException
	{
		/* Does rule exist? */
		if(!rules.containsKey(ruleName))
		{
			throw new RuleNotFoundException();
		}

		String collectorName = rules.get(ruleName).getCollectorName();

		/* Creating the rule's collector. It might be 'null' (for example, a simple pattern matching rule may only have
		 * to return true if the pattern matched, but the matched string is not important. As an example, creating a
		 * rule to match spaces often doesn't have to 'collect' the matched spaces. If a callable's collector will
		 * override the rule's collector, the collector, here, will be null as it doesn't make any sense to create a
		 * collector to later replace it by another. */
		Collector collector = collectorFactory.createCollector(collectorName);

		/* If we want this rule to use the collector of one of its bnf_parser.callables, this variable will hold its index which
		 * was defined when creating the rule. */
		int collectorPositionOverride	= rules.get(ruleName).getCollectorPositionOverride();
		Callable[] callables			= rules.get(ruleName).getCallables();

		for(int i = 0, iMax = callables.length; i < iMax; ++i)
		{
			Callable currentCallable = callables[i];

			/* The current callable failed parsing, the current rule failed. Let's exit with an exception. */
			if(!currentCallable.parse())
			{
				throw new ParsingFailedException();
			}

			/* If collector overriding has to occur... */
			if(i == collectorPositionOverride)
			{
				/* Gets the only collector contained in the "overriding" callable. If there are more than one, this
				 * throws a 'CallableContainsMoreThanOneCollectorException'. If there is none, it returns null. */
				collector	= currentCallable.getCollector();
			}
			else
			{
				/* Collector might be null, must check to prevent 'NullPointerException'. */
				if(null != collector)
				{
					/* Adding all of the callable's collectors. Most of the time, there will only be one. */
					for(Collector callableCollector	: currentCallable.getCollectors())
					{
						collector.addChild(ruleName, callableCollector, 0);
					}
				}
			}
		}

		return collector;
	}

/*	public Collector evaluateRule1(Rule1 rule)
			throws RuleNotFoundException, CollectorNotFoundException, ParsingFailedException,
				CallableContainsMoreThanOneCollectorException
		{
			String collectorName = rule.getCollectorName();

			 Creating the rule's collector. It might be 'null' (for example, a simple pattern matching rule may only have
			 * to return true if the pattern matched, but the matched string is not important. As an example, creating a
			 * rule to match spaces often doesn't have to 'collect' the matched spaces. If a callable's collector will
			 * override the rule's collector, the collector, here, will be null as it doesn't make any sense to create a
			 * collector to later replace it by another.
			Collector collector = collectorFactory.createCollector(collectorName);

			 If we want this rule to use the collector of one of its bnf_parser.callables, this variable will hold its index which
			 * was defined when creating the rule.
			int collectorPositionOverride	= rule.getCollectorPositionOverride();
			Callable[] callables			= rule.getCallables();

			for(int i = 0, iMax = callables.length; i < iMax; ++i)
			{
				Callable currentCallable = callables[i];

				 The current callable failed parsing, the current rule failed. Let's exit with an exception.
				if(!currentCallable.parse())
				{
					throw new ParsingFailedException();
				}

				 If collector overriding has to occur...
				if(i == collectorPositionOverride)
				{
					 Gets the only collector contained in the "overriding" callable. If there are more than one, this
					 * throws a 'CallableContainsMoreThanOneCollectorException'. If there is none, it returns null.
					collector	= currentCallable.getCollector();
				}
				else
				{
					 Collector might be null, must check to prevent 'NullPointerException'.
					if(null != collector)
					{
						 Adding all of the callable's collectors. Most of the time, there will only be one.
						for(Collector callableCollector	: currentCallable.getCollectors())
						{
							collector.addChild(rule, callableCollector, 0);
						}
					}
				}
			}

			return collector;
		}*/

	// TODO think about that...
	// NO::PACKAGE::NO METHODS

	/**
	 * Checks if at the current parser's position the provided string exists.
	 * @param string the string to find
	 * @return the string if found, null otherwise
	 */
	public String testString(String string)
	{
		/* Calls 'testPattern' by escaping the string to prevent any regex expression that could be contained in the
		 * string from being evaluated. */
		return testPattern(Pattern.quote(string));
	}

	/**
	 * Checks if at the current parser's position the provided pattern matches.
	 * @param pattern the pattern to check which must not start with '^'
	 * @return the matched string if success, null otherwise
	 */
	public String testPattern(String pattern)
	{
		Pattern pattern1	= Pattern.compile("(^" + pattern + ")", Pattern.DOTALL);
		Matcher matcher		= pattern1.matcher(charBuffer);

		/* There is only one group that can be found, the one contained in the parentheses above. */
		if(matcher.find())
		{
			String group = matcher.group();

			/* Moving the buffer's position after the found string. */
			setBufferPosition(charBufferPosition + group.length());

			return group;
		}

		/* Pattern did not match. */
		return null;
	}

	// PROTECTED METHODS

	// TODO set back to 'protected'
	/**
	 * Returns the buffer's current position.
	 * @return the buffer's current position
	 */
	public int getBufferPosition()
	{
		return charBufferPosition;
	}

	/**
	 * Sets the buffer position.
	 * @param position
	 */
	protected void setBufferPosition(int position)
	{
		charBufferPosition	= position;

		charBuffer.position(charBufferPosition);
	}

	// PRIVATE METHODS

	private void init()
	{
		rules = new HashMap<String, Rule>();
		rules1	= new HashMap<String, Rule1>();
	}
}
