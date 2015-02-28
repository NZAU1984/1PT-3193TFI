package bnf_parser1;

public class NoSubruleDefinedException extends Exception
{
	public NoSubruleDefinedException()
	{
		super();
	}

	public NoSubruleDefinedException(String message)
	{
		super(message);
	}
}
