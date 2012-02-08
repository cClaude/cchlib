package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;
import cx.ath.choisnet.util.FormattedProperties;

/**
 * Use OutputStream
 *
 * @author Claude CHOISNET
 */
public
class FormattedCustomProperties
    implements CustomProperties
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FormattedCustomProperties.class );
    private FileObject          fileObject;
    private FormattedProperties properties;
    private HashMap<String,Integer> linesNumbers;
    private boolean _hasChanged;

    public FormattedCustomProperties(
            final FileObject            fileObject,
            final FormattedProperties   properties
            )
    {
        this.fileObject     = fileObject;
        this.properties     = properties;
        this.linesNumbers   = new HashMap<String,Integer>();

        refreshLinesNumber();

           setEdited( false );
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

            properties.store( os, "" );

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

        for(FormattedProperties.Line line:properties.getLines()) {
            if( ! line.isComment() ) {
                this.linesNumbers.put( line.getKey(), lineNumber );
                }
            lineNumber++;
            }
    }

    private void setEdited( boolean isEdited )
    {
        // TODO: add a listener her !
        this._hasChanged = isEdited;
    }

    @Override
    public boolean isEdited()
    {
        return this._hasChanged;
    }
}
