package cx.ath.choisnet.tools.phone.contacts;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 *
 */
public class AbstractContactPropertiesBuilder
    implements ContactPropertiesBuilder
{
    private final LinkedList<String> 			nameList	= new LinkedList<>();
    private final LinkedList<ContactValueType> 	typeList	= new LinkedList<>();
    private final LinkedList<String> 			defaultList = new LinkedList<>();

    /**
     *
     */
    public AbstractContactPropertiesBuilder()
    {
    }

    protected void setContactProperty(
        final int 				index,
        final String 			name,
        final ContactValueType	type,
        final String			defaultValue
        )
    {
        if( index < this.nameList.size() ) {
            this.nameList.set( index, name );
            this.typeList.set( index, type );
            this.defaultList.set( index, defaultValue );
            }
        else {
            // Fill and add
            while( index < this.nameList.size() ) {
                this.nameList.add( "Undefine_" + this.nameList.size() );
                this.typeList.add( null );
                this.defaultList.add( "" );
                }
            this.nameList.add( name );
            this.typeList.add( type );
            this.defaultList.add( defaultValue );
            }
    }

    @Override
    public Collection<? extends String> getNames()
    {
        return this.nameList;
    }
    @Override
    public Collection<? extends ContactValueType> getTypes()
    {
        return this.typeList;
    }
    @Override
    public Collection<? extends String> getDefaultValues()
    {
        return this.defaultList;
    }
}
