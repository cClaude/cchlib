/**
 *
 */
package oldies.tools.phone.contacts;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public interface Contact extends Serializable
{
    String getValue( final String propertyName );
    String getValue( final int propertyIndex );

    void setValue( final String propertyName, final String value );
    void setValue( final int propertyIndex, final String value );

    /**
     * Returns {@link ContactProperties} for this contact
     * @return contact properties for this contact
     */
    ContactProperties getContactProperties();

    Collection<String> getValuesForTypeCollection( final ContactValueType type );
}
