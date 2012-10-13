package com.googlecode.cchlib.apps.editresourcesbundle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Properties;

import org.apache.log4j.Logger;


import cx.ath.choisnet.util.FormattedProperties;
import cx.ath.choisnet.util.FormattedProperties.Store;

/**
 *
 */
class FilesConfig implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger( FilesConfig.class );
    
    private FileObject leftFileObject;
    private FileObject rightFileObject;

    private transient Properties leftProperties;
    private transient Properties rightProperties;
    private transient FormattedProperties leftFormattedProperties;
    private transient FormattedProperties rightFormattedProperties;

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
     *
     */
    public FilesConfig()
    {
    }

    /**
     * @param filesConfig
     *
     */
    public FilesConfig( FilesConfig filesConfig )
    {
        this.leftFileObject = filesConfig.leftFileObject;
        this.leftProperties = filesConfig.leftProperties;
        this.leftFormattedProperties = filesConfig.leftFormattedProperties;

        this.rightFileObject = filesConfig.rightFileObject;
        this.rightProperties = filesConfig.rightProperties;
        this.rightFormattedProperties = filesConfig.rightFormattedProperties;

        this.fileType = filesConfig.fileType;
        this.formattedPropertiesStore = filesConfig.formattedPropertiesStore;
        this.useLeftHasDefault = filesConfig.useLeftHasDefault;
        this.showLineNumbers = filesConfig.showLineNumbers;
    }

    public void clear()
    {
        this.leftFileObject = null;
        this.leftProperties = null;
        this.leftFormattedProperties = null;

        this.rightFileObject = null;
        this.rightProperties = null;
        this.rightFormattedProperties = null;
    }

    /**
     * @return the leftFileObject
     */
    public FileObject getLeftFileObject()
    {
        return leftFileObject;
    }

    /**
     * @param leftFileObject the leftFileObject to set
     */
    public void setLeftFileObject( FileObject leftFileObject )
    {
        this.leftFileObject = leftFileObject;
    }

    /**
     * @return the rightFileObject
     */
    public FileObject getRightFileObject()
    {
        return rightFileObject;
    }

    /**
     * @param rightFileObject the rightFileObject to set
     */
    public void setRightFileObject( FileObject rightFileObject )
    {
        this.rightFileObject = rightFileObject;
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
        return leftFormattedProperties;
    }

    /**
     * @return the leftProperties
     */
    public Properties getLeftProperties()
    {
        return leftProperties;
    }

    /**
     * @return the rightFormattedProperties
     */
    public FormattedProperties getRightFormattedProperties()
    {
        return rightFormattedProperties;
    }

    /**
     * @return the rightProperties
     */
    public Properties getRightProperties()
    {
        return rightProperties;
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
     * @return true if both files are defined and exists
     */
    public boolean isFilesExists()
    {
        try {
            if( this.leftFileObject.getFile().isFile() ) {
                if( this.rightFileObject.getFile().isFile() ) {
                    return true;
                }
            }
        }
        catch( NullPointerException isFalse ) {
        	logger.warn( isFalse );
        }
        return false;
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
                loadRightProperties(leftProperties);
            }
            else {
                loadRightProperties(null);
            }
        }
        else {
            loadLeftFormattedProperties();

            if( isUseLeftHasDefault() ) {
                loadRightFormattedProperties(leftFormattedProperties);
            }
            else {
                loadRightFormattedProperties(null);
            }
        }
    }

    /**
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void loadLeftFormattedProperties()
        throws  FileNotFoundException,
                IOException
    {
        this.leftProperties = null;
        this.leftFormattedProperties = new FormattedProperties(formattedPropertiesStore);

        leftFormattedProperties.load(
                new FileInputStream(
                    this.leftFileObject.getFile()
                    )
                );
    }

    /**
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void loadLeftProperties()
        throws  FileNotFoundException,
                IOException
    {
        this.leftFormattedProperties = null;
        this.leftProperties = new Properties();

        leftProperties.load(
                new FileInputStream(
                    this.leftFileObject.getFile()
                    )
                );
    }

    /**
     * @param defaults
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void loadRightFormattedProperties(
            FormattedProperties defaults
            )
    throws  FileNotFoundException,
            IOException
    {
        this.rightProperties = null;
        this.rightFormattedProperties = new FormattedProperties(
                defaults,
                formattedPropertiesStore
                );

        rightFormattedProperties.load(
                new FileInputStream(
                    this.rightFileObject.getFile()
                    )
                );
    }

    /**
     * @param defaults
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void loadRightProperties(Properties defaults)
        throws  FileNotFoundException,
                IOException
    {
        this.rightFormattedProperties = null;
        this.rightProperties = new Properties(defaults);

        rightProperties.load(
                new FileInputStream(
                    this.rightFileObject.getFile()
                    )
                );
    }

    private static CustomProperties createCustomProperties(
            FileObject          fileObject,
            Properties          properties,
            FormattedProperties formattedProperties
            )
    {
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
        return createCustomProperties(
                this.leftFileObject,
                this.leftProperties,
                this.leftFormattedProperties
                );
    }

    public CustomProperties createRightCustomProperties()
    {
        return createCustomProperties(
                this.rightFileObject,
                this.rightProperties,
                this.rightFormattedProperties
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
        load();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((fileType == null) ? 0 : fileType.hashCode());
        result = prime
                * result
                + ((formattedPropertiesStore == null) ? 0
                        : formattedPropertiesStore.hashCode());
        result = prime * result
                + ((leftFileObject == null) ? 0 : leftFileObject.hashCode());
        result = prime * result
                + ((rightFileObject == null) ? 0 : rightFileObject.hashCode());
        result = prime * result + (showLineNumbers ? 1231 : 1237);
        result = prime * result + (useLeftHasDefault ? 1231 : 1237);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object obj )
    {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        FilesConfig other = (FilesConfig)obj;
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
        if( leftFileObject == null ) {
            if( other.leftFileObject != null ) {
                return false;
            }
        } else if( !leftFileObject.equals( other.leftFileObject ) ) {
            return false;
        }
        if( rightFileObject == null ) {
            if( other.rightFileObject != null ) {
                return false;
            }
        } else if( !rightFileObject.equals( other.rightFileObject ) ) {
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
