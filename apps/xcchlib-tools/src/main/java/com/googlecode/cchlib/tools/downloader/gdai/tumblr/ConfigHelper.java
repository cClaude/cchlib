package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.googlecode.cchlib.io.FileHelper;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;
import com.googlecode.cchlib.util.Wrappable;

public class ConfigHelper
{
    private ConfigHelper()
    {
        // Empty
    }

    private static File getConfigFile()
    {
        return FileHelper.getUserConfigDirectoryFile(
            Config.class.getName() + ".json"
            );
    }

    static void save( final Config config ) throws ConfigIOException
    {
        final File configFile = getConfigFile();

        try {
            JSONHelper.save( configFile, config, JSONHelper.PRETTY_PRINT, Include.NON_NULL );
        }
        catch( final JSONHelperException e ) {
            throw new ConfigIOException( configFile.toString(), e );
        }
    }

    static Config load() throws JSONHelperException
    {
        return JSONHelper.load( getConfigFile(), ConfigImpl.class );
    }

    public static <S,R> R[] toArray(
            final Collection<S>  values,
            final Wrappable<S,R> wrapper,
            final Class<R>       type
            )
        {
            @SuppressWarnings("unchecked")
            final R[] results = (R[]) Array.newInstance( type, values.size() );
            int i = 0;

            for( final S value : values ) {
                results[ i++ ] = wrapper.wrap( value );
            }

            return results;
        }

    public static <S> String[] toArrayString(
            final Collection<S>       values,
            final Wrappable<S,String> wrapper
            )
    {
        return toArray( values, wrapper, String.class );
    }
}
