package cx.ath.choisnet.util;

/**
 * @deprecated use {@link com.googlecode.cchlib.util.Wrappable} instead
 */
@Deprecated
public interface Wrappable<S,R>
{
    /**
     * Wrap a object to an other one.
     *
     * @param obj object to wrap
     * @return an other view for giving object
     * @throws WrappeException if any error occur while wrapping,
     *         use {@link WrappeException#getCause()} to have
     *         initial error.
     */
    abstract R wrappe(S obj) throws WrappeException;
}
