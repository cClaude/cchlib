package com.googlecode.cchlib.apps.duplicatefiles.swing.prefs;

/**
 *
 */
public interface ListContener<T> extends Iterable<ListInfo<T>>
{
    /**
     * @return get iterator on content list
     */
    Iterable<T> getContentIterable();
}
