/**
 *
 */
package cx.ath.choisnet.tools.phone.contacts;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
public interface Contact extends Serializable
{
    public String getValue( final String propertyName );
    public String getValue( final int propertyIndex );

    public void setValue( final String propertyName, final String value );
    public void setValue( final int propertyIndex, final String value );

    /**
     * Returns {@link ContactProperties} for this contact
     * @return contact properties for this contact
     */
    public ContactProperties getContactProperties();

    public Collection<String> getValuesForTypeCollection( final ContactValueType type );
}
