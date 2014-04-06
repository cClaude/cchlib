package com.googlecode.cchlib.lang.reflect;

/**
 * The class <code>MethodFilterByNameFactory</code> implements static methods that return instances of the class <code>{@link MethodFilterByName}</code>.
 *
 * @version $Revision: 1.0 $
 */
public class MethodFilterByNameFactory
 {
    /** Prevent creation of instances of this class. */
    private MethodFilterByNameFactory()
    {
    }

    /**
     * Create an instance of the class <code>{@link MethodFilterByName}</code>.
     *
     * @generatedBy CodePro at 23/06/13 12:16
     */
    public static MethodFilterByName createMethodFilterByName()
    {
        return new MethodFilterByName( InvokerByNameFactory.methodName );
    }
}