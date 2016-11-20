/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.lang.introspection;

/**
 * Top level exception for package
 * @author CC
 *
 */
public class IntrospectionException extends Exception 
{
    private static final long serialVersionUID = 1L;

    public IntrospectionException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public IntrospectionException( Throwable cause )
    {
        super( cause );
    }

    public IntrospectionException( String message )
    {
        super( message );
    }
}
