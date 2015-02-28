package bnf_parser1.callables;

import bnf_parser1.SubparserInterface;
import bnf_parser1.collectors.Collector;
import bnf_parser1.collectors.StringCollector;


public class MatchPattern extends Callable
{
	protected String pattern;

	public MatchPattern(String pattern, int minOccurences, int maxOccurences)
	{
		super(minOccurences, maxOccurences);

		this.pattern	= pattern;
	}

	@Override
	public boolean parse(SubparserInterface parser)
	{
		resetCollectors();

		Collector collector	= null;
		int occurences		= 0;
		StringBuilder sb	= new StringBuilder();

		String val;

		while((val = parser.matchPattern(pattern)) != null)
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
