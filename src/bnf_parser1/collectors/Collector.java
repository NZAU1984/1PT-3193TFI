package bnf_parser1.collectors;

public abstract class Collector
{
	// PROTECTED PROPERTIES

	int offsetInFile;

	protected String ruleName	= "";

	protected String collectorName	= "";

	// PUBLIC METHODS

	public void addChild(String ruleName, Collector collector, int offsetInFile)
	{
		this.offsetInFile	= offsetInFile;

		addChild(ruleName, collector);
	}

	// PUBLIC ABSTRACT METHODS

	protected abstract void addChild(String ruleName, Collector collector);

	// PUBLIC METHODS

	public void setRuleName(String ruleName)
	{
		this.ruleName = ruleName;
	}

	public String getRuleName()
	{
		return ruleName;
	}

	public void addString(String string)
	{

	}

	public String identify()
	{
		return collectorName;
	}
}
