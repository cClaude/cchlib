/************************************************************************************
 *                                                                                  *
 *                                                                                  *
 ************************************************************************************/
package cx.ath.choisnet.swing.filechooser;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * <p>
 * An implementation of FileFilter that filters using 
 * a specified set of extensions. The extension for a 
 * file is the portion of the file name after the last ".". 
 * Files whose name does not contain a "." have no file 
 * name extension. File name extension comparisons are 
 * case insensitive.
 * </p>
 * <p>
 * The following example creates a FileNameExtensionFilter 
 * that will show jpg files:
 * </p>
 * <code>
 * FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
 * JFileChooser fileChooser = ...;
 * fileChooser.addChoosableFileFilter(filter);
 * </code>
 * 
 * Same has {@link javax.swing.filechooser.FileNameExtensionFilter}
 * witch is only available since Java 1.6

 * @author Claude CHOISNET
 */
public class FileNameExtensionFilter extends FileFilter
{
    private String   description;
    private String[] extensions;
    
    /**
     * Creates a FileNameExtensionFilter with the
     * specified description and file name extension.
     * 
     * @param description
     * @param extension
     */
    public FileNameExtensionFilter( String description, String extension)
    {
        this.description    = description;
        this.extensions     = new String[1];
        this.extensions[0]  = extension.toLowerCase();
    }
    
    /**
     * Creates a FileNameExtensionFilter with the
     * specified description and file name extensions.
     * 
     * @param description
     * @param extensions
     * (need Java 1.5)
     */
    public FileNameExtensionFilter( String description, String...extensions )
    {
        this.description = description;
        this.extensions  = new String[extensions.length];
        
        for(int i=0;i<extensions.length;i++) {
            this.extensions[i] = extensions[i].toLowerCase();
        }
    }
    
    @Override
    public boolean accept( File file )
    {
        if( file.isDirectory() ) {
            return true;
        }

        for( String s : this.extensions ) {
            if( file.getName().toLowerCase().endsWith( "." + s ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return The description of this filter.
     */
    @Override
    public String getDescription()
    {
        return this.description;
    }

    /**
     *  @return Returns the set of file name extensions
     *  files are tested against.
     */
    public String[] getExtensions()
    {
        return this.extensions;
    }
}
