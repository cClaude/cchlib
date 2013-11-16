package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

import java.io.File;


/**
 * Each search option tab that implements FindFilterFactory defines an
 * inner class that implements FindFilter. When a search is started
 * the search panel invokes createFindFilter() on each panel that
 * implements FindFilterFactory, thus causing the panel to create
 * a FindFilter object that implements its search settings.
 * */
interface FindFilter
{
    boolean accept( File f, FindProgressCallback monitor );
}
