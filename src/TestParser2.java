import java.io.IOException;
import java.net.URL;

import uml_collectors1.DataitemCollector;
import uml_collectors1.DataitemListCollector;
import bnf_parser1.IncorrectCollectorException;
import bnf_parser1.NoSubruleDefinedException;
import bnf_parser1.Parser;
import bnf_parser1.ParsingFailedException;
import bnf_parser1.Rule;
import bnf_parser1.callables.CallableContainsMoreThanOneCollectorException;
import bnf_parser1.collectors.Collector;
import bnf_parser1.collectors.StringCollector;



public class TestParser2
{
	public TestParser2()
	{
		URL ligue = getClass().getResource("test.txt");

		//System.out.println(ligue.getPath());

		Parser parser	= null;
		try
		{
			parser = new Parser(ligue.getPath(), "UTF-8");

			Rule space	= parser.newRule()
					.matchPatternWithoutCollecting("\\s+", 1, 1);

			Rule identifier	= parser.newRule()
					.matchPattern("[A-Za-z_\\-0-9]+", 1, 1).overrideCollector();

			Rule type	= parser.newRule()
					.matchRule(identifier, 1, 1).overrideCollector();

			/* A dataitem corresponds to <identifier>:<type> (with/without spaces). Since 'identifier' and 'type' both
			 * return a StringCollector, we help differentiate them by setting different indices. */
			Rule dataitem	= parser.newRule().setCollector(DataitemCollector.class)
					.matchRule(space, 0, 1)
					.matchRule(identifier, 1, 1).setIndex(0)
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(":", 1, 1)
					.matchRule(space, 0, 1)
					.matchRule(identifier, 1, 1).setIndex(1)
					.matchRule(space, 0, 1);

			Rule dataitemOptionalRepeat	= parser.newRule().setName("dataitemOptionalRepeat")
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(",", 1, 1)
					.matchRule(dataitem, 1, 1).overrideCollector();

			Rule dataitemList	= parser.newRule().setCollector(DataitemListCollector.class).setName("dataitemlist")
					.matchRule(dataitem, 1, 1)
					.matchRule(dataitemOptionalRepeat, 0, Rule.INFINITY);

			Rule matchAny = parser.newRule()
					.matchPattern("A|B|C", 1, Rule.INFINITY).overrideCollector();

			Rule A = parser.newRule()
					.matchString("A", 1, 1).overrideCollector();

			Rule B = parser.newRule()
					.matchString("B", 1, 1).overrideCollector();

			Rule C = parser.newRule()
					.matchString("C", 1, 1).overrideCollector();

			Rule ABC = parser.newRule().setCollector(StringCollector.class)
					.matchAnyRule(1, Rule.INFINITY, A, B, C);

			// TODO no error if not reaching end of file
			try
			{
				System.out.println("Pré");
				Collector coll = parser.evaluateRule(ABC);

				if(null != coll)
				{
				System.out.println("post " + coll.getStartOffset() + ", " + coll.getEndOffset());
				}
				else
				{
					System.out.println("post... null");
				}

				if(coll instanceof StringCollector)
				{
					System.out.println(((StringCollector) coll).getString());
				}
			}
			catch (ParsingFailedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (CallableContainsMoreThanOneCollectorException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSubruleDefinedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IncorrectCollectorException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
/*
	public <T extends Collector> Collector testClass(Class<T> theClass)
	{
		Class<?> collectorClass = Parser.class;
		System.out.println("??");
		if(Collector.class.isAssignableFrom(collectorClass))
		{
			System.out.println("yes");
		}
		else
		{
			System.out.println("no");
		}

		Collector collector	= null;
		try
		{
			collector	= theClass.getConstructor().newInstance();
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return collector;
	}*/
}
