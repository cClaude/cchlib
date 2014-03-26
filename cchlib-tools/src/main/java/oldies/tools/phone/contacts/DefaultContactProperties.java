package oldies.tools.phone.contacts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 *
 *
 */
public class DefaultContactProperties
    implements     ContactProperties,
                Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DefaultContactProperties.class );

    private final String[]              names;
    private final ContactValueType[]    types;
    private final String[]              defaultValues;
    private final List<String>          defaultValueList;

    private Map<ContactValueType,Collection<Integer>> typeIndexMap = new HashMap<>();

    /**
     *
     */
    public DefaultContactProperties(
        final ContactPropertiesBuilder builder
        )
    {
        final Collection<? extends String> nameCollection = builder.getNames();
        final int size = nameCollection.size();

        this.names                = new String[ size ];
        this.types                = new ContactValueType[ size ];
        this.defaultValues        = new String[ size ];
        this.defaultValueList     = new ArrayList<>( size );

        final Iterator<? extends String>             iterName      = nameCollection.iterator();
        final Iterator<? extends ContactValueType>     iterType      = builder.getTypes().iterator();
        final Iterator<? extends String>             iterDefaults = builder.getDefaultValues().iterator();
        int index = 0;

        while( iterName.hasNext() ) {
            final String             name = iterName.next();
            final ContactValueType   type = iterType.next();
            final String             def  = iterDefaults.next();

            LOGGER.info( "Prop[" + index + "]=\"" + name + "\" (" + type + ")/\"" + def + "\"" );

            this.names[ index ]         = name;
            this.types[ index ]         = type;
            this.defaultValues[ index ] = def;
            this.defaultValueList.add( def );
            index++;
            }

        LOGGER.info( "Number of properties: " + names.length );
    }

    @Override
    public int size()
    {
        return this.names.length;
    }

    @Override
    public String getName( int index )
    {
        checkIndex( index );
        return this.names[ index ];
    }

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

    @Override
    public ContactValueType getType( int index )
    {
        checkIndex( index );
        return this.types[ index ];
    }

    @Override
    public String getDefault( int index )
    {
        checkIndex( index );
        return this.defaultValues[ index ];
    }

    @Override
    public Collection<? extends String> getDefaultCollecion()
    {
        return Collections.unmodifiableCollection(
            this.defaultValueList
            );
    }

    @Override
    public void checkIndex( final int index )
        throws IllegalArgumentException
    {
        if( index < 0 || index >= size() ) {
            throw new IllegalArgumentException( "Out of range:" + index );
            }
    }

    @Override
    public Collection<Integer> getTypeCollection(
        final ContactValueType type
        )
    {
        Collection<Integer> c = this.typeIndexMap.get( type );

        if( c == null ) {
            c = new ArrayList<>();

            // Compute value
            for( int i = 0; i<this.types.length; i++ ) {
                if( this.types[ i ].equals( type) ) {
                    c.add( i );
                    }
                }

            // Store computed value
            this.typeIndexMap.put( type, c );
            }

        return null;
    }
}
