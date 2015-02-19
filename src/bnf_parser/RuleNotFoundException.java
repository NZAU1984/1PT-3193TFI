package bnf_parser;

public class RuleNotFoundException extends Exception
{
	public RuleNotFoundException()
	{
		super();
	}

	public RuleNotFoundException(String message)
	{
		super(message);
	}
}
