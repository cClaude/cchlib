package com.googlecode.cchlib.apps.duplicatefiles.alpha.prefs;

import java.io.File;

/**
 *
 */
public class DefaultRootFile implements RootFile
{
    private static final long serialVersionUID = 1L;
    private File file;
    private RootFileAction rootFileAction;

    /**
     *
     */
    public DefaultRootFile(
        final File 				file,
        final RootFileAction 	rootFileAction
        )
    {
    	if( file == null ) {
    		throw new IllegalArgumentException( "No File provided" );
    		}
    	if( rootFileAction == null ) {
    		throw new IllegalArgumentException( "No RootFileAction provided" );
    		}
    	
        this.file 			= file;
        this.rootFileAction = rootFileAction;
    }

    /**
     * 
     * @param filepath
     * @param rootFileAction
     */
    public DefaultRootFile(
    	final String 			filepath, 
    	final RootFileAction 	rootFileAction
    	) 
    {
    	this( new File( filepath ), rootFileAction );
	}

	@Override
    public File getFile()
    {
        return this.file;
    }

    @Override
    public RootFileAction getRootFileAction()
    {
        return this.rootFileAction;
    }
}
