package cx.ath.choisnet.html;

/**
 * 
 * @author Claude
 *
 */
public class HTMLGadgetNotFoundException 
    extends HTMLFormException
{
    private static final long serialVersionUID = 1L;

    public HTMLGadgetNotFoundException(String message)
    {
        super(message);
    }

    public HTMLGadgetNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
