package bnf_parser;

import java.util.LinkedList;

import bnf_parser.collectors.Collector;



public abstract class Callable
{
	protected Parser parser;
	protected int minOccurences;
	protected int maxOccurences;
	protected String ruleName;
	protected Collector collector;
	protected LinkedList<Collector> collectors;

	// PACKAGE CONSTRUCTOR
	Callable(Parser parser, int minOccurences, int maxOccurences)
	{
		this.parser			= parser;
		this.minOccurences	= minOccurences;
		this.maxOccurences	= maxOccurences;
		//this.ruleName		= ruleName;

		resetCollectors();
	}

	// PUBLIC METHODS

	public void resetCollectors()
	{
		collectors	= new LinkedList<Collector>();
	}

	public Collector getCollector()
	{
		return collector;
	}

	public Collector[] getCollectors()
	{
		return collectors.toArray(new Collector[collectors.size()]);
	}

	// PACKAGE ABSTRACT METHODS

	abstract boolean parse();

	// PROTECTED METHODS

	protected void addCollector(Collector collector)
	{
		collectors.add(collector);
	}
}
