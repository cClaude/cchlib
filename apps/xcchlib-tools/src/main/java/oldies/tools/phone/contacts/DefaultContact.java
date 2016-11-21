package oldies.tools.phone.contacts;

/**
 *
 */
public class DefaultContact extends AbstractContact {
    private static final long serialVersionUID = 1L;
    
    private String[] values;

    /**
     * @param contactProperties
     */
    public DefaultContact( final ContactProperties contactProperties )
    {
        super( contactProperties );

        this.values = new String[contactProperties.size()];
    }

    @Override
    public String getValue( int propertyIndex )
    {
        return values[ propertyIndex ];
    }

    @Override
    public void setValue( final int propertyIndex, final String value )
    {
        values[ propertyIndex ] = value;
    }
}
