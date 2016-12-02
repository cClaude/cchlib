package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.cchlib.lang.StringHelper;

class ConfigImpl implements Config
{
    private static final long serialVersionUID = 1L;

    private List<EntryImpl> entries = new ArrayList<>();

    public ConfigImpl()
    {
        // Empty
    }

    public List<EntryImpl> getEntries()
    {
        return this.entries;
    }

    public void setEntries( final List<EntryImpl> entries )
    {
        this.entries = entries;
    }

    @JsonIgnore
    @SuppressWarnings({
        "squid:S1149",// Vector required for swing
        "squid:S1452" // No genetics API
        })
    @Override
    public Vector<Vector<?>> toVector()
    {
        final Vector<Vector<?>> vector = new Vector<>();

        for( final Entry entry : this.entries ) {
            final Vector<String> v = new Vector<>();

            v.add( entry.getName() );
            v.add( entry.getDescription() );

            vector.add( v );
            }

        return vector;
    }

    @Override
    @JsonIgnore
    @SuppressWarnings("squid:S1149") // Vector required for swing
    public void setDataFromVector( final Vector<Vector<?>> dataVector )
    {
        this.entries.clear();

        for( final Vector<?> data : dataVector ) {
            this.entries.add( newEntry( data ) );
            }
    }

    private static EntryImpl newEntry( final List<?> data )
    {
        final EntryImpl entry = new EntryImpl();

        entry.setName(        nullToEmptyString( data.get( 0 ) ) );
        entry.setDescription( nullToEmptyString( data.get( 1 ) ) );

        return entry;
    }

    private static String nullToEmptyString( final Object object )
    {
        if( object instanceof String ) {
            return StringHelper.nullToEmpty( (String)object );
        }

        return null;
    }
}
