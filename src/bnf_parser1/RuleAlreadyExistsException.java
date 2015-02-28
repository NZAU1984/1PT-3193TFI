package bnf_parser1;

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
