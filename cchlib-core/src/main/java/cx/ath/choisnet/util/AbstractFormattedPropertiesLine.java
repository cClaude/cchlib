package cx.ath.choisnet.util;

import java.io.Serializable;

/**
 * Line structure
 */
/*public*/
abstract class AbstractFormattedPropertiesLine implements FormattedPropertiesLine, Serializable
{
    private static final long serialVersionUID = 1L;

    private final int lineNumber;
    /**
     * Full comment line or key if not a comment
     * @serial
     */
    private final String content;


    AbstractFormattedPropertiesLine( final int lineNumber, final String content )
    {
        this.lineNumber = lineNumber;
        this.content    = content;
    }

    @Override
    public int getLineNumber()
    {
         return this.lineNumber;
    }

    @Override
    public String getContent()
    {
         return this.content;
    }

    /**
     *
     */
    @Override
    public String toString()
    {
        throw new UnsupportedOperationException();
    }

}
