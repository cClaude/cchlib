package com.googlecode.cchlib.net;

import java.io.File;
import com.googlecode.cchlib.lang.OperatingSystem;

/**
 * GetServiceByName lookup port numbers in the /etc/services file
 *
 * @since 4.1.8
 */
public class Services
{
    private static final String UNIX_SERVICES_FILENAME = "/etc/services";
    private static final String WIN32_SYSTEM_VARNAME = "SystemRoot";
    private static final String WIN32_SERVICES_FILENAME = "system32\\drivers\\etc\\services";
    private File servicesFile;

    public Services() throws ServicesFileNotFoundException
    {
        if( OperatingSystem.isUnix() ) {
            servicesFile = new File( UNIX_SERVICES_FILENAME );
            }
        else if( OperatingSystem.isWindows() ) {
            String systemRoot = System.getenv( WIN32_SYSTEM_VARNAME ); // $codepro.audit.disable environmentVariableAccess

            if( systemRoot != null ) {
                File systemRootFile = new File( systemRoot );
                servicesFile        = new File( systemRootFile, WIN32_SERVICES_FILENAME );
                }
         }

        if( ! servicesFile.isFile() ) {
            throw new ServicesFileNotFoundException();
        }
    }

    public File getFile()
    {
        return servicesFile;
    }



}
