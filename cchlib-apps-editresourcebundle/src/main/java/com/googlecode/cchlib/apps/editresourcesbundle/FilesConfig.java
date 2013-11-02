package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Properties;
import com.googlecode.cchlib.apps.editresourcesbundle.files.CustomProperties;
import com.googlecode.cchlib.apps.editresourcesbundle.files.DefaultCustomProperties;
import com.googlecode.cchlib.apps.editresourcesbundle.files.FileObject;
import com.googlecode.cchlib.apps.editresourcesbundle.files.FormattedCustomProperties;
import com.googlecode.cchlib.apps.editresourcesbundle.prefs.Preferences;
import cx.ath.choisnet.util.FormattedProperties;

/**
 *
 */
public class FilesConfig implements Serializable
{
    private static final long serialVersionUID = 2L;
    //private static final Logger logger = Logger.getLogger( FilesConfig.class );

    private FileObject[] fileObjects;

    private FileType fileType
          = FileType.FORMATTED_PROPERTIES;
    private EnumSet<FormattedProperties.Store> formattedPropertiesStore
          = EnumSet.allOf( FormattedProperties.Store.class );
    private boolean useLeftHasDefault
          = false;
    private boolean showLineNumbers
          = false;

    private /*final*/ int numberOfFiles;

    public enum FileType {
        PROPERTIES,
        FORMATTED_PROPERTIES
        }

//    private FilesConfig( final int numberOfFiles )
//    {
//        this.numberOfFiles = numberOfFiles;
//
//        clear();
//    }

    /**
     * Build default {@link FilesConfig} (no file selected yet)
     */
    public FilesConfig( Preferences preferences )
    {
        //this( preferences.getNumberOfFiles() );
        setNumberOfFiles( preferences.getNumberOfFiles() );
     }


    /**
     * Build {@link FilesConfig} based on a existing one
     *
     * @param filesConfig
     */
    public FilesConfig( final FilesConfig filesConfig )
    {
        //this( filesConfig.numberOfFiles );
        setNumberOfFiles( filesConfig.numberOfFiles );

        this.fileObjects              = Arrays.copyOf( filesConfig.fileObjects, numberOfFiles );
        this.fileType                 = filesConfig.fileType;
        this.formattedPropertiesStore = filesConfig.formattedPropertiesStore;
        this.useLeftHasDefault        = filesConfig.useLeftHasDefault;
        this.showLineNumbers          = filesConfig.showLineNumbers;
    }

    public void setNumberOfFiles( int numberOfFiles )
    {
        this.numberOfFiles = numberOfFiles;

        if( this.fileObjects == null ) {
            this.fileObjects   = new FileObject[ numberOfFiles ];
            }
        else {
            FileObject[] oldArray = this.fileObjects;

            this.fileObjects   = new FileObject[ numberOfFiles ];

            int min = (numberOfFiles > oldArray.length) ? oldArray.length : numberOfFiles;

            for( int i = 0; i<min; i++ ) {
                this.fileObjects[ i ] = oldArray[ i ];
                }
            }
    }

    public int getNumberOfFiles()
    {
        return numberOfFiles ;
    }

    public void clear()
    {
        this.fileObjects = new FileObject[ numberOfFiles ];
    }

    /**
     * Returns the asked FileObject
     * @return the asked FileObject
     */
    public FileObject getFileObject( final int index )
    {
        return this.fileObjects[ index ];
    }

    /**
     *
     * @param fileObject
     * @param index
     */
    public void setFileObject(
        final FileObject fileObject,
        final int        index
        )
    {
        this.fileObjects[ index ] = fileObject;
    }

    /**
     * Returns the asked CustomProperties
     * @return the asked CustomProperties
     */
    public CustomProperties getCustomProperties( final int index )
    {
        return this.fileObjects[ index ].getCustomProperties();
    }

    /**
     * @return the leftFileObject
     */
    public FileObject getLeftFileObject()
    {
        return getFileObject( 0 );
     }

