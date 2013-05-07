package com.googlecode.cchlib.apps.editresourcesbundle.files;

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

/**
 * Use OutputStream
 *
 */
public
class FormattedCustomProperties extends AbstractCustomProperties
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FormattedCustomProperties.class );
    private FileObject          fileObject;
    private FormattedProperties properties;
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
        
        InputStream is = new FileInputStream( this.fileObject.getFile() );
        try {
            this.properties.load( is );
            }
        finally {
            is.close();
            }
        
        this.linesNumbers   = new HashMap<String,Integer>();
        
        refreshLinesNumber();
        
        this.fileObject.setCustomProperties( this );
    }

    @Override
    public Set<String> stringPropertyNames()
    {
        return properties.stringPropertyNames();
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.i18n.PropertiesInOut#store(cx.ath.choisnet.tools.i18n.FileObject)
     */
    @Override
    public boolean store()
        throws FileNotFoundException, IOException
    {
        if( fileObject.isReadOnly() ) {
            logger.warn( "Can't save (readOnly): " + fileObject );

            return false;
            }
        else {
            OutputStream os = new FileOutputStream( fileObject.getFile() );

            properties.store( os, StringHelper.EMPTY );

            os.close();
            logger.info( "Save : " + fileObject );

            setEdited( false );
            return true;
            }
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
    public String getProperty( String key )
    {
        return properties.getProperty( key );
    }

    @Override
    public void setProperty( String key, String value )
    {
        setEdited( true );
        properties.setProperty( key, value );
    }

    @Override
    public boolean handleLinesNumbers()
    {
        return true;
    }

    @Override
    public int getLineNumber( String key )
    {
        Integer v = this.linesNumbers.get( key );

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

        for( FormattedProperties.Line line:properties.getLines() ) {
            if( ! line.isComment() ) {
                this.linesNumbers.put( line.getKey(), lineNumber );
                }
            lineNumber++;
            }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
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
