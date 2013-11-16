package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.io.File;

interface FindProgressCallback
{
    /**
        Should be called by all time-consuming search filters at a reasonable
        interval. Allows the search controller to report progress and to
        abort the search in a clean and timely way.
        @param filter FindFilter reporting the progress
        @param file the file being searched
        @param current current "location" of search
        @param total maximum value
        @return true if search should continue, false to abort
    */
    boolean reportProgress( FindFilter filter, File file, long current, long total);
}
