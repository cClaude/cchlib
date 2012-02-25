package alpha.com.googlecode.cchlib.utils;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 */
public class NeverFailResourceBundle extends ResourceBundle
{
    /**
     *  @see ResourceBundle
     */
    protected NeverFailResourceBundle( ResourceBundle parentResourceBundle )
    {
        setParent( parentResourceBundle );
    }

    @Override
    protected Object handleGetObject( String key )
    {
        try {
            Object value = super.parent.getObject( key );
            
            if( value != null ) {
                return value;
                }
            }
        catch( MissingResourceException ignore ) {}
        
        System.err.println( key + "=" );
        
        return "##" + key + "##";
    }

    @Override
    public Enumeration<String> getKeys()
    {
        return super.parent.getKeys();
    }

}
