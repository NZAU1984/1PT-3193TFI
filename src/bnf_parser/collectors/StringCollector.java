package bnf_parser.collectors;


public class StringCollector extends Collector
{
	// PROTECTED PROPERTIES

	StringBuilder sb;

	// PUBLIC CONSTRUCTOR

	public StringCollector()
	{
		sb	= new StringBuilder();
	}

	// PUBLIC METHODS

	@Override
	public void addChild(String ruleName, Collector collector)
	{
		if(collector instanceof StringCollector)
		{
			sb.append(((StringCollector) collector).getString());
		}
	}

	@Override
	public void addString(String string)
	{
		sb.append(string);
	}

	public String getString()
	{
		return sb.toString();
	}


}
