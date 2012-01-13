package cx.ath.choisnet.tools.phone.contacts;

import java.io.Serializable;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/**
 * AbstractContact try to unify all contact's formats
 * 
 */
public abstract class AbstratContact implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final static transient Logger logger = Logger.getLogger( AbstratContact.class );
    //public static final String DATE_FORMAT_ISO = "yyyy-MM-dd.HH-mm-ss";

    private ContactProperties contactProperties;
    private LinkedList<String> values;

    /**
     * 
     * @param contactProperties 
     *
     */
    protected AbstratContact(
    	final ContactProperties contactProperties
    	)
    {
        this.contactProperties = contactProperties;
        this.values = new LinkedList<String>( contactProperties.getDefault() );
        
        // Assert( this.value.size() == this.contactProperties.size() );
    }

    public String getValue( final int index )
    {
    	return this.values.get( index );
    }

    public String getValue( final String valueName )
    {
    	return getValue(
    		this.contactProperties.getIndex( valueName )
    		);
    }

    public void setValue(
    	final int 		index, 
    	final String	value 
    	)
    {
    	this.values.set( index, value );
    }
    
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

    public ContactValueType getType( final int index )
    {
    	return this.contactProperties.getType( index );
    }

    public ContactValueType getType( final String valueName )
    {
    	return getType(
    		this.contactProperties.getIndex( valueName )
    		);
    }
}
