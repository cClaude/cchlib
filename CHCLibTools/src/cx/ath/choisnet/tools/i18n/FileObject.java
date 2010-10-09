package cx.ath.choisnet.tools.i18n;

import java.io.File;
import java.io.Serializable;

class FileObject implements Serializable
{
    private static final long serialVersionUID = 1L;
    private File    file;
    private boolean readOnly;
    /** @serial */
    private String noFile = "<<NoFile>>";
   
    public FileObject()
    {
        this(null,true);
    }

    public FileObject(File file)
    {
        this(file,true);
    }
    
    public FileObject(File file, boolean readOnly)
    {
        this.file       = file;
        this.readOnly   = readOnly;
    }
    
    /**
     * @return the file
     */
    public File getFile()
    {
        return file;
    }
    /**
     * @return the readOnly
     */
    public boolean isReadOnly()
    {
        return readOnly;
    }
    
    public String getDisplayName()
    {
        if( file == null ) {
            return noFile;
        }
        else {
            return file.getName();
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append( "FileObject [file=" );
        builder.append( file );
        builder.append( ", readOnly=" );
        builder.append( readOnly );
        builder.append( ']' );
        return builder.toString();
    }
}