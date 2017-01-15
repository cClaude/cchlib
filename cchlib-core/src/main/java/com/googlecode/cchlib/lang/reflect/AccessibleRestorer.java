package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Restore store of an {@link AccessibleObject}
 *
 * @since 4.2
 */
public class AccessibleRestorer
{
    private final AccessibleObject fieldOrMethod;
    private final boolean          isAccessible;

    /**
     * Create an {@link AccessibleRestorer} from a {@link Field} or a {@link Method}
     * an make this object accessible.
     * <p>
     * {@link #restore()} should be invoke to restore initial state of
     * {@code fieldOrMethod}
     *
     * @param fieldOrMethod
     *            The field or the method (any {@link AccessibleObject} is
     *            a valid value, but most of the time you want to use this
     *            object for a {@link Field} or for a {@link Method}.
     *
     * @throw SecurityException if the access request is denied.
     */
    public AccessibleRestorer( final AccessibleObject fieldOrMethod )
    {
        this.fieldOrMethod = fieldOrMethod;
        this.isAccessible  = fieldOrMethod.isAccessible();

        if( ! this.isAccessible ) {
            fieldOrMethod.setAccessible( true );
        }
    }

    /**
     * Restore initial state.
     *
     * @throw SecurityException if the access request is denied.
     */
    public void restore()
    {
        if( ! this.isAccessible ) {
            this.fieldOrMethod.setAccessible( false );
        }
    }
}
