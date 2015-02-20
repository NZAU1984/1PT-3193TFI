package uml_collectors;

import java.util.LinkedList;

import bnf_parser.collectors.Collector;
import bnf_parser.collectors.StringCollector;


public class OperationCollector extends Collector
{
	// PROTECTED PROPERTIES

	protected LinkedList<DataitemCollector> dataitemCollectors;

	// PUBLIC CONSTRUCTOR

	public OperationCollector()
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

		if((collector instanceof StringCollector) &&  ((null != ruleName1) && !ruleName1.equals("")))
		{
			System.out.println("... str = " + ((StringCollector) collector).getString() + ", ruleName=" + ruleName1);
		}

		if(collector instanceof DataitemCollector)
		{
			dataitemCollectors.add((DataitemCollector) collector);

			DataitemCollector dic = (DataitemCollector) collector;
			System.out.println("dataitem :: id=" + dic.getIdentifier() + ", type=" + dic.getType());
		}

		if(collector instanceof AttributelistCollector)
		{
			System.out.println("attr list");
		}
	}




}
