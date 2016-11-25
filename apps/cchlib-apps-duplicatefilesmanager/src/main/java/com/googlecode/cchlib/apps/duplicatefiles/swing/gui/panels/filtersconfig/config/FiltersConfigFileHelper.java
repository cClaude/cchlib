package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config;

import java.io.File;
import java.io.InputStream;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.duplicatefiles.common.JSONHelper;
import com.googlecode.cchlib.apps.duplicatefiles.common.JSONHelperException;

public class FiltersConfigFileHelper
{
    private static final Logger LOGGER = Logger.getLogger( FiltersConfigFileHelper.class );
    private static final boolean PRETTY_JSON = true;

    private FiltersConfigFileHelper()
    {
        // All static
    }

//    public static FiltersConfig load( final Properties properties )
//    {
//        final FiltersConfig filtersConfig = new FiltersConfig();
//
//        load( filtersConfig.getFileTypes(), properties, "filetype" );
//        load( filtersConfig.getDirTypes() , properties, "dirtype"  );
//
//        return filtersConfig;
//    }

    public static void save(
        final File          jsonFile,
        final FiltersConfig value
        ) throws JSONHelperException
    {
        JSONHelper.toJSON( jsonFile, value, PRETTY_JSON );
    }

    public static FiltersConfig load( final InputStream json )
        throws JSONHelperException
    {
        return JSONHelper.load( json, FiltersConfig.class );
    }

//    private static void load(
//        final Collection<FilterEntry> filterEntries,
//        final Properties              properties,
//        final String                  prefix
//        )
//    {
//        for( int i=0;;i++ ) {
//            final Integer index   = Integer.valueOf( i );
//            final String  descKey = String.format( "%s.%d.description", prefix, index );
//            final String  dataKey = String.format( "%s.%d.data", prefix, index );
//            final String  desc    = properties.getProperty( descKey );
//            final String  data    = properties.getProperty( dataKey );
//
//            if( (desc != null) && (data != null) ) {
//                filterEntries.add( new FilterEntry( desc, data ) );
//                }
//            else {
//                LOGGER.info( "Can't find :" +  descKey );
//                LOGGER.info( "or         :" +  dataKey );
//                LOGGER.info( "desc :" + desc );
//                LOGGER.info( "data :" + data );
//                break;
//                }
//            }
//    }
}
