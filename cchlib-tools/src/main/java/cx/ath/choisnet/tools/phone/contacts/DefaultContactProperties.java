package cx.ath.choisnet.tools.phone.contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 *
 */
public class DefaultContactProperties
    implements 	ContactProperties,
                Serializable
{
    private static final long serialVersionUID = 1L;

    private final String[] 				names;
    private final ContactValueType []	types;
    private final String[]				defaultValues;
    private final List<String>			defaultValueList;

    /**
     *
     */
    public DefaultContactProperties(
        final ContactPropertiesBuilder builder
        )
    {
        final Collection<? extends String> nameCollection = builder.getNames();
        final int size = nameCollection.size();

        this.names 				= new String[ size ];
        this.types 				= new ContactValueType[ size ];
        this.defaultValues 		= new String[ size ];
        this.defaultValueList 	= new ArrayList<>( size );

        final Iterator<? extends String> 			iterName 	 = nameCollection.iterator();
        final Iterator<? extends ContactValueType> 	iterType 	 = builder.getTypes().iterator();
        final Iterator<? extends String> 			iterDefaults = builder.getDefaultValues().iterator();
        int index = 0;

        while( iterName.hasNext() ) {
            final String 			n = iterName.next();
            final ContactValueType 	t = iterType.next();
            final String 			d = iterDefaults.next();

            this.names[ index ] 		= n;
            this.types[ index ] 		= t;
            this.defaultValues[ index ] = d;
            this.defaultValueList.add( d );
            }
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.ContactProperties#size()
     */
    @Override
    public int size()
    {
        return this.names.length;
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.ContactProperties#getIndex(java.lang.String)
     */
    @Override
    public int getIndex( final String valueName )
    {
        for( int index = 0; index<this.names.length; index++ ) {
            if( valueName.equals( this.names[ index ] ) ) {
                return index;
                }
            }
        return -1; // FIXME exception ?
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.ContactProperties#getType(int)
     */
    @Override
    public ContactValueType getType( int index )
    {
        return this.types[ index ];
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.ContactProperties#getDefault()
     */
    @Override
    public Collection<? extends String> getDefault()
    {
        return Collections.unmodifiableCollection(
            this.defaultValueList
            );
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.phone.contacts.ContactProperties#checkIndex(int)
     */
    @Override
    public void checkIndex( final int index )
        throws IllegalArgumentException
    {
        if( index < 0 || index >= size() ) {
            throw new IllegalArgumentException( "Out of range:" + index );
            }
    }
}
