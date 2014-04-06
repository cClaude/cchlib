// $codepro.audit.disable constantNamingConvention
package com.googlecode.cchlib.lang.reflect;

import org.fest.util.VisibleForTesting;



/**
 * The class <code>InvokerByNameFactory</code> implements static methods that return instances of the class <code>{@link InvokerByName}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class InvokerByNameFactory
 {
    private static final Class<MyTestByName>  clazz             = MyTestByName.class;
    @VisibleForTesting static final String    className         = clazz.getName();
    @VisibleForTesting static final String    methodName        = "myTest";
    private static final String               noSuchMethodName  = "noSuchMethodName";

    /** Prevent creation of instances of this colass. */
    private InvokerByNameFactory()
    {
    }

    /**
     * Create an instance of the class <code>{@link InvokerByName}</code>.
     */
    public static InvokerByName<?> createInvokerByName() throws ClassNotFoundException
    {
        return new InvokerByName<Object>( Class.forName(className), methodName);
    }

    /**
     * Create an instance of the class <code>{@link InvokerByName}</code>.
     */
    public static InvokerByName<MyTestByName> createInvokerByClass()
    {
        return new InvokerByName<MyTestByName>( clazz, methodName);
    }

    /**
     * Create an instance of the class <code>{@link InvokerByName}</code>.
     * @throws ClassNotFoundException
     */
    public static InvokerByName<?> createInvokerByName3() throws ClassNotFoundException
    {
        return InvokerByName.forName( className, methodName);
    }

    public static InvokerByName<MyTestByName> createInvokerByName_NoSuchMethodException()
        throws ClassNotFoundException
    {
        return new InvokerByName<MyTestByName>( clazz, noSuchMethodName);
    }
}
