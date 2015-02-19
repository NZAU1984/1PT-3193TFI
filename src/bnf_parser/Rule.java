package bnf_parser;

public class Rule
{
	BnfParserCallable1[] subRules;

	Rule(BnfParserCallable1... args)
	{
		subRules	= args;
	}
}
