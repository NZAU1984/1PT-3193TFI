package uml_interfaces;

import uml_collectors1.OperationCollector;
import uml_collectors1.OperationListCollector;

/**
 * This is an interface used to link the MVC to a {@link OperationCollector}. It allows to reduce the coupling
 * between the MVC and {@link OperationCollector}.
 *
 * @author Hubert Lemelin
 */
public interface OperationList
{
	/**
	 * Returns an array of all the {@link Operation}'s contained in a {@link OperationListCollector}.
	 *
	 * @return An array of {@link Operation}'s.
	 */
	public Operation[] getOperations();

	/**
	 * Used to simply display the contents of a {@code Dataitem}.
	 *
	 * @return	The object as a String.
	 */
	@Override
	public String toString();
}
