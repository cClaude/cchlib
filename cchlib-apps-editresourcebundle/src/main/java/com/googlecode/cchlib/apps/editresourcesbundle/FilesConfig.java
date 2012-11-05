package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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

    private ArrayList<FileObject> fileObjectList = new ArrayList<>();
    //private FileObject leftFileObject;
    //private FileObject rightFileObject;
    private transient ArrayList<Properties> propertiesList = new ArrayList<>();
    ///private transient Properties leftProperties;
    //private transient Properties rightProperties;
    private transient ArrayList<FormattedProperties> formattedPropertiesList = new ArrayList<>();
    //private transient FormattedProperties leftFormattedProperties;
    //private transient FormattedProperties rightFormattedProperties;

    private FileType fileType
          = FileType.FORMATTED_PROPERTIES;
    private EnumSet<FormattedProperties.Store> formattedPropertiesStore
          = EnumSet.allOf( FormattedProperties.Store.class );
    private boolean useLeftHasDefault
          = false;
    private boolean showLineNumbers
          = false;

    public enum FileType {
        PROPERTIES,
        FORMATTED_PROPERTIES
        }

    /**
     * Build default {@link FilesConfig} (no file selected yet)
     */
    public FilesConfig()
    {
        clear();

        // FIXME !!!
        this.setFileObject( null, 1); // set 0 and set 1 to null
        this.setProperties( null, 1); // set 0 and set 1 to null
        this.setFormattedProperties( null, 1); // set 0 and set 1 to null
    }

    /**
     * Build {@link FilesConfig} based on a existing one
     *
     * @param filesConfig
     */
    public FilesConfig( final FilesConfig filesConfig )
    {
        clear();

        for( FileObject entry : filesConfig.fileObjectList ) {
            this.fileObjectList.add( entry );
            }
        for( Properties entry : filesConfig.propertiesList ) {
            this.propertiesList.add( entry );
            }
        for( FormattedProperties entry : filesConfig.formattedPropertiesList ) {
            this.formattedPropertiesList.add( entry );
            }

        //this.leftFileObject = filesConfig.leftFileObject;
        //this.leftProperties = filesConfig.leftProperties;
        //this.leftFormattedProperties = filesConfig.leftFormattedProperties;

        //this.rightFileObject = filesConfig.rightFileObject;
        //this.rightProperties = filesConfig.rightProperties;
        //this.rightFormattedProperties = filesConfig.rightFormattedProperties;

        this.fileType = filesConfig.fileType;
        this.formattedPropertiesStore = filesConfig.formattedPropertiesStore;
        this.useLeftHasDefault = filesConfig.useLeftHasDefault;
        this.showLineNumbers = filesConfig.showLineNumbers;
    }

    public void clear()
    {
        this.fileObjectList.clear();
        this.propertiesList.clear();
        this.formattedPropertiesList.clear();

        //this.leftFileObject = null;
        //this.leftProperties = null;
        //this.leftFormattedProperties = null;

        //this.rightFileObject = null;
        //this.rightProperties = null;
        //this.rightFormattedProperties = null;
    }

    /**
     * Returns the asked FileObject
     * @return the asked FileObject
     */
    public FileObject getFileObject( final int index )
    {
        return this.fileObjectList.get( index );
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
        while( this.fileObjectList.size() < index + 1 ) {
            this.fileObjectList.add( null );
            }
        this.fileObjectList.set( index, fileObject );
    }

    /**
     * Returns the asked FormattedProperties
     * @return the asked FormattedProperties
     */
    public FormattedProperties getFormattedProperties( final int index )
    {
        return this.formattedPropertiesList.get( index );
    }

    private void setFormattedProperties(
        final FormattedProperties formattedProperties,
        final int                 index
        )
    {
        while( this.formattedPropertiesList.size() < index + 1 ) {
            this.formattedPropertiesList.add( null );
            }
        this.formattedPropertiesList.set( index, formattedProperties );
    }

    /**
     * Returns the asked Properties
     * @return the asked Properties
     */
    public Properties getProperties( final int index )
    {
        return this.propertiesList.get( index );
    }

    private void setProperties(
        final Properties properties,
        final int        index
        )
    {
        while( this.propertiesList.size() < index + 1 ) {
            this.propertiesList.add( null );
            }
        this.propertiesList.set( index, properties );
    }

    /**
     * @return the leftFileObject
     */
    public FileObject getLeftFileObject()
    {
        return getFileObject( 0 );
        //return leftFileObject;
    }

    /**
     * @param leftFileObject the leftFileObject to set
     */
    public void setLeftFileObject( FileObject leftFileObject )
    {
        //this.leftFileObject = leftFileObject;
        setFileObject( leftFileObject, 0 );
    }

    /**
     * @return the rightFileObject
     */
    @Deprecated
    public FileObject getRightFileObject()
    {
        return getFileObject( 1 );
        //return rightFileObject;
    }

    /**
     * @param rightFileObject the rightFileObject to set
     */
    @Deprecated
    public void setRightFileObject( FileObject rightFileObject )
    {
        //this.rightFileObject = rightFileObject;
        setFileObject( rightFileObject, 1 );
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

    /**
     * @return the leftFormattedProperties
     */
    public FormattedProperties getLeftFormattedProperties()
    {
        //return leftFormattedProperties;
        return getFormattedProperties( 0 );
    }

    /**
     * @return the leftProperties
     */
    public Properties getLeftProperties()
    {
        //return leftProperties;
        return getProperties( 0 );
    }

    /**
     * @return the rightFormattedProperties
     */
    @Deprecated
    public FormattedProperties getRightFormattedProperties()
    {
        //return rightFormattedProperties;
        return getFormattedProperties( 1 );
    }

    /**
     * @return the rightProperties
     */
    @Deprecated
    public Properties getRightProperties()
    {
        //return rightProperties;
        return getProperties( 1 );
    }

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
        if( this.fileObjectList.size() == 0 ) {
            return false;
            }

        for( FileObject entry : this.fileObjectList ) {
            if( entry == null ) {
                return false;
                }
            else if( ! entry.getFile().isFile() ) {
                return false;
                }
            }

        return true;
//        try {
//            if( this.leftFileObject.getFile().isFile() ) {
//                if( this.rightFileObject.getFile().isFile() ) {
//                    return true;
//                }
//            }
//        }
//        catch( NullPointerException isFalse ) {
//            logger.warn( isFalse );
//        }
//        return false;
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

            if( isUseLeftHasDefault() ) {
                //loadRightProperties(leftProperties);
                loadRightProperties( this.getLeftProperties() );
                }
            else {
                loadRightProperties(null);
                }
            }
        else {
            loadLeftFormattedProperties();

            if( isUseLeftHasDefault() ) {
                //loadRightFormattedProperties(leftFormattedProperties);
                loadRightFormattedProperties( this.getLeftFormattedProperties() );
                }
            else {
                loadRightFormattedProperties(null);
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
        this.setProperties( null, index );

        FormattedProperties formattedProperties = new FormattedProperties(
                defaults,
                formattedPropertiesStore
                );
        this.setFormattedProperties(formattedProperties, index);

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
        this.setFormattedProperties(null, index);

        Properties properties = new Properties( defaults );

        this.setProperties( properties, index );

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
//        this.leftProperties = null;
//        this.leftFormattedProperties = new FormattedProperties(formattedPropertiesStore);
//
//        leftFormattedProperties.load(
//                new FileInputStream(
//                    this.leftFileObject.getFile()
//                    )
//                );
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
//        this.leftFormattedProperties = null;
//        this.leftProperties = new Properties();
//
//        leftProperties.load(
//                new FileInputStream(
//                    this.leftFileObject.getFile()
//                    )
//                );
        this.loadProperties( null, 0 );
    }

    /**
     * @param defaults
     * @throws IOException
     * @throws FileNotFoundException
     */
    @Deprecated
    private void loadRightFormattedProperties(
            FormattedProperties defaults
            )
    throws  FileNotFoundException,
            IOException
    {
//        this.rightProperties = null;
//        this.rightFormattedProperties = new FormattedProperties(
//                defaults,
//                formattedPropertiesStore
//                );
//
//        rightFormattedProperties.load(
//                new FileInputStream(
//                    this.rightFileObject.getFile()
//                    )
//                );
        this.loadFormattedProperties( defaults,  0 );
    }

    /**
     * @param defaults
     * @throws IOException
     * @throws FileNotFoundException
     */
    @Deprecated
    private void loadRightProperties(Properties defaults)
        throws  FileNotFoundException,
                IOException
    {
//        this.rightFormattedProperties = null;
//        this.rightProperties = new Properties(defaults);
//
//        rightProperties.load(
//                new FileInputStream(
//                    this.rightFileObject.getFile()
//                    )
//                );
        this.loadProperties( defaults, 1 );
    }

    private CustomProperties createCustomProperties( int index )
    {
        final FileObject          fileObject            = this.getFileObject( index );
        final Properties          properties            = this.getProperties( index );
        final FormattedProperties formattedProperties	= this.getFormattedProperties( index );

        if( properties != null ) {
            return new DefaultCustomProperties( fileObject, properties );
            }
        else if( formattedProperties != null ) {
            return new FormattedCustomProperties(fileObject,formattedProperties);
            }

        return null;
    }
//
//    public CustomProperties createCustomProperties( final int index )
//    {
//        return createCustomProperties(
//                this.getFileObject( index ),
//                this.getProperties( index ),
//                this.getFormattedProperties( index )
//                );
//    }

    public CustomProperties createLeftCustomProperties()
    {
//        return createCustomProperties(
//                this.leftFileObject,
//                this.leftProperties,
//                this.leftFormattedProperties
//                );
        return createCustomProperties( 0 );
    }

    @Deprecated
    public CustomProperties createRightCustomProperties()
    {
//        return createCustomProperties(
//                this.rightFileObject,
//                this.rightProperties,
//                this.rightFormattedProperties
//                );
        return createCustomProperties( 1 );
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
        load();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((fileObjectList == null) ? 0 : fileObjectList.hashCode());
        result = prime * result
                + ((fileType == null) ? 0 : fileType.hashCode());
        result = prime
                * result
                + ((formattedPropertiesList == null) ? 0
                        : formattedPropertiesList.hashCode());
        result = prime
                * result
                + ((formattedPropertiesStore == null) ? 0
                        : formattedPropertiesStore.hashCode());
        result = prime * result
                + ((propertiesList == null) ? 0 : propertiesList.hashCode());
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
        if (fileObjectList == null) {
            if (other.fileObjectList != null)
                return false;
        } else if (!fileObjectList.equals(other.fileObjectList))
            return false;
        if (fileType != other.fileType)
            return false;
        if (formattedPropertiesList == null) {
            if (other.formattedPropertiesList != null)
                return false;
        } else if (!formattedPropertiesList
                .equals(other.formattedPropertiesList))
            return false;
        if (formattedPropertiesStore == null) {
            if (other.formattedPropertiesStore != null)
                return false;
        } else if (!formattedPropertiesStore
                .equals(other.formattedPropertiesStore))
            return false;
        if (propertiesList == null) {
            if (other.propertiesList != null)
                return false;
        } else if (!propertiesList.equals(other.propertiesList))
            return false;
        if (showLineNumbers != other.showLineNumbers)
            return false;
        if (useLeftHasDefault != other.useLeftHasDefault)
            return false;
        return true;
    }
}
