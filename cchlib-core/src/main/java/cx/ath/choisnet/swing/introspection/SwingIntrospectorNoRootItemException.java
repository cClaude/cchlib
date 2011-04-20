/**
 * 
 */
package cx.ath.choisnet.swing.introspection;

/**
 * @author CC
 *
 */
public class SwingIntrospectorNoRootItemException extends
        SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     * @param cause
     */
    public SwingIntrospectorNoRootItemException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param cause
     */
    public SwingIntrospectorNoRootItemException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     */
    public SwingIntrospectorNoRootItemException( String message )
    {
        super( message );
    }

}
