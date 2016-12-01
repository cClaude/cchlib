package com.googlecode.cchlib.net.download.cache;

import java.io.IOException;
import java.util.EventListener;

/**
 * {@link URICache}
 * @since 4.1.7
 */
public interface URICacheListener extends EventListener
{
    /**
     * Handle {@link IOException}
     * @param cause Error to handle
     */
    void ioExceptionHandler( IOException cause );

    /**
     * NEEDDOC
     */
    void autoStoreDone();
}
