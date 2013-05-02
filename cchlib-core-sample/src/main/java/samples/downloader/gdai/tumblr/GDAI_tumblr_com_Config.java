package samples.downloader.gdai.tumblr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import com.google.common.base.Strings;

/**
 * 
 *
 */
class GDAI_tumblr_com_Config
{
    private final List<Entry> entries = new ArrayList<Entry>();
    
    /**
     * 
     */
    public interface Entry 
    {
        public String getName();
        public String getDescription();
        }
    
    private class DefaultEntry implements Entry
    {
        private String name;
        private String description;
        public DefaultEntry( String blogName, String blogDescription )
        {
            this.name = blogName;
            this.description = blogDescription;
        }
        @Override
        public String getName()
        {
            return this.name;
        }
        public void setName( String name )
        {
            this.name = name;
        }
        @Override
        public String getDescription()
        {
            return this.description;
        }
        public void setDescription( String description )
        {
            this.description = description;
        }
    }
    
    /**
     * 
     */
    public GDAI_tumblr_com_Config()
    {
        GDAI_tumblr_com_ConfigLoader loader = new GDAI_tumblr_com_ConfigLoader();

        for( int i = 0; i< loader.getBlogsSize(); i++ ) {
            DefaultEntry entry = new DefaultEntry(
                loader.getBlogsName( i ),
                loader.getBlogsDescription( i )
                );

            entries.add( entry ); 
            }
    }

    public void save() throws IOException
    {
        final int       len              = this.entries.size();
        final String[]  blogsNames       = new String[ len ];
        final String[]  blogsDescriptions= new String[ len ];
        int i = 0;
        
        for( Entry entry : this.entries ) {
            blogsNames[ i ]          = entry.getName();
            blogsDescriptions[ i++ ] = entry.getDescription();
            }
        
        GDAI_tumblr_com_ConfigLoader.save( blogsNames, blogsDescriptions );
    }
    
    public Collection<Entry> getEntriesCollection()
    {
        return this.entries;
    }
    
    public Vector<Vector<?>> toEntriesVector()
    {
        final Vector<Vector<?>> vector = new Vector<Vector<?>>();
        
        for( Entry entry : this.entries ) {
            Vector<String> v = new Vector<String>();
            
            v.add( entry.getName() );
            v.add( entry.getDescription() );
            
            vector.add( v );
            }
        
        return vector;
    }
    

    public void setDataVector( final Vector<Vector<?>> dataVector )
    {
        entries.clear();
        
        for( Vector<?> data : dataVector ) {
            Object name = data.get( 0 );
            Object desc = data.get( 1 );
            
            DefaultEntry entry = new DefaultEntry(
                Strings.nullToEmpty( name.toString() ),
                Strings.nullToEmpty( desc ==  null ? null : desc.toString() )
                );

            entries.add( entry ); 
            }
    }    
}