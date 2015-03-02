import java.io.IOException;
import java.net.URL;

import uml_parser.Model;
import uml_parser.ParsingFailedException;
import uml_parser.UmlParser;



public class IFT3913_TP1
{

	public static void main(String[] args) throws IOException
	{
		IFT3913_TP1 myClass = new IFT3913_TP1();

		URL ligue = myClass.getClass().getResource("test.txt");

		UmlParser up = UmlParser.getInstance();

		try
		{
			Model model = up.parse(ligue.getPath(), UmlParser.LATIN_1_ENCODING);

			System.out.println(model);
		}
		catch (ParsingFailedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public IFT3913_TP1()
	{

	}

}
