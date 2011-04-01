package cx.ath.choisnet.util;

import java.util.Collection;

/**
 *
 * @author Claude CHOISNET
 * @param <T> 
 *
 */
public interface CollectionFilter<T>
{
    public abstract Collection<T> apply( Collection<T> collection );
}
