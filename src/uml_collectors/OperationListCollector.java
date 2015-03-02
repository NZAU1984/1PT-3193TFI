package uml_collectors;

import java.util.ArrayList;

import uml_interfaces.Operation;
import uml_interfaces.OperationList;
import bnf_parser.collectors.Collector;
import bnf_parser.collectors.StringCollector;

/**
 * This class collects a {@code Dataitem} which correspond to <identifier>:<multiplicity> (with/without spaces).
 *
 * @author Hubert Lemelin
 */
public class OperationListCollector extends Collector implements OperationList
{
	// PROTECTED PROPERTIES

	protected ArrayList<OperationCollector>	operationCollectors;

	public OperationListCollector()
	{
		super();

		operationCollectors	= new ArrayList<OperationCollector>();
	}

	/**
	 * Expects two {@link StringCollector}'s, the first one (index = 0) being the {@code identifier}, the second one
	 * (index = 1) being the {@code multiplicity}.
	 *
	 * @see Collector#addChild(Collector, int)
	 */
	@Override
	public void addChild(Collector collector, int index)
	{
		if((null != collector) && (collector instanceof OperationCollector))
		{
			operationCollectors.add((OperationCollector) collector);
		}
	}

	@Override
	public Operation[] getOperations()
	{
		return operationCollectors.toArray(new Operation[operationCollectors.size()]);
	}

	@Override
	public String toString()
	{
		StringBuilder sb	= new StringBuilder();

		sb.append("OperationList{");

		if(0 < operationCollectors.size())
		{
			sb.append(operationCollectors.get(0));

			for(int i = 1, iMax = operationCollectors.size(); i < iMax; ++i)
			{
				sb.append(", ").append(operationCollectors.get(i));
			}
		}

		sb.append("}");

		return sb.toString();
	}
}
