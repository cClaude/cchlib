package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import com.googlecode.cchlib.lang.StringHelper;

@Deprecated
class GDAI_tumblr_com_Config implements Config
{
    private static final long serialVersionUID = 1L;

    private final List<Entry> entries = new ArrayList<>();

    /**
     *
     */
    public GDAI_tumblr_com_Config()
    {
        final GDAI_tumblr_com_ConfigLoader loader = new GDAI_tumblr_com_ConfigLoader();

        for( int i = 0; i< loader.getBlogsSize(); i++ ) {
            final EntryImpl entry = new EntryImpl(
                loader.getBlogsName( i ),
                loader.getBlogsDescription( i )
                );

            this.entries.add( entry );
            }
    }

    public void save() throws IOException
    {
        final int       len              = this.entries.size();
        final String[]  blogsNames       = new String[ len ];
        final String[]  blogsDescriptions= new String[ len ];
        int i = 0;

        for( final Entry entry : this.entries ) {
            blogsNames[ i ]          = entry.getName();
            blogsDescriptions[ i++ ] = entry.getDescription();
            }

        GDAI_tumblr_com_ConfigLoader.save( blogsNames, blogsDescriptions );
    }

    public Collection<Entry> getEntriesCollection()
    {
        return this.entries;
    }

    @Override
    public Vector<Vector<?>> toVector() //toEntriesVector()
    {
        final Vector<Vector<?>> vector = new Vector<Vector<?>>();

        for( final Entry entry : this.entries ) {
            final Vector<String> v = new Vector<String>();

            v.add( entry.getName() );
            v.add( entry.getDescription() );

            vector.add( v );
            }

        return vector;
    }

    @Override
    public void setDataFromVector( final Vector<Vector<?>> dataVector )
    {
        this.entries.clear();

        for( final Vector<?> data : dataVector ) {
            final Object name = data.get( 0 );
            final Object desc = data.get( 1 );

            final EntryImpl entry = new EntryImpl(
                StringHelper.nullToEmpty( name.toString() ),
                StringHelper.nullToEmpty( (desc ==  null) ? null : desc.toString() )
                );

            this.entries.add( entry );
            }
    }
}
