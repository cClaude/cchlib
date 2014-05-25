package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import java.io.File;

public interface CurrentFiles
{
    void setCurrentDirLabel( String currentDirLabel );
    void setCurrentDir( File currentDir );

    void setCurrentFileLabels( String label );
    void setCurrentFile( int threadNumber, File currentFile );
    void setCurrentFileNewLength( int threadNumber, File currentFile, long lengthToInc );

    ///** Initialize <code>threadNumber</code> */
    //void clearCurrentFiles( int threadNumber );
    //void clearCurrentFileLabels();
    void clear();
}
