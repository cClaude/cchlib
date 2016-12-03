package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config;

import java.io.File;
import java.io.InputStream;
import com.googlecode.cchlib.json.JSONHelper;
import com.googlecode.cchlib.json.JSONHelperException;

public class FiltersConfigFileHelper
{
    private FiltersConfigFileHelper()
    {
        // All static
    }

    public static void save(
        final File          jsonFile,
        final FiltersConfig value
        ) throws JSONHelperException
    {
        JSONHelper.save( jsonFile, value, JSONHelper.PRETTY_PRINT );
    }

    public static FiltersConfig load( final InputStream json )
        throws JSONHelperException
    {
        return JSONHelper.load( json, FiltersConfig.class );
    }
}
