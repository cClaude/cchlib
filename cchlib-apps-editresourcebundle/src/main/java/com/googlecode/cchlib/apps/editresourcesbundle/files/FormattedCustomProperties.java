package com.googlecode.cchlib.apps.editresourcesbundle.files;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.lang.StringHelper;
import cx.ath.choisnet.util.FormattedProperties;
import cx.ath.choisnet.util.FormattedPropertiesLine;

/**
 * Use OutputStream
 *
 */
public
class FormattedCustomProperties extends AbstractCustomProperties
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( FormattedCustomProperties.class );

    private final FileObject              fileObject;
    private final FormattedProperties     properties;
    private HashMap<String,Integer> linesNumbers;

    public FormattedCustomProperties(
        final FileObject                            fileObject,
        final FormattedProperties                   defaults,
        final EnumSet<FormattedProperties.Store>    formattedPropertiesStore
        ) throws FileNotFoundException, IOException
    {
        this.fileObject     = fileObject;
        this.properties     = new FormattedProperties(
                defaults,
                formattedPropertiesStore
                );
    }

    public void load() throws FileNotFoundException, IOException
    {
        try( InputStream is = new FileInputStream( this.fileObject.getFile() ) ) {
            this.properties.load( is );
            }

        this.linesNumbers = new HashMap<String,Integer>();

        refreshLinesNumber();

        this.fileObject.setCustomProperties( this );
    }

    @Override
    public Set<String> stringPropertyNames()
    {
        return properties.stringPropertyNames();
    }

    @Override
    public boolean store()
        throws FileNotFoundException, IOException
    {
        if( fileObject.isReadOnly() ) {
            LOGGER.warn( "Can't save (readOnly): " + fileObject );

            return false;
            }
        else {
            return storeAs( fileObject.getFile() );
            }
    }

    @Override
    public boolean storeAs( final File file ) throws FileNotFoundException, IOException
    {
        try( final OutputStream os = new FileOutputStream( file ) ) {
            properties.store( os, StringHelper.EMPTY );
            }

        LOGGER.info( "Save : " + file );

        setEdited( false );

        return true;
    }


    @Override
    public FileObject getFileObject()
    {
        return fileObject;
    }

    public FormattedProperties getFormattedProperties()
    {
        return properties;
    }

    @Override
    public String getProperty( final String key )
    {
        return properties.getProperty( key );
    }

    @Override
    public void setProperty( final String key, final String value )
    {
        setEdited( true );
        properties.setProperty( key, value );
    }

    @Override
    public boolean isLinesNumberHandle()
    {
        return true;
    }

    @Override
    public int getLineNumber( final String key )
    {
        final Integer v = this.linesNumbers.get( key );

        if( v != null ) {
            return v.intValue();
            }
        else {
            return 0;
            }
    }

    private void refreshLinesNumber()
    {
        this.linesNumbers.clear();

        int lineNumber = 1;

        for( final FormattedPropertiesLine line : properties.getLines() ) {
            if( ! line.isComment() ) {
                this.linesNumbers.put( line.getContent(), Integer.valueOf( lineNumber ) );
                }
            lineNumber++;
            }
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append( "FormattedCustomProperties [fileObject=" );
        builder.append( fileObject );
        builder.append( ", properties=" );
        builder.append( properties );
        builder.append( ", linesNumbers=" );
        builder.append( linesNumbers );
        builder.append( ']' );
        return builder.toString();
    }

}
