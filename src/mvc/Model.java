package mvc;

import java.io.IOException;

import uml_parser.ParsingFailedException;
import uml_parser.UmlParser;


public class Model
{
	// PROPERTEC PROPERTIES

	/**
	 * The {@link UmlParser} used to analyse the file provided by the user.
	 */
	protected UmlParser umlParser;

	// PUBLIC CONSTRUCTOR

	public Model()
	{
		umlParser	= UmlParser.getInstance();
	}

	public void analyseFile(String filename)
	{
		try
		{
			umlParser.parse(filename, UmlParser.UTF8_ENCODING);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParsingFailedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
