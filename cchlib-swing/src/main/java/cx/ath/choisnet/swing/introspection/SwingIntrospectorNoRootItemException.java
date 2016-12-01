package cx.ath.choisnet.swing.introspection;

public class SwingIntrospectorNoRootItemException extends
        SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;

    public SwingIntrospectorNoRootItemException( final String message, final Throwable cause )
    {
        super( message, cause );
    }

    public SwingIntrospectorNoRootItemException( final Throwable cause )
    {
        super( cause );
    }

    public SwingIntrospectorNoRootItemException( final String message )
    {
        super( message );
    }
}
