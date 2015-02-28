package bnf_parser1;

public class ParsingFailedException extends Exception
{
	public ParsingFailedException()
	{
		super();
	}

	public ParsingFailedException(String message)
	{
		super(message);
	}
}
