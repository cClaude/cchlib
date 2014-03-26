package com.googlecode.cchlib.xutil.google.googlecontact.extension;

import com.googlecode.cchlib.xutil.google.googlecontact.types.IMEntry;

public interface SimpifiedIMEntry extends SimpifiedBasicEntry {

    IMEntry getIMEntry();

    String getService();
}
