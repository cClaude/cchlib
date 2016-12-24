package oldies.tools.phone.contacts;

public class DefaultContact extends AbstractContact
{
    private static final long serialVersionUID = 1L;

    private final String[] values;

    public DefaultContact( final ContactProperties contactProperties )
    {
        super( contactProperties );

        this.values = new String[contactProperties.size()];
    }

    @Override
    public String getValue( final int propertyIndex )
    {
        return this.values[ propertyIndex ];
    }

    @Override
    public void setValue( final int propertyIndex, final String value )
    {
        this.values[ propertyIndex ] = value;
    }
}
