import java.io.IOException;
import java.net.URL;

import uml_collectors.UmlCollectorFactory;
import bnf_parser.Parser;
import bnf_parser.ParsingFailedException;
import bnf_parser.RuleAlreadyExistsException;
import bnf_parser.RuleNotFoundException;
import bnf_parser.callables.CallableContainsMoreThanOneCollectorException;
import bnf_parser.collectors.Collector;
import bnf_parser.collectors.CollectorNotFoundException;
import bnf_parser.collectors.StringCollector;



public class IFT3913_TP1
{

	public static void main(String[] args) throws IOException
	{
		IFT3913_TP1 myClass = new IFT3913_TP1();

		URL ligue = myClass.getClass().getResource("test.txt");

		//System.out.println(ligue.getPath());

		Parser parser = new Parser(ligue.getPath(), "UTF-8", new UmlCollectorFactory());

		int iii = 2;

		TestParser2 tp2 = new TestParser2();

		if(2 == iii)
		{
			return;
		}
/*		try
		{
			Rule spaceRule	= parser.createRule1("space").matchPattern("\\s+", 0, 1).build();
		}
		catch (RuleAlreadyExistsException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		try
		{

			parser.createRule("space",
				parser.matchPattern("\\s+"));

			parser.createRule("identifier",	0,
				parser.matchPattern("[A-Za-z_\\-0-9]+"));

			parser.createRule("multiplicity", 0,
				parser.matchRule("identifier"));

			parser.createRule("data_item", "DataitemCollector",
				parser.matchRule("identifier"),
				parser.matchRule_0_1("space"),
				parser.matchString(":"),
				parser.matchRule_0_1("space"),
				parser.matchRule("multiplicity")
			);

			parser.createRule("data_item_list_optional_repeat", 2,
				parser.matchString(","),
				parser.matchRule_0_1("space"),
				parser.matchRule("data_item"),
				parser.matchRule_0_1("space")
			);

			parser.createRule("data_item_list", "AttributelistCollector",
				parser.matchRule("data_item"),
				parser.matchRule_0_1("space"),
				parser.matchRule_0_p("data_item_list_optional_repeat")
			);

			parser.createRule("operation", "OperationCollector",
				parser.matchRule("identifier"),
				parser.matchRule_0_1("space"),
				parser.matchString("("),
				parser.matchRule_0_1("space"),
				parser.matchRule_0_1("data_item_list"),
				parser.matchRule_0_1("space"),
				parser.matchString(")"),
				parser.matchRule_0_1("space"),
				parser.matchString(":"),
				parser.matchRule_0_1("space"),
				parser.matchRule("multiplicity")
			);

			parser.createRule("operation_list_optional_repeat", 2,
				parser.matchString(","),
				parser.matchRule_0_1("space"),
				parser.matchRule("operation"),
				parser.matchRule_0_1("space")
			);


		}
		catch (RuleAlreadyExistsException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collector res = null;

		try
		{
			res = parser.evaluateRule("operation");
		}
		catch (CollectorNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParsingFailedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (RuleNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (CallableContainsMoreThanOneCollectorException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if((null != res) && (res instanceof StringCollector))
		{
		StringCollector sc = (StringCollector) res;

		System.out.println("=> " + sc.getString());
		}

		System.out.println("position = " + parser.getBufferPosition());

		parser.close();
	}


	public IFT3913_TP1()
	{

	}

}
