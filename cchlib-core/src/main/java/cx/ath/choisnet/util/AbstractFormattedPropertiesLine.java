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

    @Override
    public boolean equals( final Object other )
    {
        if( other instanceof AbstractFormattedPropertiesLine ) {

            final AbstractFormattedPropertiesLine otherLine = (AbstractFormattedPropertiesLine)other;

            if( this.isComment() == otherLine.isComment() ) {
                return isEqualTo( otherLine );
            }
        }

        return false;
    }

    private boolean isEqualTo( final AbstractFormattedPropertiesLine otherLine )
    {
        if( this.content == null ) {
            if( otherLine.content != null ) {
                return false;
            }
        } else if( !this.content.equals( otherLine.content ) ) {
            return false;
        }
        if( this.lineNumber != otherLine.lineNumber ) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.content == null) ? 0 : this.content.hashCode());
        result = (prime * result) + this.lineNumber;
        return result;
    }
}
