package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Properties;
import cx.ath.choisnet.util.FormattedProperties;
import cx.ath.choisnet.util.FormattedProperties.Store;

/**
 *
 */
public class FilesConfig implements Serializable
{
    private static final long serialVersionUID = 2L;
    //private static final Logger logger = Logger.getLogger( FilesConfig.class );

    private FileObject[] fileObjectList;
    private transient Properties[] propertiesList;
    private transient FormattedProperties[] formattedPropertiesList;

    private FileType fileType
          = FileType.FORMATTED_PROPERTIES;
    private EnumSet<FormattedProperties.Store> formattedPropertiesStore
          = EnumSet.allOf( FormattedProperties.Store.class );
    private boolean useLeftHasDefault
          = false;
    private boolean showLineNumbers
          = false;

    private final int numberOfFiles;

    public enum FileType {
        PROPERTIES,
        FORMATTED_PROPERTIES
        }

    /**
     * Build default {@link FilesConfig} (no file selected yet)
     */
    public FilesConfig( final int numberOfFiles )
    {
        this.numberOfFiles = numberOfFiles;

        clear();
    }

    /**
     * Build {@link FilesConfig} based on a existing one
     *
     * @param filesConfig
     */
    public FilesConfig( final FilesConfig filesConfig )
    {
        this( filesConfig.numberOfFiles );

        for( int i = 0; i<numberOfFiles; i++ ) {
            this.fileObjectList[ i ]          = filesConfig.fileObjectList[ i ];
            this.propertiesList[ i ]          = filesConfig.propertiesList[ i ];
            this.formattedPropertiesList[ i ] = filesConfig.formattedPropertiesList [ i ];
            }

        this.fileType = filesConfig.fileType;
        this.formattedPropertiesStore = filesConfig.formattedPropertiesStore;
        this.useLeftHasDefault = filesConfig.useLeftHasDefault;
        this.showLineNumbers = filesConfig.showLineNumbers;
    }

    public int getNumberOfFiles()
    {
        return numberOfFiles ;
    }

    public void clear()
    {
        this.fileObjectList          = new FileObject[ numberOfFiles ];
        this.propertiesList          = new Properties[ numberOfFiles ];
        this.formattedPropertiesList = new FormattedProperties[ numberOfFiles ];
    }

    /**
     * Returns the asked FileObject
     * @return the asked FileObject
     */
    public FileObject getFileObject( final int index )
    {
        return this.fileObjectList[ index ];
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
        this.fileObjectList[ index ] = fileObject;
    }

    /**
     * Returns the asked FormattedProperties
     * @return the asked FormattedProperties
     */
    public FormattedProperties getFormattedProperties( final int index )
    {
        return this.formattedPropertiesList[ index ];
    }

    private void setFormattedProperties(
        final FormattedProperties formattedProperties,
        final int                 index
        )
    {
        this.formattedPropertiesList[ index ] = formattedProperties;
    }

    /**
     * Returns the asked Properties
     * @return the asked Properties
     */
    public Properties getProperties( final int index )
    {
        return this.propertiesList[ index ];
    }

    private void setProperties(
        final Properties properties,
        final int        index
        )
    {
        this.propertiesList[ index ] = properties;
    }

    /**
     * @return the leftFileObject
     */
    public FileObject getLeftFileObject()
    {
        return getFileObject( 0 );
     }

    /**
     * @param leftFileObject the leftFileObject to set
     */
    public void setLeftFileObject( FileObject leftFileObject )
    {
        setFileObject( leftFileObject, 0 );
    }

//    /**
//     * @return the rightFileObject
//     */
//    @Deprecated
//    public FileObject getRightFileObject()
//    {
//        return getFileObject( 1 );
//        //return rightFileObject;
//    }
//
//    /**
//     * @param rightFileObject the rightFileObject to set
//     */
//    @Deprecated
//    public void setRightFileObject( FileObject rightFileObject )
//    {
//        //this.rightFileObject = rightFileObject;
//        setFileObject( rightFileObject, 1 );
//    }

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

    /**
     * @return the leftFormattedProperties
     */
    public FormattedProperties getLeftFormattedProperties()
    {
        return getFormattedProperties( 0 );
    }

    /**
     * @return the leftProperties
     */
    public Properties getLeftProperties()
    {
        return getProperties( 0 );
    }

//    /**
//     * @return the rightFormattedProperties
//     */
//    @Deprecated
//    public FormattedProperties getRightFormattedProperties()
//    {
//        //return rightFormattedProperties;
//        return getFormattedProperties( 1 );
//    }
//
//    /**
//     * @return the rightProperties
//     */
//    @Deprecated
//    public Properties getRightProperties()
//    {
//        //return rightProperties;
//        return getProperties( 1 );
//    }

    public EnumSet<Store> getFormattedPropertiesStore()
    {
        return formattedPropertiesStore;
    }

    public void setFormattedPropertiesStore( EnumSet<Store> storeOptions )
    {
        formattedPropertiesStore = storeOptions;
    }

