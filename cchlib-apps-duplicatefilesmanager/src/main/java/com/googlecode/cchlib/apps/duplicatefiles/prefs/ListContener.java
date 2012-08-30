package com.googlecode.cchlib.apps.duplicatefiles.prefs;

/**
 *
 */
public interface ListContener<T> extends Iterable<ListInfo<T>>
{
    /**
     * @return get iterator on content list
     */
    public Iterable<T> getContentIterable();
}
