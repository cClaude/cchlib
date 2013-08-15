package samples.batchrunner.tel.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.googlecode.cchlib.util.PropertiesMap;

public class DestinationFoldersV0
{
    private Map<String,File>   outputFolderMap;
    private Map<String,String> outputMap;
    private File               outputFolderFile;

    public DestinationFoldersV0(final File outputFolderFile, final Map<String,String> outputMap )
    {
        this.outputFolderFile = outputFolderFile;
        this.outputMap        = outputMap;
        this.outputFolderMap  = new HashMap<>( outputMap.size() );

        for( Entry<String,String> entry : outputMap.entrySet() ) {
            File folder = new File( outputFolderFile, entry.getValue() );

            outputFolderMap.put( entry.getKey(), folder );
        }
    }

    public DestinationFoldersV0( File outputFolderFile ) throws IOException
    {
        this( outputFolderFile, getDefaultMap( outputFolderFile ) );
    }

    public File getFolder( String number )
    {
        File folder = outputFolderMap.get( number );

        if( folder == null ) {
            String value = outputMap.get( number );

            if( value != null ) {
                folder = new File( outputFolderFile, value );
            }
        }
        return folder;
    }

    private static Map<String,String> getDefaultMap(File outputFolderFile) throws IOException
    {
        File confFile = new File( outputFolderFile, "conf.properties" );
        
        PropertiesMap map = PropertiesMap.load( confFile );

        return map;
    }
}
