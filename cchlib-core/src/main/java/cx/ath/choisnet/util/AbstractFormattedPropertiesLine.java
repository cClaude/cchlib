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
         return lineNumber;
    }

    @Override
    public String getContent()
    {
         return content;
    }

    /**
     *
     */
    @Override
    public String toString()
    {
        throw new UnsupportedOperationException();
    }

//    /* (non-Javadoc)
//     * @see java.lang.Object#hashCode()
//     */
//    @Override
//    public int hashCode()
//    {
//        final int prime = 31;
//        int result = 1;
//        result = (prime * result) + super.hashCode();
//        result = (prime * result)
//                + ((content == null) ? 0 : content.hashCode());
//        result = (prime * result) + (isComment() ? 1231 : 1237);
//        return result;
//    }
//
//    /* (non-Javadoc)
//     * @see java.lang.Object#equals(java.lang.Object)
//     */
//    @Override
//    public boolean equals( final Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
//    {
//        if( this == obj ) {
//            return true;
//            }
//        if( obj == null ) {
//            return false;
//            }
//        if( getClass() != obj.getClass() ) { // $codepro.audit.disable useEquals
//            return false;
//            }
//        FormattedPropertiesLine other = (FormattedPropertiesLine)obj;
//        if( content == null ) {
//            if( other.getContent() != null ) {
//                return false;
//                }
//            }
//        else if( !content.equals( other.getContent() ) ) {
//            return false;
//            }
//        if( isComment() != other.isComment() ) {
//            return false;
//            }
//        return true;
//    }
}
