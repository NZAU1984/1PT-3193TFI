package bnf_parser1.callables;

import bnf_parser1.Rule;
import bnf_parser1.SubparserInterface;
import bnf_parser1.collectors.Collector;


public class MatchRule extends Callable
{
	protected String ruleName;

	protected Rule rule;

	public MatchRule(Rule rule, int minOccurences, int maxOccurences)
	{
		super(minOccurences, maxOccurences);

		//this.ruleName	= ruleName;

		this.rule = rule;
	}

	@Override
	public boolean parse(SubparserInterface parser)
	{
		resetCollectors();

		int occurences		= 0;

		while(occurences <= maxOccurences)
		{
			try
			{
				//Collector collector	= parser.evaluateRule(ruleName);
				Collector collector	= parser.evaluateRule(rule);

				if(null != collector)
				{
					//collector.setRuleName(ruleName);
				}

				addCollector(collector);
			}
			catch (Exception e)
			{
				//e.printStackTrace();

				//resetCollectors();

				break;
			}

			++occurences;

			if(occurences == maxOccurences)
			{
				break;
			}
		}

		if(occurences >= minOccurences)
		{
			// collector...

			return true;
		}

		// TODO simplify
		resetCollectors();

		return false;
	}

}
