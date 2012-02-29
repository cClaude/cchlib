package com.googlecode.cchlib.swing.filechooser.accessory.findaccessory;

/**
Implemented by each search option panel. Each panel is responsible for
creating a FindFilter object that implements the search criteria
specified by its user interface.
*/
interface FindFilterFactory
{
    public FindFilter createFindFilter();
}
