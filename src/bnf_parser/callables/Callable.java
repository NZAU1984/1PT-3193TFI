package bnf_parser.callables;

import java.util.LinkedList;

import bnf_parser.Parser;
import bnf_parser.collectors.Collector;

public abstract class Callable
{
	protected Parser parser;
	protected int minOccurences;
	protected int maxOccurences;
	protected LinkedList<Collector> collectors;

	// PROTECTED CONSTRUCTOR

	protected Callable(Parser parser, int minOccurences, int maxOccurences)
	{
		this.parser			= parser;
		this.minOccurences	= minOccurences;
		this.maxOccurences	= maxOccurences;

		resetCollectors();
	}

	// PUBLIC METHODS

	public Collector getCollector() throws CallableContainsMoreThanOneCollectorException
	{
		if(1 < collectors.size())
		{
			throw new CallableContainsMoreThanOneCollectorException();
		}

		if(0 == collectors.size())
		{
			return null;
		}

		return collectors.getFirst();
	}

	public Collector[] getCollectors()
	{
		return collectors.toArray(new Collector[collectors.size()]);
	}

	// PUBLIC ABSTRACT METHODS

	public abstract boolean parse();

	// PROTECTED METHODS

	protected void resetCollectors()
	{
		collectors	= new LinkedList<Collector>();
	}

	protected void addCollector(Collector collector)
	{
		collectors.add(collector);
	}
}