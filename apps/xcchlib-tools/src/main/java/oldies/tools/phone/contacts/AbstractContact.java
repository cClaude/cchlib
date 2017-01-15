package oldies.tools.phone.contacts;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractContact implements Contact
{
    private static final long serialVersionUID = 1L;
    protected ContactProperties contactProperties;

    public AbstractContact(
        final ContactProperties contactProperties
        )
    {
        this.contactProperties = contactProperties;
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.Contact#getContactProperties()
     */
    @Override
    public ContactProperties getContactProperties()
    {
        return this.contactProperties;
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.Contact#getValue(java.lang.String)
     */
    @Override
    public String getValue( final String propertyName )
    {
        return getValue( this.contactProperties.getIndex( propertyName ) );
    }

    @Override
    public void setValue(
        final String propertyName,
        final String value
        )
    {
        setValue( this.contactProperties.getIndex( propertyName ), value );
    }

    @Override
    public Collection<String> getValuesForTypeCollection(
        final ContactValueType type
        )
    {
        final Collection<Integer> indexes = this.contactProperties.getTypeCollection( type );
        final ArrayList<String>   vList   = new ArrayList<>();

        for( final Integer index : indexes ) {
            vList.add( getValue( index.intValue() ) );
            }

        return vList;
    }
}
