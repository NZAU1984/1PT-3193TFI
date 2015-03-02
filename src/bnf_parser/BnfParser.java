package bnf_parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bnf_parser.callables.Callable;
import bnf_parser.callables.CallableContainsMoreThanOneCollectorException;
import bnf_parser.collectors.Collector;


public class BnfParser
{
	// PROTECTED PROPERTIES

	protected Subparser subparser;

	// PUBLIC CONSTRUCTORS

	public BnfParser()
	{
		subparser	= new Subparser();
	}

	// PUBLIC METHODS

	public void open(String filename, String charset) throws IOException
	{
		subparser.open(filename, charset);
	}

	public void close()
	{
		subparser.close();
	}

	public Rule newRule()
	{
		return new Rule();
	}

	// TODO redo Javadoc
	/**
	 * Evaluates the specified rule. In case of success, returns a new collector whose multiplicity was specified when creating
	 * the rule, including null if none was specified. Throws a 'ParsingFailedException' if any part of the rule
	 * fails parsing.
	 * @param ruleName The name of the rule to be evaluated.
	 * @return
	 * @throws ParsingFailedException
	 * @throws CallableContainsMoreThanOneCollectorException
	 * @throws NoFileSpecifiedException
	 */
	public Collector evaluateRule(Rule rule)
			throws ParsingFailedException, CallableContainsMoreThanOneCollectorException, NoFileSpecifiedException
	{
		return subparser.evaluateRule(rule);
	}

	protected class Subparser implements SubparserInterface
	{
		// PROTECTED PROPERTIES

		protected FileInputStream fileInputStream;

		protected CharBuffer charBuffer;

		protected int charBufferPosition	= 0;

		// PUBLIC CONSTRUCTORS

		public Subparser()
		{

		}

		public Subparser(String filename, String charset) throws IOException
		{
			open(filename, charset);
		}

		// PUBLIC METHODS

		public void open(String filename, String charset) throws IOException
		{
			// http://www.java-tips.org/java-se-tips/java.util.regex/how-to-apply-regular-expressions-on-the-contents-of-a.html

			fileInputStream			= new FileInputStream(filename);
	        FileChannel fileChannel	= fileInputStream.getChannel();
	        ByteBuffer byteBuffer	= fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, (int) fileChannel.size());
	        charBuffer				= Charset.forName(charset).newDecoder().decode(byteBuffer);
		}

		public void close()
		{
			try
			{
				fileInputStream.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			finally
			{
				fileInputStream	= null;
				charBuffer		= null;
			}
		}

		@Override
		public Collector evaluateRule(Rule rule)
				throws ParsingFailedException, CallableContainsMoreThanOneCollectorException, NoFileSpecifiedException
		{
			if((null == fileInputStream) || (null == charBuffer))
			{
				throw new NoFileSpecifiedException();
			}

			/* Creating the rule's collector. It might be 'null' (for example, a simple pattern matching rule may only have
			 * to return true if the pattern matched, but the matched string is not important. As an example, creating a
			 * rule to match spaces often doesn't have to 'collectString' the matched spaces. If a callable's collector will
			 * override the rule's collector, the collector, here, will be null as it doesn't make any sense to create a
			 * collector to later replace it by another. */
			Collector collector = rule.createCollector();

			int startOffset	= getBufferPosition();

			rule.resetIterator();

			int bufferPositionBeforeParsing = getBufferPosition();

			while(rule.hasNext())
			{
				Callable callable	= rule.next();

				if(!callable.parse(this))
				{
					setBufferPosition(bufferPositionBeforeParsing);

					throw new ParsingFailedException();
				}

				if(rule.doOverrideCollector())
				{
					collector = callable.getCollector();
				}
				else if(rule.noCollectorOverriding() && (null != collector))
				{
					for(Collector callableCollector	: callable.getCollectors())
					{
						collector.addChild(callableCollector, rule.getIndex());
					}
				}
			}

			if(null != collector)
			{
				collector.setOffsets(startOffset, getBufferPosition());
			}

			return collector;
		}

		/**
		 * Checks if at the current parser's position the provided pattern matches.
		 * @param pattern the pattern to check which must not start with '^'
		 * @return the matched string if success, null otherwise
		 */
		@Override
		public String matchPattern(String pattern)
		{
			Pattern pattern1	= Pattern.compile("(^" + pattern + ")", Pattern.DOTALL);
			Matcher matcher		= pattern1.matcher(charBuffer);

			/* There is only one group that can be found, the one contained in the parentheses above. */
			if(matcher.find())
			{
				String group = matcher.group();

				/* Moving the buffer's position after the found string. */
				setBufferPosition(charBufferPosition + group.length());

				return group;
			}

			/* Pattern did not match. */
			return null;
		}

		// PROTECTED METHODS

		/**
		 * Returns the buffer's current position.
		 * @return the buffer's current position
		 */
		protected int getBufferPosition()
		{
			return charBufferPosition;
		}

		/**
		 * Sets the buffer position.
		 * @param position
		 */
		protected void setBufferPosition(int position)
		{
			charBufferPosition	= position;

			charBuffer.position(charBufferPosition);
		}
	}
}
