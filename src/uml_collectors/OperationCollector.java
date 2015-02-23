package uml_collectors;

import bnf_parser.collectors.Collector;
import bnf_parser.collectors.StringCollector;


public class OperationCollector extends Collector
{
	// PROTECTED PROPERTIES

	protected String identifier;

	protected DataitemCollector[] dataitemCollectors;

	// PUBLIC CONSTRUCTOR

	public OperationCollector()
	{
		System.out.println("OperationCollector :: constructor");

		collectorName		= "OperationCollector";
	}

	// PUBLIC METHODS

	@Override
	public void addChild(String ruleName, Collector collector)
	{
		if(null == collector)
		{
			return;
		}

		String ruleName1 = collector.getRuleName();

		if((collector instanceof StringCollector) &&  ((null != ruleName1) && ruleName1.equals("identifier")))
		{
			identifier = ((StringCollector) collector).getString();

			return;
		}

		if((collector instanceof StringCollector) &&  ((null != ruleName1) && ruleName1.equals("type")))
		{
			System.out.println("TYPE");

			return;
		}

		if(collector instanceof AttributelistCollector)
		{
			dataitemCollectors	= ((AttributelistCollector) collector).getDataitemCollectors();
		}
	}




}
