package com.googlecode.cchlib.util.populator;

/**
 * Offer some implementation of {@link PersistentResolverFactory}
 *
 *  @since 4.2
 */
public class PersistentResolverFactories
{
    private PersistentResolverFactories()
    {
        // All static
    }

    /**
     * Returns a {@link PersistentResolverFactory} based on {@link DefaultPersistentResolver}
     *
     * @return a {@link PersistentResolverFactory}
     */
    public static PersistentResolverFactory newDefaultPersistentResolverFactory()
    {
        return DefaultPersistentResolver::new;
    }


    /**
     * Returns a {@link PersistentResolverFactory} without any resolver.
     * <p>
     * Could be use if {@link Persistent} annotation is not use.
     *
     * @return a {@link PersistentResolverFactory}
     */
    public static PersistentResolverFactory newNonePersistentResolverFactory()
    {
        return () -> new PersistentResolver(){ /* empty */ };
    }
}
