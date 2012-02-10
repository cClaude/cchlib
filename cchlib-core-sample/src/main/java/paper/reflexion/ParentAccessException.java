package paper.reflexion;

/**
 *
 */
public class ParentAccessException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * @param cause
     */
    public ParentAccessException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message
     * @param cause
     */
    public ParentAccessException(
        final String     message,
        final Throwable cause
        )
    {
        super( message, cause );
    }
}
