package cx.ath.choisnet.tools.i18n;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

public interface CustomProperties extends Serializable 
{
    public FileObject getFileObject();
    public String getProperty( String key );
    public void setProperty( String key, String value );

    /**
     * Load from fileObject
     * @param keyBuilderSet 
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void load(Set<String> keyBuilderSet) 
        throws FileNotFoundException, IOException;
    
    /**
     * Save from fileObject
     * 
     * @return true if file has been saved
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean store()
        throws FileNotFoundException, IOException;
}
