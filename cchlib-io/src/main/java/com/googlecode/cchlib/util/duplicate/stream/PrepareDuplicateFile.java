package com.googlecode.cchlib.util.duplicate.stream;

import com.googlecode.cchlib.NeedDoc;
import java.io.File;
import java.util.Map;
import java.util.Set;

@NeedDoc
public interface PrepareDuplicateFile {

    @NeedDoc
    Map<Long, Set<File>> compute();

}
