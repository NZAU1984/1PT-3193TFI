package uml_collectors;

import bnf_parser.collectors.CollectorFactory;

/**
 * This class extends bnf_parser.collectors.CollectorFactory to create custom collectors contained in this package.
 *
 * @author Hubert Lemelin
 */
public class UmlCollectorFactory extends CollectorFactory
{
	// PUBLIC CONSTRUCTOR

	/**
	 * Only calls the super constructor so it can grab its own package name and this package name.
	 */
	public UmlCollectorFactory()
	{
		super();
	}
}
