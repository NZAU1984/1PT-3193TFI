import java.io.IOException;
import java.net.URL;

import uml_parser.collectors.AggregationCollector;
import uml_parser.collectors.AssociationCollector;
import uml_parser.collectors.ClassContentCollector;
import uml_parser.collectors.DataitemCollector;
import uml_parser.collectors.DataitemListCollector;
import uml_parser.collectors.GeneralizationCollector;
import uml_parser.collectors.IdentifierListCollector;
import uml_parser.collectors.ModelCollector;
import uml_parser.collectors.OperationCollector;
import uml_parser.collectors.OperationListCollector;
import uml_parser.collectors.RoleCollector;
import uml_parser.collectors.RoleListCollector;
import bnf_parser.BnfParser;
import bnf_parser.IncorrectCollectorException;
import bnf_parser.NoFileSpecifiedException;
import bnf_parser.NoSubruleDefinedException;
import bnf_parser.ParsingFailedException;
import bnf_parser.Rule;
import bnf_parser.callables.CallableContainsMoreThanOneCollectorException;
import bnf_parser.collectors.Collector;
import bnf_parser.collectors.StringCollector;



public class TestParser2
{
	public TestParser2()
	{
		URL ligue = getClass().getResource("test.txt");

		//System.out.println(ligue.getPath());

		BnfParser bnfParser	= null;
		try
		{
			bnfParser	= new BnfParser();

			bnfParser.open(ligue.getPath(), "UTF-8");

			Rule space	= bnfParser.newRule().setName("space")
					.matchPatternWithoutCollecting("\\s+", 1, 1);

			Rule identifier	= bnfParser.newRule().setName("identifier")
					.matchPattern("[A-Za-z_\\-0-9]+", 1, 1).overrideCollector();

			Rule type	= bnfParser.newRule().setName("multiplicity")
					.matchRule(identifier, 1, 1).overrideCollector();

			/* A dataitem corresponds to <identifier>:<multiplicity> (with/without spaces). Since 'identifier' and 'multiplicity' both
			 * return a StringCollector, we help differentiate them by setting different indices. */
			Rule dataitem	= bnfParser.newRule().setCollector(DataitemCollector.class).setName("dataitem")
					.matchRule(space, 0, 1)
					.matchRule(identifier, 1, 1).setIndex(0)
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(":", 1, 1)
					.matchRule(space, 0, 1)
					.matchRule(identifier, 1, 1).setIndex(1)
					.matchRule(space, 0, 1);

			Rule dataitemOptionalRepeat	= bnfParser.newRule().setName("dataitemOptionalRepeat")
					.matchStringWithoutCollecting(",", 1, 1)
					.matchRule(dataitem, 1, 1).overrideCollector();

			/* This is an alias of 'attribute_list' and 'arg_list' which are the same: [<dataitem>{,<dataitem>}] */
			Rule dataitemList	= bnfParser.newRule().setCollector(DataitemListCollector.class).setName("dataitemlist")
					.matchRule(dataitem, 1, 1)
					.matchRule(dataitemOptionalRepeat, 0, Rule.INFINITY);

			Rule operation	= bnfParser.newRule().setCollector(OperationCollector.class).setName("operation")
					.matchRule(space, 0, 1)
					.matchRule(identifier, 1, 1).setIndex(0)
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting("(", 1, 1)
					.matchRule(dataitemList, 0, 1)
					.matchStringWithoutCollecting(")", 1, 1)
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(":", 1, 1)
					.matchRule(space, 0, 1)
					.matchRule(type, 1, 1).setIndex(1)
					.matchRule(space, 0, 1);

			Rule operationOptionalRepeat	= bnfParser.newRule().setName("operationOptionalRepeat")
					.matchStringWithoutCollecting(",", 1, 1)
					.matchRule(operation, 1, 1).overrideCollector();

			Rule operationList	= bnfParser.newRule().setCollector(OperationListCollector.class).setName("operationList")
					.matchRule(space, 1, 1)
					.matchRule(operation, 1, 1)
					.matchRule(operationOptionalRepeat, 0, Rule.INFINITY);

			Rule operations	= bnfParser.newRule().setName("operations")
					.matchStringWithoutCollecting("OPERATIONS", 1, 1)
					.matchRule(operationList, 0, 1).overrideCollector();

			Rule classContent	= bnfParser.newRule().setCollector(ClassContentCollector.class).setName("classContent")
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting("CLASS", 1, 1)

					/* Forces a space between 'CLASS' and <identifier>, otherwise both could be "stuck" together which
					 * would not be very logical. */
					.matchRule(space, 1, 1)
					.matchRule(identifier, 1, 1)

					/* Same as comment above. */
					.matchRule(space, 1, 1)
					.matchStringWithoutCollecting("ATTRIBUTES", 1, 1)

					/* Let's force a space between 'ATTRIBUTES' and [<identifier> of a possible <dataitem> for the list
					 * of attributes if it exists OR 'OPERATIONS' if no attributes exist]. Otherwise it would allow
					 * something like 'ATTRIBUTESnom_equipe:String' or 'ATTRIBUTESOPERATIONS'. */
					.matchRule(space, 1, 1)

					.matchRule(dataitemList, 0, 1)
					.matchStringWithoutCollecting("OPERATIONS", 1, 1)

					.matchRule(operationList, 0, 1)

					/* If there was no operationList, there might be a space before ";", that's why we check for it. */
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(";", 1, 1)
					.matchRule(space, 0, 1);

			/* ASSOCIATION */

			Rule multiplicity	= bnfParser.newRule().setName("multiplicity")
					.matchPattern("ONE_OR_MANY|ONE|MANY|OPTIONALLY_ONE|UNDEFINED", 1, 1).overrideCollector();

			Rule role	= bnfParser.newRule().setCollector(RoleCollector.class).setName("role")
					.matchStringWithoutCollecting("CLASS", 1, 1)
					.matchRule(space, 1, 1)
					.matchRule(identifier, 1, 1).setIndex(0)
					.matchRule(space, 1, 1)
					.matchRule(multiplicity, 1, 1).setIndex(1);

			Rule association	= bnfParser.newRule().setCollector(AssociationCollector.class).setName("association")
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting("RELATION", 1, 1)
					.matchRule(space, 1, 1)
					.matchRule(identifier, 1,  1)
					.matchRule(space, 1, 1)
					.matchStringWithoutCollecting("ROLES", 1, 1)
					.matchRule(space, 1, 1)
					.matchRule(role, 1, 1).setIndex(0)
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(",", 1, 1)
					.matchRule(space,  0,  1)
					.matchRule(role, 1, 1).setIndex(1)
					.matchRule(space,  0, 1)
					.matchStringWithoutCollecting(";", 1, 1)
					.matchRule(space, 0, 1);

			/* GENERALIZATION */

			Rule identifierOptionalRepeat	= bnfParser.newRule().setName("identifierOptionalRepeat")
					.matchStringWithoutCollecting(",", 1, 1)
					.matchRule(space, 0, 1)
					.matchRule(identifier, 1, 1).overrideCollector()
					.matchRule(space, 0, 1);

			Rule identifierList	= bnfParser.newRule().setCollector(IdentifierListCollector.class).setName("identifierList")
					.matchRule(space, 0, 1)
					.matchRule(identifier, 1, 1)
					.matchRule(identifierOptionalRepeat, 0, Rule.INFINITY);

			Rule generalization	= bnfParser.newRule().setCollector(GeneralizationCollector.class).setName("generalization")
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting("GENERALIZATION", 1, 1)
					.matchRule(space,  1, 1)
					.matchRule(identifier, 1, 1)
					.matchRule(space, 1, 1)
					.matchStringWithoutCollecting("SUBCLASSES", 1, 1)
					.matchRule(space, 1, 1)
					.matchRule(identifierList, 1, 1)
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(";", 1, 1)
					.matchRule(space, 0, 1);

			/* AGGREGATION */

			Rule roleOptionalRepeat	= bnfParser.newRule().setName("roleOptionalRepeat")
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(",", 1, 1)
					.matchRule(space, 0, 1)
					.matchRule(role, 1, 1).overrideCollector();

			Rule roleList	= bnfParser.newRule().setCollector(RoleListCollector.class).setName("roleList")
					.matchRule(role, 1, 1)
					.matchRule(roleOptionalRepeat, 0, Rule.INFINITY);

			Rule aggregation	= bnfParser.newRule().setCollector(AggregationCollector.class).setName("aggregation")
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting("AGGREGATION", 1, 1)
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting("CONTAINER", 1, 1)
					.matchRule(space, 1, 1)
					.matchRule(role, 1,  1)
					.matchRule(space, 1, 1)
					.matchStringWithoutCollecting("PARTS", 1, 1)
					.matchRule(space, 1, 1)
					.matchRule(roleList, 1, 1)
					.matchRule(space, 0, 1)
					.matchStringWithoutCollecting(";", 1, 1)
					.matchRule(space, 0, 1);

			/* MODEL */

			Rule model	= bnfParser.newRule().setCollector(ModelCollector.class)
					.matchStringWithoutCollecting("MODEL", 1, 1)
					.matchRule(space, 1, 1)
					.matchRule(identifier, 1, 1)
					.matchRule(space, 1, 1)
					.matchAnyRule(1, Rule.INFINITY, classContent, association, generalization, aggregation);

			// TODO no error if not reaching end of file
			try
			{
				System.out.println("Pré");
				Collector coll = bnfParser.evaluateRule(model);

				//System.out.println(coll);

				if(null != coll)
				{
				System.out.println("post " + coll.getStartOffset() + ", " + coll.getEndOffset());
				System.out.println(coll);
				}
				else
				{
					System.out.println("post... null");
				}

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
			catch (NoFileSpecifiedException e)
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
}
