package bnf_parser.callables;

import bnf_parser.Parser;
import bnf_parser.collectors.Collector;
import bnf_parser.collectors.StringCollector;


public class MatchPattern extends Callable
{
	protected String pattern;

	public MatchPattern(Parser parser, String pattern, int minOccurences, int maxOccurences)
	{
		super(parser, minOccurences, maxOccurences);

		this.pattern	= pattern;
	}

	@Override
	public boolean parse()
	{
		resetCollectors();

		Collector collector	= null;
		int occurences		= 0;
		StringBuilder sb	= new StringBuilder();

		String val;

		while((val = parser.testPattern(pattern)) != null)
		{
			sb.append(val);

			++occurences;

			if(occurences == maxOccurences)
			{
				break;
			}
		}

		if(occurences >= minOccurences)
		{
			collector	= new StringCollector();
			collector.addString(sb.toString());

			addCollector(collector);

			return true;
		}

		return false;
	}

}
