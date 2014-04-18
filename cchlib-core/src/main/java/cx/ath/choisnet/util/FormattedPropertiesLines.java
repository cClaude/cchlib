package cx.ath.choisnet.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FormattedPropertiesLines
    implements Iterable<FormattedPropertiesLine>, Serializable
{
    private static final long serialVersionUID = 1L;

    private final FormattedProperties formattedProperties;
    private final List<FormattedPropertiesLine> lines = new ArrayList<>();

    FormattedPropertiesLines(FormattedProperties formattedProperties)
    {
        this.formattedProperties = formattedProperties; // Can't build lines outside this class
    }

    private FormattedPropertiesLine buildCommentLine(
        final int    lineNumber,
        final String comment
        )
    {
        return new FormattedPropertiesLineComment( lineNumber, comment );
    }

    private FormattedPropertiesLine buildPropertiesLine(
        final int    lineNumber,
        final String key
        )
    {
        return new FormattedPropertiesLineKey( this.formattedProperties, lineNumber, key );
    }

    public void addKey( final String key )
    {
        lines.add( buildPropertiesLine( size() + 1, key ) );
    }

    public int size()
    {
        return this.lines.size();
    }

    public void addCommentLine( final String comment )
    {
        lines.add( buildCommentLine( size() + 1, comment ) );
    }

    public boolean contains( final Object key )
    {
        for( FormattedPropertiesLine line : lines ) {
            if( ! line.isComment() ) {
                if( line.getContent().equals( key )) {
                    return true;
                    }
                }
            }

        return false;
    }

    public FormattedPropertiesLine remove( final Object key )
    {
        final Iterator<FormattedPropertiesLine> iter = lines.iterator();

        while( iter.hasNext() ) { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.minimizeScopeOfLocalVariables
            final FormattedPropertiesLine line = iter.next();

            if( ! line.isComment() ) {
                if( line.getContent().equals( key )) {
                    iter.remove();
                    return line;
                    }
                }
            }

        return null;
    }

    @Override
    public Iterator<FormattedPropertiesLine> iterator()
    {
        return lines.iterator();
    }

    public void clear()
    {
        lines.clear();
    }

    public List<FormattedPropertiesLine> getLines()
    {
        return lines;
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
//        result = (prime * result) + ((lines == null) ? 0 : lines.hashCode());
//        return result;
//    }
//
//    /* (non-Javadoc)
//     * @see java.lang.Object#equals(java.lang.Object)
//     */
//    @Override
//    public boolean equals( Object obj ) // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals
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
//        FormattedPropertiesLines other = (FormattedPropertiesLines)obj;
//
//        if( lines == null ) {
//            if( other.lines != null ) {
//                return false;
//                }
//            }
//        else if( !lines.equals( other.lines ) ) {
//            return false;
//            }
//
//        return true;
//    }
}
// ---------------------------------------------
