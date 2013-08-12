package com.googlecode.cchlib.swing.batchrunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import com.googlecode.cchlib.NeedDoc;

/**
 * 
 * @since 4.1.8
 */
@NeedDoc
public interface BRExecutionEvent 
{
    /**
     * 
     * @return TODOC
     */
    @NeedDoc
    public File getSourceFile();
    
    /**
     * 
     * @return TODOC
     * @throws FileNotFoundException 
     */
    @NeedDoc
    public InputStream getInputStream() throws FileNotFoundException;

    /**
     * 
     * @return TODOC
     */
    @NeedDoc
    public File getDestinationFile();
    
    /**
     * 
     * @return TODOC
     * @throws FileNotFoundException 
     */
    @NeedDoc
    public OutputStream getOutputStream() throws FileNotFoundException;
}
