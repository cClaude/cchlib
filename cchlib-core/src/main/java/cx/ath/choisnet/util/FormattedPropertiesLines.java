package cx.ath.choisnet.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FormattedPropertiesLines
    implements Iterable<FormattedPropertiesLine>, Serializable
{
    private static final long serialVersionUID = 2L;

    private final FormattedProperties                formattedProperties;
    private final ArrayList<FormattedPropertiesLine> lines = new ArrayList<>();

    FormattedPropertiesLines(final FormattedProperties formattedProperties)
    {
        this.formattedProperties = formattedProperties; // Can't build lines outside this class
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.formattedProperties == null) ? 0 : this.formattedProperties.hashCode());
        result = (prime * result) + ((this.lines == null) ? 0 : this.lines.hashCode());
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final FormattedPropertiesLines other = (FormattedPropertiesLines)obj;
        if( this.formattedProperties == null ) {
            if( other.formattedProperties != null ) {
                return false;
            }
        }

        if( this.lines == null ) {
            if( other.lines != null ) {
                return false;
            }
        } else if( ! isArraysEquals( this.lines, other.lines ) ) {
            return false;
        }
        return true;
    }

    private static <T> boolean isArraysEquals( final List<T> array1, final List<T> array2 )
    {
        if( array1.size() != array2.size() ) {
            return false;
        }

        return array1.containsAll( array2 );
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
        this.lines.add( buildPropertiesLine( size() + 1, key ) );
    }

    public int size()
    {
        return this.lines.size();
    }

    public void addCommentLine( final String comment )
    {
        this.lines.add( buildCommentLine( size() + 1, comment ) );
    }

    public boolean contains( final Object key )
    {
        for( final FormattedPropertiesLine line : this.lines ) {
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
        final Iterator<FormattedPropertiesLine> iter = this.lines.iterator();

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
        return this.lines.iterator();
    }

    public void clear()
    {
        this.lines.clear();
    }

    public List<FormattedPropertiesLine> getLines()
    {
        return this.lines;
    }
}
