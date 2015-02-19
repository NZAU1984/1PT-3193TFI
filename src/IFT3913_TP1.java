import java.net.URL;

import bnf_parser.Parser;
import bnf_parser.RuleAlreadyExistsException;
import bnf_parser.RuleNotFoundException;



public class IFT3913_TP1
{

	public static void main(String[] args)
	{
		IFT3913_TP1 myClass = new IFT3913_TP1();

		URL ligue = myClass.getClass().getResource("Ligue.ucd");

		//System.out.println(ligue.getPath());

		Parser parser = new Parser(ligue.getPath());

		try
		{
			parser.createRule("identifier", parser.matchString_0_1("allo"), parser.matchString("terre"));
		}
		catch (RuleAlreadyExistsException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			boolean res = parser.evaluateRule("identifier");

			System.out.println("Eval " + (res ? "success" : "failed"));
		}
		catch (RuleNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public IFT3913_TP1()
	{

	}

}
