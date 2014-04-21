package com.googlecode.cchlib.apps.duplicatefiles.gui.panels.search;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.apps.duplicatefiles.FileFilterBuilders;
import com.googlecode.cchlib.apps.duplicatefiles.KeyFileState;

public abstract class JPanelSearching extends JPanelSearchingFilters implements Cancelable{
    enum Pass {
        PASS1,PASS2
    }

    private static final long serialVersionUID = 1L;

    public abstract void startScan( //
            String messageDigestAlgorithm, //
            int messageDigestBufferSize, //
            boolean ignoreEmptyFiles, //
            Collection<File> entriesToScans, //
            Collection<File> entriesToIgnore, //
            FileFilterBuilders fileFilterBuilders, //
            Map<String, Set<KeyFileState>> duplicateFiles //
            );
}
