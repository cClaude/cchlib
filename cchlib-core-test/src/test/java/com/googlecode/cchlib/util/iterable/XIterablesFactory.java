package com.googlecode.cchlib.util.iterable;



/**
 * The class <code>XIterablesFactory</code> implements static methods that return instances
 * of the class <code>{@link XIterables}</code>.
 *
 * @version $Revision: 1.0 $
 */
class XIterablesFactory
{
    private XIterablesFactory() {}

    public static XIterable<Integer> createXIterable()
    {
        return new XIterableImpl<Integer>( IterablesTestFactory.createIterable() );
    }
}