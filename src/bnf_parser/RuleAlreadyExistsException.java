package bnf_parser;

public class RuleAlreadyExistsException extends Exception
{
	public RuleAlreadyExistsException()
	{
		super();
	}

	public RuleAlreadyExistsException(String message)
	{
		super(message);
	}
}
