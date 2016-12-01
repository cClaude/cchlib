package cx.ath.choisnet.swing.introspection;

import java.lang.reflect.Method;

public class SwingIntrospectorIllegalArgumentException
    extends SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private final String methodString;

    /**
     * NEEDDOC
     *
     * @param method NEEDDOC
     * @param value NEEDDOC
     * @param cause NEEDDOC
     */
    public SwingIntrospectorIllegalArgumentException(
            final Method method,
            final Object value,
            final Throwable cause
            )
    {
        super( "Error with value [" + value + "] from " + method, cause );

        this.methodString = method.toString();
    }

    /**
     * Returns related method description
     *
     * @return the {@link Method#toString()}
     */
    public String getMethod()
    {
        return this.methodString;
    }
}
