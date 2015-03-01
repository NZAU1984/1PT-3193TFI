package bnf_parser1.callables;

import bnf_parser1.Rule;
import bnf_parser1.SubparserInterface;

/**
 * This class checks whether or not a rule matches from the current position in the file being parsed.
 *
 * @author Hubert Lemelin
 */
public class MatchAnyRuleOnce extends Callable
{
	/**
	 * The rule that must be matched.
	 */
	protected Rule[] rules;

	/**
	 * Constructor.
	 * @param rule			The rule to be matched.
	 * @param minOccurences	The minimum number of occurences the pattern must appear. Can be 0.
	 * @param maxOccurences	The maximum number of occurences the pattern must appear. Can be 'infinity'
	 * ({@code Integer.MAX_VALUE}).
	 */
	public MatchAnyRuleOnce(Rule... rules)
	{
		super(1, 1);

		this.rules	= rules;
	}

	/**
	 * Checks if the rule matches in the parser at the current file position. The number of occurences of the pattern
	 * was defined in the constructor.
	 *
	 * @return	True if parsing succeeded, false otherwise.
	 */
	@Override
	public boolean parse(SubparserInterface parser)
	{
		resetCollectors();

		for(Rule rule : rules)
		{
			try
			{
				addCollector(parser.evaluateRule(rule));

				return true;
			}
			catch (Exception e)
			{
				continue;
			}
		}

		return false;
	}
}
