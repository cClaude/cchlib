package com.googlecode.cchlib.util.duplicate;

import java.io.File;
import com.googlecode.cchlib.NeedDoc;

/**
 * @since 4.2
 */
@NeedDoc
public interface DFFPass1 {

    void addFile( File file );

    void addFiles( Iterable<File> files );

}
