package com.googlecode.cchlib.lang.reflect;

/**
 * The class MethodFilterByNameFactory implements static methods that return
 * instances of the class {@link MethodFilterByName}.
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
     * Create an instance of the class {@link MethodFilterByName}.
     *
     * @generatedBy CodePro at 23/06/13 12:16
     */
    public static MethodFilterByName createMethodFilterByName()
    {
        return new MethodFilterByName( InvokerByNameFactory.methodName );
    }
}