package bnf_parser.callables;

import bnf_parser.Parser;
import bnf_parser.collectors.Collector;
import bnf_parser.collectors.StringCollector;

public class MatchString extends Callable
{
	protected String string;

	public MatchString(Parser parser, String string, int minOccurences, int maxOccurences)
	{
		super(parser, minOccurences, maxOccurences);

		this.string	= string;
	}

	@Override
	public boolean parse()
	{
		resetCollectors();

		Collector collector	= null;
		int occurences		= 0;
		StringBuilder sb	= new StringBuilder();

		String val;

		while((val = parser.testString(string)) != null)
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