    /**
     * @return the fileType
     */
    public FileType getFileType()
    {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType( FileType fileType )
    {
        this.fileType = fileType;
    }

    public boolean isUseLeftHasDefault()
    {
        return useLeftHasDefault;
    }

    /**
     * @param useLeftHasDefault the useLeftHasDefault to set
     */
    public void setUseLeftHasDefault( boolean useLeftHasDefault )
    {
        this.useLeftHasDefault = useLeftHasDefault;
    }

    public EnumSet<FormattedProperties.Store> getFormattedPropertiesStore()
    {
        return formattedPropertiesStore;
    }

    public void setFormattedPropertiesStore(
        final EnumSet<FormattedProperties.Store> storeOptions
        )
    {
        formattedPropertiesStore = storeOptions;
    }

    public void add( final FormattedProperties.Store storeOption )
    {
        formattedPropertiesStore.add( storeOption );
    }

    public void remove( final FormattedProperties.Store storeOption )
    {
        formattedPropertiesStore.remove( storeOption );
    }

    public boolean isShowLineNumbers()
    {
        return showLineNumbers;
    }

    public void setShowLineNumbers(boolean showLineNumbers)
    {
        this.showLineNumbers = showLineNumbers;
    }

    /**
     * @return true if all (both?) files are defined and exists
     */
    public boolean isFilesExists()
    {
        for( FileObject entry : this.fileObjects ) {
            if( entry == null ) {
                return false;
                }
            else if( ! entry.getFile().isFile() ) {
                return false;
                }
            }

        return true;
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void load()
        throws  FileNotFoundException,
                IOException
    {
        switch( fileType )
        {
            case PROPERTIES:
                privateLoadProperties();
                break;

            case FORMATTED_PROPERTIES :
                privateLoadFormattedProperties();
                break;
        }
    }

    private void privateLoadProperties() throws FileNotFoundException, IOException
    {
        privateLoadProperties( null, 0 );

        Properties def = isUseLeftHasDefault() ?
            DefaultCustomProperties.class.cast(
                this.fileObjects[ 0 ].getCustomProperties()
                ).getProperties()
            :
            null;

        for( int i = 1; i<this.numberOfFiles; i++ ) {
            privateLoadProperties( def, i);
            }
    }


    private void privateLoadProperties(
            final Properties defaults,
            final int        index
            )
            throws  FileNotFoundException,
                    IOException
    {
        CustomProperties cprop = new DefaultCustomProperties( this.getFileObject( index ), defaults );
    }

    private void privateLoadFormattedProperties() throws FileNotFoundException, IOException
    {
        privateLoadFormattedProperties( null, 0 );

        FormattedProperties def  =
            isUseLeftHasDefault() ?
                FormattedCustomProperties.class.cast(
                    this.fileObjects[ 0 ].getCustomProperties()
                    ).getFormattedProperties()
                :
                null;

        for( int i = 1; i<this.numberOfFiles; i++ ) {
            privateLoadFormattedProperties( def, i );
            }
    }

    private void privateLoadFormattedProperties(
        final FormattedProperties defaults,
        final int                 index
        )
        throws  FileNotFoundException,
                IOException
    {
        CustomProperties cprop = new FormattedCustomProperties(
                this.getFileObject( index ),
                defaults,
                formattedPropertiesStore
                );
    }

    //Serializable
    private void writeObject(java.io.ObjectOutputStream out)
        throws IOException
    {
        out.defaultWriteObject();
    }

    //Serializable
    private void readObject(java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();

        // Restore transient fields !
        //load();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + Arrays.hashCode( fileObjects );
        result = (prime * result)
                + ((fileType == null) ? 0 : fileType.hashCode());
        result = (prime
                * result)
                + ((formattedPropertiesStore == null) ? 0
                        : formattedPropertiesStore.hashCode());
        result = (prime * result) + numberOfFiles;
        result = (prime * result) + (showLineNumbers ? 1231 : 1237);
        result = (prime * result) + (useLeftHasDefault ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( !(obj instanceof FilesConfig) ) return false;
        FilesConfig other = (FilesConfig)obj;
        if( !Arrays.equals( fileObjects, other.fileObjects ) ) {
            return false;
        }
        if( fileType != other.fileType ) return false;
        if( formattedPropertiesStore == null ) {
            if( other.formattedPropertiesStore != null ) return false;
        } else if( !formattedPropertiesStore
                .equals( other.formattedPropertiesStore ) ) return false;
        if( numberOfFiles != other.numberOfFiles ) return false;
        if( showLineNumbers != other.showLineNumbers ) return false;
        if( useLeftHasDefault != other.useLeftHasDefault ) return false;
        return true;
    }
}
