package oldies.tools.phone.contacts;

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
     * @param propertyName Name of property
     * @return index of a property using property name
     */
    public int getIndex(String propertyName);

    /**
     * Verify if 'index' is value range.
     * @param index Value to check.
     * @throws IllegalArgumentException if value is
     *         out of range
     */
    public void checkIndex(int index)
        throws IllegalArgumentException;

    /**
     * Returns name of a property using property index
     * @param index Index of property
     * @return name of a property using property index
     */
    public String getName(int index);

    /**
     * Returns type of this properties
     * @param index Index of the property
     * @return type of this properties
     */
    public ContactValueType getType(int index);

    /**
     * Returns {@link Collection} of index for this {@link ContactValueType}
     * @param type {@link ContactValueType} to return
     * @return {@link Collection} of index for this {@link ContactValueType}
     */
    public Collection<Integer> getTypeCollection(
        final ContactValueType type
        );

    /**
     * Returns default value for this properties
     * @param index Index of the property
     * @return default value of this properties
     */
    public String getDefault(int index);

    /**
     * Returns defaults values for properties
     * @return defaults values for properties
     */
    public Collection<? extends String> getDefaultCollecion();
}
