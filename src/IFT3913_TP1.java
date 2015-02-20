import java.net.URL;

import uml_collectors.UmlCollectorFactory;
import bnf_parser.Parser;
import bnf_parser.ParsingFailedException;
import bnf_parser.RuleAlreadyExistsException;
import bnf_parser.RuleNotFoundException;
import bnf_parser.collectors.Collector;
import bnf_parser.collectors.CollectorNotFoundException;
import bnf_parser.collectors.StringCollector;



public class IFT3913_TP1
{

	public static void main(String[] args)
	{
		IFT3913_TP1 myClass = new IFT3913_TP1();

		URL ligue = myClass.getClass().getResource("Ligue.ucd");

		//System.out.println(ligue.getPath());

		Parser parser = new Parser(ligue.getPath(), new UmlCollectorFactory());

		try
		{
			parser.createRule("space",		null,				parser.matchPattern("\\s+"));

			parser.createRule("identifier",	"StringCollector",	parser.matchPattern("[A-Za-z_\\-0-9]+"));

			parser.createRule("type",		"StringCollector",	parser.matchRule("identifier"));

			parser.createRule("data_item",	"DataitemCollector",
				parser.matchRule("identifier"),
				parser.matchRule_0_1("space"),
				parser.matchString(":"),
				parser.matchRule_0_1("space"),
				parser.matchRule("type")
			);

			parser.createRule("data_item_list_optional_repeat", "AttributelistCollector",
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

			/*parser.createRule("attribute_list", "AttributelistCollector",
				parser.matchRule_0_1("data_item_list")
			);*/

			/*parser.createRule("arg_declaration", "ArgdeclarationCollector",
				parser.matchString("("),
				parser.matchRule_0_1("space"),
				parser.matchRule_0_1("data_item_list"),
				parser.matchRule_0_1("space"),
				parser.matchString(")")
			);*/

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
				parser.matchRule("type")
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


		if((null != res) && (res instanceof StringCollector))
		{
		StringCollector sc = (StringCollector) res;

		System.out.println("=> " + sc.getString());
		}
	}


	public IFT3913_TP1()
	{

	}

}
