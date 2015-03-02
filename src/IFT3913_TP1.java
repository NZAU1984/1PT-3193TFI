import java.io.IOException;
import java.net.URL;



public class IFT3913_TP1
{

	public static void main(String[] args) throws IOException
	{
		IFT3913_TP1 myClass = new IFT3913_TP1();

		URL ligue = myClass.getClass().getResource("test.txt");

		//System.out.println(ligue.getPath());

		TestParser2 tp2 = new TestParser2();
	}


	public IFT3913_TP1()
	{

	}

}
