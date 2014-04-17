package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import java.util.Map.Entry;
import java.util.Set;
import javax.swing.ListModel;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFiles;
import com.googlecode.cchlib.util.HashMapSet;

public interface JPanelResultListModel extends ListModel<KeyFiles>
{
    Iterable<KeyFileState> getAllDuplicates();

    HashMapSet<String, KeyFileState> getDuplicateFiles();

    void setDuplicateFiles( HashMapSet<String, KeyFileState> duplicateFiles );
    void setSelectFirstMode( SelectFirstMode selectFirstMode );
    void setSortMode( SortMode sortMode );

    void updateCache();

    void clearKeepDelete();
    void clearSelected();

    SelectFirstMode getSelectFirstMode();

    SortMode getSortMode();

    Set<KeyFileState> getStateSet( String key );

    Set<Entry<String, Set<KeyFileState>>> getStateEntrySet();

    void setKeepDelete( String key, Set<KeyFileState> s );

    void refreshList();
}
