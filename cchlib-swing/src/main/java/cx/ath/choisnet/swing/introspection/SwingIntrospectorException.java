package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.IntrospectionException;

public class SwingIntrospectorException extends IntrospectionException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message message
     * @param cause cause
     */
    public SwingIntrospectorException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param cause cause
     */
    public SwingIntrospectorException( final Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message message
     */
    public SwingIntrospectorException( final String message )
    {
        super( message );
    }
}
