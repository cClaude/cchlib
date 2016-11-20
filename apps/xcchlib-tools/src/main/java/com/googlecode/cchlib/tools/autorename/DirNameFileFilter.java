/**
 * 
 */
package com.googlecode.cchlib.tools.autorename;

import java.io.File;
import java.util.EnumSet;

/**
 * @author CC
 *
 */
public class DirNameFileFilter extends DirFileFilter
{
    public enum Attrib{ IGNORE_CASE };

    private String  dirname;
    EnumSet<Attrib> attribSet;
    
    /**
     * @param dirname
     * @param attributes 
     * 
     */
    public DirNameFileFilter( String dirname, EnumSet<Attrib> attributes )
    {
        this.dirname = dirname;
        
        if( attributes == null ) {
            this.attribSet = EnumSet.noneOf( Attrib.class );
        }
        else {
            this.attribSet = attributes;
            }
    }

    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept( File file )
    {
        if( ! file.isDirectory() ) {
            return false;
        }
        
        if( attribSet.contains( Attrib.IGNORE_CASE ) ) {
            if( file.getName().equalsIgnoreCase( this.dirname ) ) {
                return true;
            }
        } else {
            if( file.getName().equals( this.dirname ) ) {
                return true;
            }
        }
        
        System.out.println( "L2_IGNORE_DIR: " + file );
        
        return false;
    }

}
