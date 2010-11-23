/**
 * 
 */
package alpha.cx.ath.choisnet.utils;

/**
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 * @param <T> 
 *
 */
public interface Walkable<T>
{
    /**
     * TODO: Doc!
     * 
     * @param visitor
     */
    public void walk(Visitor<T> visitor);
}
