package bnf_parser.collectors;

/**
 * This class uses the factory pattern to instantiate "collectors" used by the Parser class to collect matched items
 * (like strings and other collectors). It first tries to instantiate collectors from this package ("basic" creators
 * like StringCollector), then it tries to instantiate collector from the subclass package.
 * <p>
 * Since this class is abstract, it requires a subclass. This subclass must be in the same package as the custom
 * collectors. It only has to have a public constructor and call the super constructor (which is not even mandatory
 * since Java does it by itself it omitted).
 * </p>
 *
 * @author Hubert Lemelin
 *
 */
public abstract class CollectorFactory
{
	// PROTECTED PROPERTIES

	/**
	 * The superclass (this class) package name.
	 */
	protected final String SUPERCLASS_PACKAGE_NAME;

	/**
	 * The subclass (the class extending this (abstract) class) package name.
	 */
	protected final String SUBCLASS_PACKAGE_NAME;

	// PROTECTED CONSTRUCTOR

	/**
	 * Intializes superclass and subclass package names.
	 */
	protected CollectorFactory()
	{
		String superclassPackageName	= null;
		String subclassPackageName		= null;

		try
		{
			/* Trying to get the superclass (CollectorFactory) package name and the subclass (the class extending this
			 * one) package name. */
			superclassPackageName	= this.getClass().getSuperclass().getPackage().getName();
			subclassPackageName	= this.getClass().getPackage().getName();
		}
		catch(Exception e)
		{

		}

		SUPERCLASS_PACKAGE_NAME	= superclassPackageName;
		SUBCLASS_PACKAGE_NAME	= subclassPackageName;
	}

	// PUBLIC METHOD

	/**
	 * Tries to instantiate a collector by using the provided name. It first tries to instantiate the collector from
	 * the base package, then if it fails, it tries to instantiate it from the package containing the superclass
	 * (the one that can be instantiated since this class is abstract).
	 * @param collectorName The name of the collector to instantiate.
	 * @return A new instance of the collector if it was found, null if collectorName is null.
	 * @throws CollectorNotFoundException Thrown when the non-null collectorName could not lead to the instantiation of
	 * a new collector.
	 */
	public Collector createCollector(String collectorName) throws CollectorNotFoundException
	{
		// Some rules don't need a collector, so we allow the null value. It will have to be checked to avoid
		// NullPointerException
		if(null == collectorName)
		{
			return null;
		}

		/* If the superclass package (current package) name is not null, first tries to instantiate the collector from
		 * that package. */
		if(null != SUPERCLASS_PACKAGE_NAME)
		{
			try
			{
				return (Collector) Class.forName(SUPERCLASS_PACKAGE_NAME + "." + collectorName)
					.getConstructor().newInstance();
			}
			catch (Exception e)
			{

			}
		}

		/* If we get here, the previous instantiation failed (very probably a ClassNotFoundException). We'll now try to
		 * instantiate the collector from the subclass package. */
		if(null != SUBCLASS_PACKAGE_NAME)
		{
			try
			{
				return (Collector) Class.forName(SUBCLASS_PACKAGE_NAME + "." + collectorName)
					.getConstructor().newInstance();
			}
			catch (Exception e)
			{

			}
		}

		/* Both instantiations failed, throwing an exceptoin. */
		throw new CollectorNotFoundException();
	}

}
