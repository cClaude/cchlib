package cx.ath.choisnet.util;

/**
 * Line structure
 */
/*public*/
class FormattedPropertiesLineKey extends AbstractFormattedPropertiesLine
{
    private static final long serialVersionUID = 1L;
    private final FormattedProperties formattedProperties;

    FormattedPropertiesLineKey(
        final FormattedProperties formattedProperties,
        final int                 lineNumber,
        final String              key
        )
    {
        super( lineNumber, key );

        this.formattedProperties = formattedProperties; // Can't build line outside this class
    }

    @Override
    public boolean isComment()
    {
       return false;
    }

    @Override
    public String getLine()
    {
        final String content = getContent();

        //TODO: encode? probably not
        return content + '=' + this.formattedProperties.getProperty(content);
    }
}
