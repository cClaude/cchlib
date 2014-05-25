package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search.files;

import java.io.File;
import java.io.Serializable;
import com.googlecode.cchlib.lang.Classes;
import com.googlecode.cchlib.lang.StringHelper;

public final class CurrentFile implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final int COLUMN_LABEL = 0;
    public static final int COLUMN_FILE  = 1;
    public static final int COLUMN_COUNT = 2;

    public static final File NO_FILE = null;

    private File file;
    private long currentPos;

    public CurrentFile( final File file)
    {
        this.file  = file;
    }

    public CurrentFile()
    {
    }

    public void setFile( final File file )
    {
        this.file = file;
        this.currentPos = 0;
    }

    public File getFile()
    {
        return file;
    }

    public String getFilePath()
    {
        if( file == NO_FILE ) {
            return StringHelper.EMPTY;
        } else {
            return file.getAbsolutePath();
        }
    }

    public int getCurrentPos()
    {
        return (int)currentPos;
    }

    public void setCurrentPos( final long currentPos )
    {
        this.currentPos = currentPos;
    }

    public int getLength()
    {
        if( file == NO_FILE ) {
            return 0;
        } else {
            return (int)file.length();
        }
    }

    @Override
    public String toString()
    {
        return getFilePath();
    }

    public static String toString( final CurrentFile currentFile )
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( Classes.getSimpleName( currentFile ) );
        builder.append( " [getFile()=" );
        builder.append( currentFile.getFile() );
        builder.append( ", getFilePath()=" );
        builder.append( currentFile.getFilePath() );
        builder.append( ", getCurrentPos()=" );
        builder.append( currentFile.getCurrentPos() );
        builder.append( ", getLength()=" );
        builder.append( currentFile.getLength() );
        builder.append( "]" );
        return builder.toString();
    }

    public static String createLabel( final int row )
    {
        return Integer.toString( row + 1 );
    }

    public String getString()
    {
        final int perCent = getPerCent();

        if( perCent >= 0 ) {
            return getFilePath() + " ("+ perCent + "%)";
        } else {
            return getFilePath();
        }
    }

    private int getPerCent()
    {
        if( file.length() > 0 ) {
            final long pCent = (currentPos * 100) / file.length();

            return (int)pCent;
        } else {
            return -1;
        }
    }
}
