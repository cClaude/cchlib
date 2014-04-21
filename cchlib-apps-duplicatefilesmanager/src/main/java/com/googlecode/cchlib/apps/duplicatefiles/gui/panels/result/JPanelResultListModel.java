package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.result;

import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFiles;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.ListModel;

public interface JPanelResultListModel extends ListModel<KeyFiles>
{
    Iterable<KeyFileState> getAllDuplicates();

    Map<String, Set<KeyFileState>> getDuplicateFiles();

    void setDuplicateFiles( Map<String,Set<KeyFileState>> duplicateFiles );
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
