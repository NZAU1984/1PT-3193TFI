package mvc;

import java.io.File;

import javax.swing.JFileChooser;

import mvc.views.MainWindow;

public class Controller
{
	// PROTECTED PROPERTIES

	/**
	 * The model (hardcoded to save time).
	 */
	protected Model model;

	/**
	 * The view (hardcoded to save time).
	 */
	protected MainWindow view;

	protected JFileChooser fileChooser;

	protected File file;

	public Controller(Model model, MainWindow view)
	{
		this.model	= model;
		this.view	= view;

		fileChooser	= new JFileChooser();

		/* Will select the directory in which the bin/src folder reside. */
		file	= new File(System.getProperty("user.dir"));
	}

	public void selectFileButtonClicked()
	{
		/* Use previous file's directory (at first, it is the project directory). */
		fileChooser.setCurrentDirectory(file);

		int val	= fileChooser.showOpenDialog(view);

		if(val == JFileChooser.APPROVE_OPTION)
		{
			file = fileChooser.getSelectedFile();

			if(null != file)
			{
				view.setFilename(file.getAbsolutePath());
			}
		}
	}

	public void parseButtonClicked()
	{
		if(null != file)
		{
			System.out.println("??");
			model.analyseFile(file.getAbsolutePath());
		}
	}
}
