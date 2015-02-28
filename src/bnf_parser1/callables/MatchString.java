package bnf_parser1.callables;

import java.util.regex.Pattern;

import bnf_parser1.SubparserInterface;
import bnf_parser1.collectors.Collector;
import bnf_parser1.collectors.StringCollector;

public class MatchString extends Callable
{
	protected String string;

	public MatchString(String string, int minOccurences, int maxOccurences)
	{
		super(minOccurences, maxOccurences);

		this.string	= string;
	}

	@Override
	public boolean parse(SubparserInterface parser)
	{
		resetCollectors();

		Collector collector	= null;
		int occurences		= 0;
		StringBuilder sb	= new StringBuilder();

		String val;

		while((val = parser.matchPattern(Pattern.quote(string))) != null)
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
