package alpha.cx.ath.choisnet.system.impl.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import com.googlecode.cchlib.lang.OperatingSystem;
import alpha.cx.ath.choisnet.system.SystemEnvironmentVar;

public class JavaSystemEnvironmentVar 
    extends AbstractSystemEnvironmentVar 
        implements SystemEnvironmentVar
{
    private static final String SYSTEM_ENVIRONMENT_VAR_FILENAME = ".javaSystemEnvironment";
    private static JavaSystemEnvironmentVar javaSystemEnvironmentVar;
    private File javaSystemEnvironmentVarFile;

    private JavaSystemEnvironmentVar()
    {
        File userHome = OperatingSystem.getUserHome();
        javaSystemEnvironmentVarFile = new File( userHome, SYSTEM_ENVIRONMENT_VAR_FILENAME );

        if( ! javaSystemEnvironmentVarFile.isFile() ) {
            // Create file if not exist
            saveUnsynchronizedProperties( new Properties() );
            }
    }

    /**
     * @return the unique SystemEnvironmentVar based on full native Java code.
     */
    public static SystemEnvironmentVar getSystemEnvironmentVar()
    {
        if( javaSystemEnvironmentVar == null ) {
            javaSystemEnvironmentVar = new JavaSystemEnvironmentVar();
            }

        return javaSystemEnvironmentVar;
    }

    private Properties loadUnsynchronizedProperties()
    {
        Properties properties = new Properties();

        try( InputStream stream = new FileInputStream( javaSystemEnvironmentVarFile ) ) {
            properties.load( stream  );
            }
        catch( IOException e ) {
            throw new RuntimeException( e );
            }

        return properties;
    }

    private void saveUnsynchronizedProperties( Properties properties )
    {
        try( OutputStream stream = new FileOutputStream( javaSystemEnvironmentVarFile ) ) {
            properties.store( stream, null );
            }
        catch( IOException e ) {
            throw new RuntimeException( "Error while setting vars", e );
            }
    }

    private Properties getProperties()
    {
        Properties properties;

        synchronized( javaSystemEnvironmentVarFile ) {
            properties = loadUnsynchronizedProperties();
            }

        return properties;
    }

    @Override
    public String getVar( String name )
    {
        return getProperties().getProperty( name );
    }

    @Override
    public void setVar( String name, String value )
    {
        synchronized( javaSystemEnvironmentVarFile ) {
            Properties properties = loadUnsynchronizedProperties();

            properties.put( name, value );

            saveUnsynchronizedProperties( properties );
            }
    }

    @Override
    public void deleteVar( String varname )
        throws UnsupportedOperationException
    {
        synchronized( javaSystemEnvironmentVarFile ) {
            Properties properties = loadUnsynchronizedProperties();

            properties.remove( varname );

            saveUnsynchronizedProperties( properties );
            }
    }

    @Override
    protected Iterable<String> getStringKeys()
    {
        return getProperties().stringPropertyNames();
    }
}
