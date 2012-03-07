package com.googlecode.cchlib.util.zip;

import java.util.EventListener;

/**
 * TODOC
 */
public interface UnZipListener extends EventListener
{
	/**
	 * TODOC
	 * @param event
	 */
    public void entryPostProcessing( UnZipEvent event );

	/**
	 * TODOC
	 * @param event
	 */
    public void entryAdded( UnZipEvent event );
}
