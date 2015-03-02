package mvc.views;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import mvc.Controller;

public class MainWindow	extends JFrame implements Observer
{
	// PROTECTED PROPERTIES

	/**
	 * The controller (hardcoded to save time).
	 */
	protected Controller controller;

	protected JLabel filenameLabel;

	protected JButton selectFileButton;

	protected JButton parseButton;

	protected JList<String> classList;

	protected JList<String> attributeList;

	protected JList <String> subclassList;

	protected JList <String> methodList;

	protected JList<String> assocAggregList;

	protected JLabel detailLabel;

	protected JTextArea detailTextArea;

	protected JFileChooser fileChooser;

	protected File file;


	public MainWindow()
	{
		super("IFT3913 :: TP1 par Hubert Lemelin");

		super.setSize(800, 500);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());

		fileChooser	= new JFileChooser();

		/* Will select the directory in which the bin/src folder reside. */
		file	= new File(System.getProperty("user.dir"));

		filenameLabel	= new JLabel();

		filenameLabel.setPreferredSize(new Dimension(450, 20));
		filenameLabel.setText("");
		filenameLabel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1)));

		selectFileButton	= new JButton("Sélectionner...");

		parseButton	= new JButton("Analyser");

		JPanel filePanel = new JPanel();

		filePanel.add(filenameLabel, BorderLayout.WEST);
		filePanel.add(selectFileButton, BorderLayout.EAST);
		filePanel.setBorder(BorderFactory.createTitledBorder("Sélection de fichier"));

		GridBagConstraints c = new GridBagConstraints();
		c.fill	= GridBagConstraints.BOTH;
		c.weighty = 0.0;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;

		getContentPane().add(filePanel, c);

		JPanel actionPanel	= new JPanel();
		actionPanel.add(parseButton);
		actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

		c.gridy = 1;

		getContentPane().add(actionPanel, c);

		JPanel mainPanel	= new JPanel(new GridBagLayout());

		GridBagConstraints c1	= new GridBagConstraints();

		c1.fill	= GridBagConstraints.BOTH;

		c1.gridx = 0;
		c1.gridy = 0;
		c1.weightx = (float) 1 / 3;
		c1.weighty = 1.0;
		c1.gridheight = 3;

		mainPanel.setBorder(BorderFactory.createTitledBorder("Contenu"));

		JPanel classPanel	= new JPanel(new BorderLayout());
		classPanel.setBorder(BorderFactory.createTitledBorder("Classes"));

		DefaultListModel<String> classes = new DefaultListModel<String>();
		classes.addElement("test");
		classes.addElement("test1");
		classes.addElement("test2");

		classList	= new JList<String>();

		classList.setBorder(BorderFactory.createLoweredBevelBorder());

		classList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		classList.setVisibleRowCount(-1);

		classList.setModel(classes);

		classPanel.add(classList);

		mainPanel.add(classPanel, c1);

		JPanel attributePanel	= new JPanel(new BorderLayout());
		attributePanel.setBorder(BorderFactory.createTitledBorder("Attributs"));

		DefaultListModel<String> attributes = new DefaultListModel<String>();
		attributes.addElement("aaa");
		attributes.addElement("bbb");
		attributes.addElement("ccc");

		attributeList	= new JList<String>();

		attributeList.setBorder(BorderFactory.createLoweredBevelBorder());

		attributeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		attributeList.setVisibleRowCount(-1);

		attributeList.setModel(attributes);

		attributePanel.add(attributeList);

		c1.gridx = 1;
		c1.weighty = (float) 1 / 3;
		c1.gridheight = 1;

		mainPanel.add(attributePanel, c1);

		JPanel subclassPanel	= new JPanel(new BorderLayout());
		subclassPanel.setBorder(BorderFactory.createTitledBorder("Sous-classes"));

		DefaultListModel<String> subclasses = new DefaultListModel<String>();
		subclasses.addElement("111");
		subclasses.addElement("222");
		subclasses.addElement("333");

		subclassList	= new JList<String>();

		subclassList.setBorder(BorderFactory.createLoweredBevelBorder());

		subclassList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		subclassList.setVisibleRowCount(-1);

		subclassList.setModel(subclasses);

		subclassPanel.add(subclassList);

		c1.gridx = 1;
		c1.gridy = 1;

		mainPanel.add(subclassPanel, c1);

		JPanel methodPanel	= new JPanel(new BorderLayout());
		methodPanel.setBorder(BorderFactory.createTitledBorder("Méthodes"));

		DefaultListModel<String> methods = new DefaultListModel<String>();
		methods.addElement("!!!");
		methods.addElement("@@@");
		methods.addElement("###");

		methodList	= new JList<String>();

		methodList.setBorder(BorderFactory.createLoweredBevelBorder());

		methodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		methodList.setVisibleRowCount(-1);

		methodList.setModel(methods);

		methodPanel.add(methodList);

		c1.gridx = 2;
		c1.gridy = 0;

		mainPanel.add(methodPanel, c1);

		JPanel assocAggregPanel	= new JPanel(new BorderLayout());
		assocAggregPanel.setBorder(BorderFactory.createTitledBorder("Aggrégations / Associations"));

		DefaultListModel<String> assocAggregs = new DefaultListModel<String>();
		assocAggregs.addElement("---");
		assocAggregs.addElement("___");
		assocAggregs.addElement("===");

		assocAggregList	= new JList<String>();

		assocAggregList.setBorder(BorderFactory.createLoweredBevelBorder());

		assocAggregList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		assocAggregList.setVisibleRowCount(-1);

		assocAggregList.setModel(assocAggregs);

		assocAggregPanel.add(assocAggregList);

		c1.gridx = 2;
		c1.gridy = 1;

		mainPanel.add(assocAggregPanel, c1);

		JPanel detailPanel	= new JPanel(new BorderLayout());
		detailPanel.setBorder(BorderFactory.createTitledBorder("Détails"));

		detailTextArea	= new JTextArea();

		detailTextArea.setBorder(BorderFactory.createLoweredBevelBorder());

		detailTextArea.setText("... ... ... ... \n ... ... ... \n ... ... ...");

		//detailPanel.add(detailTextArea);

		c1.gridx = 1;
		c1.gridy = 2;
		c1.gridwidth = 2;

		JScrollPane detailPanel1 = new JScrollPane(detailTextArea);

		//detailPanel1.add(detailTextArea);

		detailPanel.add(detailPanel1);

		mainPanel.add(detailPanel, c1);

		c.gridy = 2;
		c.weighty = 1.0;

		getContentPane().add(mainPanel, c);

		selectFileButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				selectFileButtonClicked();
			}
		});

		parseButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parseButtonClicked();
			}
		});

		setVisible(true);
	}

	// PUBLIC METHODS

	public void setController(Controller controller)
	{
		this.controller	= controller;
	}

	public void setFilename(String filename)
	{
		filenameLabel.setText(filename);
	}

	// PROTECTED METHODS

	protected void selectFileButtonClicked()
	{
		if(null != controller)
		{
			controller.selectFileButtonClicked();
		}
	}

	protected void parseButtonClicked()
	{
		if(null != controller)
		{
			controller.parseButtonClicked();
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub

	}
}
