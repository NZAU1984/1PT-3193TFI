package bnf_parser1.callables;

import java.util.LinkedList;

import bnf_parser1.Parser;
import bnf_parser1.collectors.Collector;

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
		// TODO remove until...
		if(collectors.size() > 1)
		{
			StringBuilder sb1 = new StringBuilder();
			sb1.append("/// ").append(this.getClass().getSimpleName()).append(" >> ").append(collectors.size()).append(" collectors:");
			for(int i = 0; i < collectors.size(); ++i)
			{
				sb1.append(" ").append(collectors.get(i).identify()).append(",");
			}
			sb1.append(" ///");

			System.out.println(sb1.toString());

		}
		// TODO ... here

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