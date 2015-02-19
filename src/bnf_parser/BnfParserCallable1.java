package bnf_parser;

public abstract class BnfParserCallable1
{
	protected Parser parser;
	protected int minOccurences;
	protected int maxOccurences;

	BnfParserCallable1(Parser parser, int minOccurences, int maxOccurences)
	{
		this.parser			= parser;
		this.minOccurences	= minOccurences;
		this.maxOccurences	= maxOccurences;
	}

	abstract boolean parse();
}
