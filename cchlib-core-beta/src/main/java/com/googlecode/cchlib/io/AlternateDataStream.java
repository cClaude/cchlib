package com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Provide some tool to access to AlternateDataStream (Alpha)
 */
public class AlternateDataStream
{
    private final File    file;
    private final String  filename;
    private final String  streamname;
    private Boolean isSupported;

    public AlternateDataStream(
            final File   parent,
            final String filename,
            final String streamname
            )
    {
        this.filename   = filename;
        this.streamname = streamname;
        this.file       = new File( parent, filename + ':' + streamname);
    }

    /**
     * @return the file
     */
    protected File getStreamFile()
    {
        return this.file;
    }

    /**
     * @return the filename
     */
    public String getFilename()
    {
        return this.filename;
    }

    /**
     * @return the stream name
     */
    public String getStreamName()
    {
        return this.streamname;
    }

    public File getParentFile()
    {
        return this.file.getParentFile();
    }

    protected File getStreamSupportFile()
    {
        return new File(
                this.file.getParentFile(),
                this.filename
                );
    }

    public boolean isSupported()
    {
        if( this.isSupported == null ) {
            File parent = this.file;

            while( parent.getParentFile() != null ) {
                parent = parent.getParentFile();
            }

            final AlternateDataStream ads = new AlternateDataStream(
                    parent,
                    "~ThisFileShouldNotExist." + getClass().getName() + ".tmp",
                    "Test"
                    );
            boolean isSupported;

            try( final FileOutputStream fos = new FileOutputStream(ads.getStreamFile()) ) {
                fos.write( 1 );
                isSupported = true;
            }
            catch( final IOException e ) {
                isSupported = false;
            }

            ads.getStreamSupportFile().delete();

            this.isSupported = Boolean.valueOf( isSupported );
        }

        return this.isSupported.booleanValue();
    }
}
