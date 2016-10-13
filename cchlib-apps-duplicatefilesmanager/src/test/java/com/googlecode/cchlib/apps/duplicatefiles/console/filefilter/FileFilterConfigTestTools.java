package com.googlecode.cchlib.apps.duplicatefiles.console.filefilter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class FileFilterConfigTestTools
{
    static final String STR1 = "Str1";
    static final String STR2 = "Str2";
    static final String PATH1 = "unix/Path1";
    static final String PATH2 = "windows\\Path2";

    static final String JSON_SIMPLE_IN1 = "{\"excludeNames\":[\"Str2\",\"Str1\"]}";
    static final String JSON_FULL_OUT1  = "{\"excludeNames\":[\"Str2\",\"Str1\"],\"excludePaths\":null}";
    static final String JSON_FULL_OUT2  = "{\"excludeNames\":[\"Str2\",\"Str1\"],\"excludePaths\":[\"windows\\\\Path2\",\"unix/Path1\"]}";

    private FileFilterConfigTestTools()
    {
        // static
    }

    @SafeVarargs
    private static <T> Collection<T> toCollection( final T...values )
    {
        final Set<T> collection = new HashSet<>();

        for( final T value : values ) {
            collection.add( value );
        }

        return collection;
    }

    static void populateNames( final CustomFileFilterConfig config )
    {
        config.setExcludeNames( toCollection( STR1, STR2 ) );
    }

    static void populatePaths( final CustomFileFilterConfig config )
    {
        config.setExcludePaths( toCollection( PATH1, PATH2 ) );
    }

    public static CustomFileFilterConfig newFileFilterConfigTestTools()
    {
        final CustomFileFilterConfig cffc = new CustomFileFilterConfig();

        populateNames( cffc );
        populatePaths( cffc );

        return cffc;
    }
}
