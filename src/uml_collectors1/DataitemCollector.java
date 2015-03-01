package uml_collectors1;

import bnf_parser1.collectors.Collector;
import bnf_parser1.collectors.StringCollector;

/**
 * This class collects a {@code Dataitem} which correspond to <identifier>:<type> (with/without spaces).
 *
 * @author Hubert Lemelin
 */
public class DataitemCollector extends Collector
{
	protected String identifier;
	protected String type;

	public DataitemCollector()
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
		if((null != collector) && (collector instanceof StringCollector))
		{
			/* If collector is an instance of StringCollector, let's grab its string. */
			String str	= ((StringCollector) collector).getString();

			/* Switch on the index to set the right property. */
			switch(index)
			{
				case 0:
					identifier	= str;

					break;

				case 1:
					type	= str;
//TODO remove
//System.out.println("// DataitemCollector :: " + identifier + " = " + type + " //");
					break;

				default:
					break;
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
