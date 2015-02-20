package uml_collectors;

import java.util.LinkedList;

import bnf_parser.collectors.Collector;


public class AttributelistCollector extends Collector
{
	// PROTECTED PROPERTIES

	protected LinkedList<DataitemCollector> dataitemCollectors;

	// PUBLIC CONSTRUCTOR

	public AttributelistCollector()
	{
		dataitemCollectors	= new LinkedList<DataitemCollector>();
	}

	// PUBLIC METHODS

	@Override
	public void addChild(String ruleName, Collector collector)
	{
		//System.out.println("... addChild");

		if(null == collector)
		{
			return;
		}

		String ruleName1 = collector.getRuleName();

		if(collector instanceof DataitemCollector)
		{
			dataitemCollectors.add((DataitemCollector) collector);

			DataitemCollector dic = (DataitemCollector) collector;
			//System.out.println("dataitem :: id=" + dic.getIdentifier() + ", type=" + dic.getType());
		}
	}




}
