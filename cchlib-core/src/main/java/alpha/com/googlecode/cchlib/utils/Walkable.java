package alpha.com.googlecode.cchlib.utils;

/**
 * TODOC
 *
 */
public interface Walkable<T>
{
    /**
     * TODOC
     *
     * @param visitor
     */
    public void walk(Visitor<T> visitor);
}
