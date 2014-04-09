package com.googlecode.cchlib.io.impl.win32;

/**
 * Provide some tool to access to AlternateDataStream
 * for NTFS (Alpha)
 * <br>
 * This class is a contender for default NTFS ADS description.
 */
public class MSAlternateDataStreamDescriptionEntry
{
    private String systemFile;
    private String fileName;
    private int    mtfRecord;
    private String description;

    public MSAlternateDataStreamDescriptionEntry(
            String systemFile,
            String fileName,
            int    mtfRecord,
            String description
            )
    {
        this.systemFile  = systemFile;
        this.fileName    = fileName;
        this.mtfRecord   = mtfRecord;
        this.description = description;
    }

    /**
     * @return the System File
     */
    public String getSystemFile()
    {
        return systemFile;
    }

    /**
     * @return the FileName
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * @return the MTF Record
     */
    public int getMTFRecord()
    {
        return mtfRecord;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }
}
