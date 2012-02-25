/**
 * 
 */
package alpha.com.googlecode.cchlib.utils;

/**
 * The result type of a {@link Visitor}. 
 * 
 * @author Claude CHOISNET
 */
public enum VisitResult
{
    /**
     * Continue.
     */
    CONTINUE,
    /**
     * Continue without visiting the siblings entry.
     */
    SKIP_SIBLINGS,
    /**
     * Continue without visiting the child entries (if
     * any) in this entry.
     */
    SKIP_SUBTREE,
    /**
     * Terminate.
     */
    TERMINATE
}
