package com.googlecode.cchlib.net;

import java.io.File;
import com.googlecode.cchlib.lang.OperatingSystem;

public class ServicesFactory
{
    private static final class ServicesImpl implements Services
    {
        private static final String UNIX_SERVICES_FILENAME  = "/etc/services";
        private static final String WIN32_SYSTEM_VARNAME    = "SystemRoot";
        private static final String WIN32_SERVICES_FILENAME = "system32\\drivers\\etc\\services";

        private File servicesFile;

        public ServicesImpl() throws ServicesFileNotFoundException
        {
            if( OperatingSystem.isUnix() ) {
                this.servicesFile = new File( UNIX_SERVICES_FILENAME );
                }
            else if( OperatingSystem.isWindows() ) {
                final String systemRoot = System.getenv( WIN32_SYSTEM_VARNAME );

                if( systemRoot != null ) {
                    final File systemRootFile = new File( systemRoot );
                    this.servicesFile         = new File( systemRootFile, WIN32_SERVICES_FILENAME );
                    }
             }

            if( ! this.servicesFile.isFile() ) {
                throw new ServicesFileNotFoundException();
            }
        }

        @Override
        public File getFile()
        {
            return this.servicesFile;
        }
   }

    private ServicesFactory()
    {
        // All static
    }

    /**
     * GetServiceByName lookup port numbers in the /etc/services file
     *
     * @return actual implementation of {@link Services}
     * @throws ServicesFileNotFoundException if service file not found or not visible
     */
    public static Services newServices() throws ServicesFileNotFoundException
    {
        return new ServicesImpl();
    }
}
