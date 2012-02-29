/**
 * 
 */
package alpha.com.googlecode.cchlib.utils;

/**
 * TODOC
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
