package bnf_parser.collectors;

public class CollectorNotFoundException extends Exception
{
	public CollectorNotFoundException()
	{
		super();
	}

	public CollectorNotFoundException(String message)
	{
		super(message);
	}
}
