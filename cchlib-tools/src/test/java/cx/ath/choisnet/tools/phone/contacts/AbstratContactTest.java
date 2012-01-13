package cx.ath.choisnet.tools.phone.contacts;

import java.util.Collection;
import org.junit.Test;

/**
 *
 */
public class AbstratContactTest
{
    @Test
    public void test1()
    {
        ContactProperties contactProperties = new ContactProperties()
        {

            @Override
            public int size() {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public int getIndex(String valueName) {
                // TODO Auto-generated method stub
                return 0;
            }

            @Override
            public ContactValueType getType(int index) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Collection<? extends String> getDefault() {
                // TODO Auto-generated method stub
                return null;
            }

        };
        AbstratContact ac = new AbstratContact(contactProperties)
        {
            private static final long serialVersionUID = 1L;
        };


    }
    /**
    private static final long serialVersionUID = 1L;
    private final static transient Logger logger = Logger.getLogger( AbstratContactTest.class );
    //public static final String DATE_FORMAT_ISO = "yyyy-MM-dd.HH-mm-ss";

    private LinkedList<String> values;

    protected AbstratContactTest(
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
    }*/
}
