package com.googlecode.cchlib.util.duplicate.stream;

import java.io.File;
import java.util.Map;
import java.util.Set;
import com.googlecode.cchlib.NeedDoc;

@NeedDoc
public interface PrepareDuplicateFile {

    @NeedDoc
    Map<Long, Set<File>> compute();

}
