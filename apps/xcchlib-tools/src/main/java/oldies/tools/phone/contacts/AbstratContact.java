package oldies.tools.phone.contacts;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * AbstractContact try to unify all contact's formats
 */
public abstract class AbstratContact implements Contact, Serializable
{
    private static final long serialVersionUID = 1L;

    private final ContactProperties contactProperties;
    private final LinkedList<String> values;

    protected AbstratContact(
        final ContactProperties contactProperties
        )
    {
        this.contactProperties = contactProperties;
        this.values = new LinkedList<String>(
                contactProperties.getDefaultCollecion()
                );
    }

    @Override
    public String getValue( final int index )
    {
        return this.values.get( index );
    }

    @Override
    public String getValue( final String valueName )
    {
        return getValue(
            this.contactProperties.getIndex( valueName )
            );
    }

    @Override
    public void setValue(
        final int       index,
        final String    value
        )
    {
        this.contactProperties.checkIndex( index );
        this.values.set( index, value );
    }

    @Override
    public void setValue(
        final String valueName,
        final String value
        )
    {
        setValue(
            this.contactProperties.getIndex( valueName ),
            value
            );
    }

    @Override
    public ContactProperties getContactProperties()
    {
        return this.contactProperties;
    }
}
