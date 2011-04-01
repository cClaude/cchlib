/**
 * 
 */
package alpha.cx.ath.choisnet.utils.tree;

/**
 * Indicate an error during insertion of an item
 * into {@link NamedTree} if root name of new node
 * is not equal to root node name of current tree.
 * 
 * @author Claude CHOISNET
 */
public class BadRootNameException extends NamedTreeException 
{
    private static final long serialVersionUID = 1L;
    
    public BadRootNameException()
    {
    }

    public BadRootNameException( String message )
    {
        super( message );
    }

    public BadRootNameException( Throwable cause )
    {
        super( cause );
    }

    public BadRootNameException( String message, Throwable cause )
    {
        super( message, cause );
    }
}
