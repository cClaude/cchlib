package cx.ath.choisnet.util;

public interface FormattedPropertiesLine
{
    int getLineNumber();

    /**
     * @return true if Line is a comment, false otherwise
     */
    boolean isComment();

    /**
     * @return comment or key value
     */
    String getContent();

    /**
     * @return full line content
     */
    String getLine();

}
