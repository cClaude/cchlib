package com.googlecode.cchlib.util.iterable;



/**
 * The classXIterablesFactory implements static methods that return instances
 * of the class {@link XIterables}.
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