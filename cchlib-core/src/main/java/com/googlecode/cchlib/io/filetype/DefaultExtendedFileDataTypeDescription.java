package com.googlecode.cchlib.io.filetype;

import java.awt.Dimension;
import com.googlecode.cchlib.io.filetype.FileDataTypes.Type;

/**
 *
 */
final class DefaultExtendedFileDataTypeDescription
    implements ExtendedFileDataTypeDescription
{
    final private FileDataTypeDescription typeDescription;
    final private Dimension dimension;
    private String formatName;
    
    /**
     * 
     */
    public DefaultExtendedFileDataTypeDescription(
        final FileDataTypeDescription   typeDescription,
        final ImageIOFileData           imageIOFileData
        )
    {
        this.typeDescription    = typeDescription;
        this.dimension          = imageIOFileData.getDimension();
        this.formatName         = imageIOFileData.getFormatName();
    }
    @Override
    public String getExtension()
    {
        if( typeDescription == null ) {
            return null;
            }

        return typeDescription.getExtension();
    }
    @Override
    public String getShortExtension()
    {
        if( typeDescription == null ) {
            return null;
            }

        return typeDescription.getShortExtension();
    }
    @Override
    public Type getType()
    {
        if( typeDescription == null ) {
            return null;
            }

        return typeDescription.getType();
    }
    @Override
    public Dimension getDimension()
    {
        return this.dimension;
    }
    @Override
    public String getFormatName()
    {
        return this.formatName;
    }
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "DefaultExtendedFileDataTypeDescription [typeDescription=" );
        builder.append( typeDescription );
        builder.append( ", dimension=" );
        builder.append( dimension );
        builder.append( ", formatName=" );
        builder.append( formatName );
        builder.append( "]" );
        return builder.toString();
    }
}
