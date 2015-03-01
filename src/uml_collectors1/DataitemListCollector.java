package uml_collectors1;

import bnf_parser1.collectors.Collector;
import bnf_parser1.collectors.StringCollector;

/**
 * This class collects a {@code Dataitem} which correspond to <identifier>:<type> (with/without spaces).
 *
 * @author Hubert Lemelin
 */
public class DataitemListCollector extends Collector
{
	public DataitemListCollector()
	{
		super();

// TODO remove
//System.out.println("CONSTRUCTOR DataitemCollector");
	}

	/**
	 * Expects two {@link StringCollector}'s, the first one (index = 0) being the {@code identifier}, the second one
	 * (index = 1) being the {@code type}.
	 *
	 * @see Collector#addChild(Collector, int)
	 */
	@Override
	public void addChild(Collector collector, int index)
	{
		if((null != collector) && (collector instanceof DataitemCollector))
		{
			System.out.println("!! DataitemCollector !! " + ((DataitemCollector) collector).getIdentifier() + " : " + ((DataitemCollector) collector).getType());
		}
	}

}
