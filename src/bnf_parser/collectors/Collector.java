package bnf_parser.collectors;

public abstract class Collector
{
	// PROTECTED PROPERTIES

	int offsetInFile;
	String ruleName	= "";

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
}
