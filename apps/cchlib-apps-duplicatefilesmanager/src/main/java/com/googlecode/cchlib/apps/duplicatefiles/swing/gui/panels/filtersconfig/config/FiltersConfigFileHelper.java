package com.googlecode.cchlib.apps.duplicatefiles.swing.gui.panels.filtersconfig.config;

import java.io.File;
import java.io.InputStream;
import com.googlecode.cchlib.apps.duplicatefiles.common.JSONHelper;
import com.googlecode.cchlib.apps.duplicatefiles.common.JSONHelperException;

public class FiltersConfigFileHelper
{
    private static final boolean PRETTY_JSON = true;

    private FiltersConfigFileHelper()
    {
        // All static
    }

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
}
