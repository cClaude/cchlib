package com.googlecode.cchlib.apps.editresourcesbundle.files;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        final FileObject                     fileObject,
        final FormattedProperties            defaults,
        final Set<FormattedProperties.Store> formattedPropertiesStore
        ) throws IOException
    {
        this.fileObject = fileObject;
        this.properties = new FormattedProperties(
                defaults,
                formattedPropertiesStore
                );
    }

    public void load() throws IOException
    {
        try( InputStream is = new FileInputStream( this.fileObject.getFile() ) ) {
            this.properties.load( is );
            }

        this.linesNumbers = new HashMap<>();

        refreshLinesNumber();

        this.fileObject.setCustomProperties( this );
    }

    @Override
    public Set<String> stringPropertyNames()
    {
        return this.properties.stringPropertyNames();
    }

    @Override
    public boolean store()
        throws IOException
    {
        if( this.fileObject.isReadOnly() ) {
            LOGGER.warn( "Can't save (readOnly): " + this.fileObject );

            return false;
            }
        else {
            return storeAs( this.fileObject.getFile() );
            }
    }

    @Override
    public boolean storeAs( final File file ) throws IOException
    {
        try( final OutputStream os = new FileOutputStream( file ) ) {
            this.properties.store( os, StringHelper.EMPTY );
            }

        LOGGER.info( "Save : " + file );

        setEdited( false );

        return true;
    }


    @Override
    public FileObject getFileObject()
    {
        return this.fileObject;
    }

    public FormattedProperties getFormattedProperties()
    {
        return this.properties;
    }

    @Override
    public String getProperty( final String key )
    {
        return this.properties.getProperty( key );
    }

    @Override
    public void setProperty( final String key, final String value )
    {
        setEdited( true );
        this.properties.setProperty( key, value );
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

        for( final FormattedPropertiesLine line : this.properties.getLines() ) {
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
        builder.append( this.fileObject );
        builder.append( ", properties=" );
        builder.append( this.properties );
        builder.append( ", linesNumbers=" );
        builder.append( this.linesNumbers );
        builder.append( ']' );
        return builder.toString();
    }

}
