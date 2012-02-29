package autoRename.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 */
public class XFileFilter 
{
    private FileFilter fileFilter;
    
    XFileFilter( FileFilter fileFilter )
    {
        this.fileFilter = fileFilter;
    }
    
    public Collection<File> getFiles( File file )
    {
        File[] files = file.listFiles( fileFilter );
        
        if( files == null ) {
            return Collections.emptyList();
        }

        Collection<File> filterFiles = new ArrayList<File>();
        
        for( File f : files ) {
            if( this.fileFilter.accept( f ) ) {
                filterFiles.add( f );
                }
        }
        
        return filterFiles;
    }
}
