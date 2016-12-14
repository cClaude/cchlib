package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config;

import java.io.File;
import java.io.InputStream;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
        JSONHelper.save( jsonFile, value, JSONHelper.PRETTY_PRINT, Include.NON_NULL );
    }

    public static FiltersConfig load( final InputStream json )
        throws JSONHelperException
    {
        return JSONHelper.load( json, FiltersConfig.class );
    }
}
