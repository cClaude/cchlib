package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.IntrospectionRuntimeException;

public class SwingIntrospectorNotYetImplementedException
    extends IntrospectionRuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *            message
     */
    public SwingIntrospectorNotYetImplementedException( final String message )
    {
        super( message );
    }

    /**
     * @param message
     *            message
     * @param cause
     *            cause
     */
    public SwingIntrospectorNotYetImplementedException( final String message, final Throwable cause )
    {
        super( message, cause );
    }
}
