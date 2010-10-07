/**
 *
 */
package cx.ath.choisnet.lang.introspection.method;

import java.lang.reflect.Method;
import cx.ath.choisnet.lang.introspection.IntrospectionException;

/**
 * @author CC
 *
 */
public class IntrospectionCompareException extends IntrospectionException
{
    private static final long serialVersionUID = 1L;
    /** @serial */
    private int returnCmpValue;
    /** @serial */
    private Method method;

    public IntrospectionCompareException(
            final String messagePrefix,
            final Method m,
            final Object o1,
            final Object o2,
            final int compareValue
            )
    {
        super(
            (messagePrefix == null ? "" : messagePrefix + ' ')
            + "M:" + m.getName() + " o1=(" + o1 + ") o2=(" + o2 + ')'
            );

        this.returnCmpValue = compareValue;
        this.method = m;
    }

    public IntrospectionCompareException(
            final Method m,
            final Object o1,
            final Object o2,
            final int compareValue
            )
    {
        this( null, m, o1, o2, compareValue );
    }

    public int getCompareValue()
    {
        return this.returnCmpValue;
    }

    public Method getMethod()
    {
        return this.method;
    }
}
