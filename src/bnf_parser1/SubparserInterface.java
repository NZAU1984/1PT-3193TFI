package bnf_parser1;

import bnf_parser1.callables.CallableContainsMoreThanOneCollectorException;
import bnf_parser1.collectors.Collector;

public interface SubparserInterface
{
	public Collector evaluateRule(Rule rule) throws ParsingFailedException, CallableContainsMoreThanOneCollectorException;

	public String matchPattern(String pattern);
}
