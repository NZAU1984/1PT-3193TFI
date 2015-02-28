package bnf_parser1.callables;

import bnf_parser1.Parser;
import bnf_parser1.collectors.Collector;
import bnf_parser1.collectors.StringCollector;

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
