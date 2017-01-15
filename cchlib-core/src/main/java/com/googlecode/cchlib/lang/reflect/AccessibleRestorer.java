package com.googlecode.cchlib.lang.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Restore store of an {@link AccessibleObject}
 *
 * @since 4.2
 * @see AccessibleHelper
 */
public class AccessibleRestorer
{
    private final AccessibleObject fieldOrMethod;
    private final boolean isAccessible;

    /**
     * Create an {@link AccessibleRestorer} from a {@link Field} or a {@link Method}
     * an make this object accessible.
     *
     * @param fieldOrMethod
     *            The field or the method.
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
