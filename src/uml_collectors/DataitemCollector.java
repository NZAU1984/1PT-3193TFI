package uml_collectors;

import bnf_parser.collectors.Collector;
import bnf_parser.collectors.StringCollector;


public class DataitemCollector extends Collector
{
	// PROTECTED PROPERTIES

	protected String identifier;
	protected String type;

	// PUBLIC CONSTRUCTOR

	public DataitemCollector()
	{
		System.out.println("** DataitemCollector::constructor");

		collectorName		= "DataitemCollector";
	}

	// PUBLIC METHODS

	@Override
	public void addChild(String ruleName, Collector collector)
	{
		if(null == collector)
		{
			return;
		}

		if(collector instanceof DataitemCollector)
		{
			System.out.println("=> Dataitemcollector");
		}

		String ruleName1 = collector.getRuleName();

		//System.out.println("    addChild::ruleName=" + ruleName + ", ruleName1=" + ruleName1);

		if(null == ruleName1)
		{
			return;
		}

		if(collector instanceof StringCollector)
		{
			if(ruleName1.equals("identifier"))
			{
				identifier = ((StringCollector) collector).getString();
				//System.out.println("    DataitemCollector::id = " + identifier);
			}
			else if(ruleName1.equals("type"))
			{
				type = ((StringCollector) collector).getString();
				//System.out.println("    DataitemCollector::type = " + type);
			}
		}
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public String getType()
	{
		return type;
	}


}
