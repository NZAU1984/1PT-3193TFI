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
		System.out.println("%%% AttributeListCollector :: constructor");

		dataitemCollectors	= new LinkedList<DataitemCollector>();
		collectorName		= "AttributelistCollector";
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

		if(collector instanceof DataitemCollector)
		{
			dataitemCollectors.add((DataitemCollector) collector);

			DataitemCollector dic = (DataitemCollector) collector;
		}
	}

	public DataitemCollector[] getDataitemCollectors()
	{
		return dataitemCollectors.toArray(new DataitemCollector[dataitemCollectors.size()]);
	}
}
