package com.googlecode.cchlib.lang.reflect;



/**
 * The class <code>InvokerByNameFactory</code> implements static methods that return instances of the class <code>{@link InvokerByName}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class InvokerByNameFactory
 {
    private final static Class<MyTestByName>  clazz       = MyTestByName.class;
    final static String                       className   = clazz.getName();
    final static String                       methodName  = "myTest";
    final static String                       noSuchMethodName  = "noSuchMethodName";

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
