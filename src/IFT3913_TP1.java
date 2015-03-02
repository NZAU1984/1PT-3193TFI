import java.io.IOException;

import mvc.Controller;
import mvc.Model;
import mvc.views.MainWindow;



public class IFT3913_TP1
{

	public static void main(String[] args) throws IOException
	{
/*		IFT3913_TP1 myClass = new IFT3913_TP1();

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
		}*/

		MainWindow view = new MainWindow();

		Model model	= new Model();

		Controller controller	= new Controller(model, view);

		view.setController(controller);
	}


	public IFT3913_TP1()
	{

	}

}
