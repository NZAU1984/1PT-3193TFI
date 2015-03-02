package uml_collectors1;

import java.util.ArrayList;

import uml_interfaces.Role;
import uml_interfaces.RoleList;
import bnf_parser1.collectors.Collector;

/**
 * This class collects a {@code RoleList} which correspond to <role>{,<role>} (with/without spaces).
 *
 * @author Hubert Lemelin
 */
public class RoleListCollector extends Collector implements RoleList
{
	// PROTECTED PROPERTIES

	/**
	 * List of {@link Role}'s.
	 */
	protected ArrayList<Role>	roleCollectors;

	public RoleListCollector()
	{
		super();

		roleCollectors	= new ArrayList<Role>();
	}

	/**
	 * Expects 0+ {@link RoleCollector}'s and puts each, in order, in a list.
	 *
	 * @see Collector#addChild(Collector, int)
	 */
	@Override
	public void addChild(Collector collector, int index)
	{
		if((null != collector) && (collector instanceof RoleCollector))
		{
			System.out.println("!");
			roleCollectors.add((RoleCollector) collector);
		}
	}

	@Override
	public Role[] getRoles()
	{
		return roleCollectors.toArray(new Role[roleCollectors.size()]);
	}

	@Override
	public String toString()
	{
		StringBuilder sb	= new StringBuilder();

		sb.append("RoleList{");

		if((null != roleCollectors) && (0 < roleCollectors.size()))
		{
			sb.append(roleCollectors.get(0));

			for(int i = 1, iMax = roleCollectors.size(); i < iMax; ++i)
			{
				sb.append(", ").append(roleCollectors.get(i));
			}
		}

		sb.append("}");

		return sb.toString();
	}

}
