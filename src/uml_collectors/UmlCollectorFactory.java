package uml_collectors;

import bnf_parser.collectors.Collector;
import bnf_parser.collectors.CollectorFactory;

public class UmlCollectorFactory extends CollectorFactory
{
	public UmlCollectorFactory()
	{

	}

	@Override
	protected Collector _createCollector(String collectorName)
	{
		if(collectorName.equals("DataitemCollector"))
		{
			return new DataitemCollector();
		}

		if(collectorName.equals("AttributelistCollector"))
		{
			return new AttributelistCollector();
		}

		if(collectorName.equals("OperationCollector"))
		{
			return new OperationCollector();
		}

		System.out.println("%" + collectorName);
		// TODO Auto-generated method stub

		return null;
	}

}
