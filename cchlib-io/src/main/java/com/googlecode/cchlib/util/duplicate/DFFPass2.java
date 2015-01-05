package com.googlecode.cchlib.util.duplicate;

import com.googlecode.cchlib.NeedDoc;

/**
 * @since 4.2
 */
@NeedDoc
public interface DFFPass2 {

    void addEventListener( DuplicateFileFinderEventListener eventListener );

    void removeEventListener( DuplicateFileFinderEventListener eventListener );

    void find();

}
