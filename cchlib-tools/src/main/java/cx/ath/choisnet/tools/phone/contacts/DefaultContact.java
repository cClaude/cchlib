package cx.ath.choisnet.tools.phone.contacts;

/**
 *
 */
public class DefaultContact extends AbstractContact
{
    private static final long serialVersionUID = 1L;
    private String[] values;

    /**
     * @param contactProperties
     */
    public DefaultContact(
        final ContactProperties contactProperties
        )
    {
        super( contactProperties );

        this.values = new String[ contactProperties.size() ];
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.Contact#getValue(int)
     */
    @Override
    public String getValue( int propertyIndex )
    {
        return values[ propertyIndex ];
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.Contact#setValue(int, java.lang.String)
     */
    @Override
    public void setValue(
        final int 		propertyIndex,
        final String	value
        )
    {
        values[ propertyIndex ] = value;
    }
}
