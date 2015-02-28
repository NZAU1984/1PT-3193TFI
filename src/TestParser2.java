import java.io.IOException;
import java.net.URL;

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

			Rule space	= parser.newRule().matchPattern("\\s+", 1, 1);
			Rule A		= parser.newRule().matchString("A", 1, 1).setCollector(StringCollector.class);
			Rule spaceAspace = parser.newRule()
					.matchRule(space, 1, 1)
					.matchRule(A, 1, 1).overrideCollector()
					.matchRule(space, 0, 1);
			// TODO no error if not reaching end of file
			try
			{
				Collector coll = parser.evaluateRule(spaceAspace);

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
