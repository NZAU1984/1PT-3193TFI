package bnf_parser1.collectors;

public abstract class Collector
{
	// PROTECTED PROPERTIES

	int offsetInFile;

	protected String collectorName	= "";

	// PUBLIC METHODS

	public void addChild(Collector collector, int index, int offsetInFile)
	{
		this.offsetInFile	= offsetInFile;

		addChild(collector, index);
	}

	// PUBLIC ABSTRACT METHODS

	protected abstract void addChild(Collector collector, int index);

	// PUBLIC METHODS

	public void addString(String string)
	{

	}

	public String identify()
	{
		return collectorName;
	}
}
