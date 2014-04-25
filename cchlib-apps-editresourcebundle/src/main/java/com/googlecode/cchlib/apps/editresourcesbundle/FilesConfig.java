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

public class FilesConfig implements Serializable
{
    private static final long serialVersionUID = 2L;

    private FileObject[] fileObjects;

    private FileType fileType
          = FileType.FORMATTED_PROPERTIES;
    private EnumSet<FormattedProperties.Store> formattedPropertiesStore
          = EnumSet.allOf( FormattedProperties.Store.class );
    private boolean useLeftHasDefault
          = false;
    private boolean showLineNumbers
          = false;

    private int numberOfFiles;

    public enum FileType {
        PROPERTIES,
        FORMATTED_PROPERTIES
        }

    /**
     * Build default {@link FilesConfig} (no file selected yet)
     */
    public FilesConfig( final Preferences preferences )
    {
        setNumberOfFiles( preferences.getNumberOfFiles() );
     }


    /**
     * Build {@link FilesConfig} based on a existing one
     *
     * @param filesConfig
     */
    public FilesConfig( final FilesConfig filesConfig )
    {
        setNumberOfFiles( filesConfig.numberOfFiles );

        this.fileObjects              = Arrays.copyOf( filesConfig.fileObjects, numberOfFiles );
        this.fileType                 = filesConfig.fileType;
        this.formattedPropertiesStore = filesConfig.formattedPropertiesStore;
        this.useLeftHasDefault        = filesConfig.useLeftHasDefault;
        this.showLineNumbers          = filesConfig.showLineNumbers;
    }

    public void setNumberOfFiles( final int numberOfFiles )
    {
        this.numberOfFiles = numberOfFiles;

        if( this.fileObjects == null ) {
            this.fileObjects   = new FileObject[ numberOfFiles ];
            }
        else {
            final FileObject[] oldArray = this.fileObjects;

            this.fileObjects = new FileObject[ numberOfFiles ];

            final int min = (numberOfFiles > oldArray.length) ? oldArray.length : numberOfFiles;

            System.arraycopy( oldArray, 0, fileObjects, 0, min );
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
    public void setFileType( final FileType fileType )
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
    public void setUseLeftHasDefault( final boolean useLeftHasDefault )
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

    public void setShowLineNumbers(final boolean showLineNumbers)
    {
        this.showLineNumbers = showLineNumbers;
    }

    /**
     * @return true if all (both?) files are defined and exists
     */
    public boolean isFilesExists()
    {
        for( final FileObject entry : this.fileObjects ) {
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

        final Properties def = isUseLeftHasDefault() ?
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
        final CustomProperties cprop = new DefaultCustomProperties( this.getFileObject( index ), defaults );
    }

    private void privateLoadFormattedProperties() throws FileNotFoundException, IOException
    {
        privateLoadFormattedProperties( null, 0 );

        final FormattedProperties def  =
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
        final FormattedCustomProperties cprop = new FormattedCustomProperties(
                this.getFileObject( index ),
                defaults,
                formattedPropertiesStore
                );
        cprop.load();
    }

    //Serializable
    private void writeObject(final java.io.ObjectOutputStream out)
        throws IOException
    {
        out.defaultWriteObject();
    }

    //Serializable
    private void readObject(final java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();

        // Restore transient fields !
        //load();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31; // $codepro.audit.disable numericLiterals
        int result = 1;
        result = (prime * result) + Arrays.hashCode( fileObjects );
        result = (prime * result)
                + ((fileType == null) ? 0 : fileType.hashCode());
        result = (prime
                * result)
                + ((formattedPropertiesStore == null) ? 0
                        : formattedPropertiesStore.hashCode());
        result = (prime * result) + numberOfFiles;
        result = (prime * result) + (showLineNumbers ? 1231 : 1237); // $codepro.audit.disable numericLiterals
        result = (prime * result) + (useLeftHasDefault ? 1231 : 1237); // $codepro.audit.disable numericLiterals
        return result;
    }

    @Override
    public boolean equals( final Object obj ) // $codepro.audit.disable cyclomaticComplexity
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( !(obj instanceof FilesConfig) ) {
            return false;
        }
        final FilesConfig other = (FilesConfig)obj;
        if( !Arrays.equals( fileObjects, other.fileObjects ) ) {
            return false;
        }
        if( fileType != other.fileType ) {
            return false;
        }
        if( formattedPropertiesStore == null ) {
            if( other.formattedPropertiesStore != null ) {
                return false;
            }
        } else if( !formattedPropertiesStore
                .equals( other.formattedPropertiesStore ) ) {
            return false;
        }
        if( numberOfFiles != other.numberOfFiles ) {
            return false;
        }
        if( showLineNumbers != other.showLineNumbers ) {
            return false;
        }
        if( useLeftHasDefault != other.useLeftHasDefault ) {
            return false;
        }
        return true;
    }
}
