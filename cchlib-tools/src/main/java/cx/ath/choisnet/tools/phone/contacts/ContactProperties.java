package cx.ath.choisnet.tools.phone.contacts;

import java.util.Collection;

/**
 *
 */
public interface ContactProperties
{
	/**
	 * Returns number of properties for this ContactProperties
	 * @return number of properties for this ContactProperties
	 */
	public int size();

	/**
	 * Returns index of a property using property name
	 * @param valueName Name of property
	 * @return index of a property using property name
	 */
	public int getIndex(String valueName);

	/**
	 * Returns type of this properties
	 * @param index Index of the property
	 * @return type of this properties
	 */
	public ContactValueType getType(int index);
	
	/**
	 * Returns defaults values for properties
	 * @return defaults values for properties
	 */
	public Collection<? extends String> getDefault();
}
