package bnf_parser;

import bnf_parser.collectors.Collector;


public interface CallableToParserInterface
{
	public Collector evaluateRule(String ruleName);

	// TODO Javadoc
	public String testString(String string);

	/**
	 * Checks if at the current parser's position the provided pattern matches.
	 * @param pattern the pattern to check which must not start with '^'
	 * @return the matched string if success, null otherwise
	 */
	public String testPattern(String pattern);

	/**
	 * Sets the buffer position.
	 * @param position
	 */
	public void setBufferPosition(int position);
}
