package cx.ath.choisnet.util;

/**
 * Line structure
 */
/*public*/
class FormattedPropertiesLineComment extends AbstractFormattedPropertiesLine
{
    private static final long serialVersionUID = 1L;

    FormattedPropertiesLineComment(
        final int lineNumber,
        final String comment
        )
    {
        super( lineNumber, comment );
    }

    @Override
    public boolean isComment()
    {
       return true;
    }

    @Override
    public String getLine()
    {
        return getContent();
    }
}
