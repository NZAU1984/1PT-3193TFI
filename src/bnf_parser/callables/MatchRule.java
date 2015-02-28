package bnf_parser.callables;

import bnf_parser.Parser;
import bnf_parser.collectors.Collector;


public class MatchRule extends Callable
{
	protected String ruleName;

	public MatchRule(Parser parser, String ruleName, int minOccurences, int maxOccurences)
	{
		super(parser, minOccurences, maxOccurences);

		this.ruleName	= ruleName;
	}

	@Override
	public boolean parse()
	{
		resetCollectors();

		int occurences		= 0;

		while(occurences <= maxOccurences)
		{
			try
			{
				Collector collector	= parser.evaluateRule(ruleName);

				if(null != collector)
				{
					collector.setRuleName(ruleName);
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
