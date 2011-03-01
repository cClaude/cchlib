package alpha.cx.ath.choisnet.io;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Provide some tool to access to AlternateDataStream (Alpha)
 *
 * @author Claude CHOISNET
 */
public class AlternateDataStream 
{
    private File    file;
    private String  filename;
    private String  streamname;
    private Boolean isSupported;
    
    public AlternateDataStream(
            File   parent,
            String filename,
            String streamname
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
        return file;
    }

    /**
     * @return the filename
     */
    public String getFilename()
    {
        return filename;
    }

    /**
     * @return the stream name
     */
    public String getStreamName()
    {
        return streamname;
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
            
            AlternateDataStream ads = new AlternateDataStream(
                    parent,
                    "~ThisFileShouldNotExist." + getClass().getName() + ".tmp",
                    "Test"
                    );
            boolean isSupported;
            
            try {
                FileOutputStream fos = new FileOutputStream(ads.getStreamFile());
                fos.write( 1 );
                fos.close();
                isSupported = true;
            }
            catch( java.io.IOException e ) {
                isSupported = false;
            }
           
            ads.getStreamSupportFile().delete();
            
            this.isSupported = isSupported;
        }
        
        return this.isSupported;
    }
}
