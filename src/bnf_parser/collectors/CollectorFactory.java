package bnf_parser.collectors;


public abstract class CollectorFactory
{
	// PUBLIC METHODS

	public Collector createCollector(String collectorName) throws CollectorNotFoundException
	{
		// Some rules don't need a collector, so we allow the null value. It will have to be checked to avoid
		// NullPointerException
		if(null == collectorName)
		{
			return null;
		}

		// Java 6 = no switch on strings...
		if(collectorName.equals("StringCollector"))
		{
			return new StringCollector();
		}

		Collector collector	= _createCollector(collectorName);

		if(null == collector)
		{
			throw new CollectorNotFoundException();
		}

		return collector;
	}

	// PROTECTED ABSTRACT METHODS

	protected abstract Collector _createCollector(String collectorName) throws CollectorNotFoundException;

}
