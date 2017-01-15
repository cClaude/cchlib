package oldies.tools.phone.contacts;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public interface ContactProperties extends Serializable
{
    /**
     * Returns number of properties for this ContactProperties
     * @return number of properties for this ContactProperties
     */
    int size();

    /**
     * Returns index of a property using property name
     * @param propertyName Name of property
     * @return index of a property using property name
     */
    int getIndex(String propertyName);

    /**
     * Verify if 'index' is value range.
     * @param index Value to check.
     * @throws IllegalArgumentException if value is
     *         out of range
     */
    void checkIndex(int index)
        throws IllegalArgumentException;

    /**
     * Returns name of a property using property index
     * @param index Index of property
     * @return name of a property using property index
     */
    String getName(int index);

    /**
     * Returns type of this properties
     * @param index Index of the property
     * @return type of this properties
     */
    ContactValueType getType(int index);

    /**
     * Returns {@link Collection} of index for this {@link ContactValueType}
     * @param type {@link ContactValueType} to return
     * @return {@link Collection} of index for this {@link ContactValueType}
     */
    Collection<Integer> getTypeCollection(
        final ContactValueType type
        );

    /**
     * Returns default value for this properties
     * @param index Index of the property
     * @return default value of this properties
     */
    String getDefault(int index);

    /**
     * Returns defaults values for properties
     * @return defaults values for properties
     */
    Collection<? extends String> getDefaultCollecion();
}