    public void add( Store storeOption )
    {
        formattedPropertiesStore.add( storeOption );
    }

    public void remove( Store storeOption )
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
        for( FileObject entry : this.fileObjectList ) {
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
        if( fileType == FileType.PROPERTIES ) {
            loadLeftProperties();

            Properties def = isUseLeftHasDefault() ? this.getLeftProperties() : null;

            for( int i = 1; i<this.numberOfFiles; i++ ) {
                loadProperties( def, i);
                }
            }
        else {
            loadLeftFormattedProperties();

            FormattedProperties def = isUseLeftHasDefault() ? this.getLeftFormattedProperties() : null;

            for( int i = 1; i<this.numberOfFiles; i++ ) {
                loadFormattedProperties( def, i);
                }
            }
    }

    private void loadFormattedProperties(
        final FormattedProperties defaults,
        final int                index
        )
        throws  FileNotFoundException,
                IOException
    {
        FormattedProperties formattedProperties = new FormattedProperties(
                defaults,
                formattedPropertiesStore
                );

        this.setProperties( null, index );
        this.setFormattedProperties( formattedProperties, index );

        formattedProperties.load(
                new FileInputStream(
                    this.getFileObject( index ).getFile()
                    )
                );
    }

    private void loadProperties(
        final Properties defaults,
        final int        index
        )
        throws  FileNotFoundException,
                IOException
    {
        Properties properties = new Properties( defaults );

        this.setProperties( properties, index );
        this.setFormattedProperties( null, index );

        properties.load(
                new FileInputStream(
                    this.getFileObject( index ).getFile()
                    )
                );
    }

    /**
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void loadLeftFormattedProperties()
        throws  FileNotFoundException,
                IOException
    {
        this.loadFormattedProperties( null,  0 );
    }

    /**
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void loadLeftProperties()
        throws  FileNotFoundException,
                IOException
    {
        this.loadProperties( null, 0 );
    }

    public CustomProperties createCustomProperties( int index )
    {
        final FileObject          fileObject            = this.getFileObject( index );
        final Properties          properties            = this.getProperties( index );
        final FormattedProperties formattedProperties    = this.getFormattedProperties( index );

        if( properties != null ) {
            return new DefaultCustomProperties( fileObject, properties );
            }
        else if( formattedProperties != null ) {
            return new FormattedCustomProperties(fileObject,formattedProperties);
            }

        return null;
    }

    public CustomProperties createLeftCustomProperties()
    {
        return createCustomProperties( 0 );
    }

//    @Deprecated
//    public CustomProperties createRightCustomProperties()
//    {
////        return createCustomProperties(
////                this.rightFileObject,
////                this.rightProperties,
////                this.rightFormattedProperties
////                );
//        return createCustomProperties( 1 );
//    }

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
        this.propertiesList          = new Properties[ numberOfFiles ];
        this.formattedPropertiesList = new FormattedProperties[ numberOfFiles ];
        load();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(fileObjectList);
        result = prime * result
                + ((fileType == null) ? 0 : fileType.hashCode());
        result = prime * result + Arrays.hashCode(formattedPropertiesList);
        result = prime
                * result
                + ((formattedPropertiesStore == null) ? 0
                        : formattedPropertiesStore.hashCode());
        result = prime * result + numberOfFiles;
        result = prime * result + Arrays.hashCode(propertiesList);
        result = prime * result + (showLineNumbers ? 1231 : 1237);
        result = prime * result + (useLeftHasDefault ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof FilesConfig))
            return false;
        FilesConfig other = (FilesConfig) obj;
        if (!Arrays.equals(fileObjectList, other.fileObjectList))
            return false;
        if (fileType != other.fileType)
            return false;
        if (!Arrays.equals(formattedPropertiesList,
                other.formattedPropertiesList))
            return false;
        if (formattedPropertiesStore == null) {
            if (other.formattedPropertiesStore != null)
                return false;
        } else if (!formattedPropertiesStore
                .equals(other.formattedPropertiesStore))
            return false;
        if (numberOfFiles != other.numberOfFiles)
            return false;
        if (!Arrays.equals(propertiesList, other.propertiesList))
            return false;
        if (showLineNumbers != other.showLineNumbers)
            return false;
        if (useLeftHasDefault != other.useLeftHasDefault)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("FilesConfig [fileObjectList=");
        builder.append(Arrays.toString(fileObjectList));
        builder.append(", propertiesList=");
        builder.append(Arrays.toString(propertiesList));
        builder.append(", formattedPropertiesList=");
        builder.append(Arrays.toString(formattedPropertiesList));
        builder.append(", fileType=");
        builder.append(fileType);
        builder.append(", formattedPropertiesStore=");
        builder.append(formattedPropertiesStore);
        builder.append(", useLeftHasDefault=");
        builder.append(useLeftHasDefault);
        builder.append(", showLineNumbers=");
        builder.append(showLineNumbers);
        builder.append(", numberOfFiles=");
        builder.append(numberOfFiles);
        builder.append("]");

        return builder.toString();
    }
}
