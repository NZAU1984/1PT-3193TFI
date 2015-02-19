package bnf_parser;

public class MatchString extends BnfParserCallable1
{
	protected String string;

	MatchString(Parser parser, String string, int minOccurences, int maxOccurences)
	{
		super(parser, minOccurences, maxOccurences);

		this.string	= string;
	}

	@Override
	public boolean parse()
	{
		int occurences = 0;

		String val;// = parser.testString(string);

		while((val = parser.testString(string)) != null)
		{
			System.out.println("== val = " + val);
			++occurences;

			if(occurences == maxOccurences)
			{
				return true;
			}
		}

		return (occurences >= minOccurences);
	}

}
