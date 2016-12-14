package com.googlecode.cchlib.xutil.google.googlecontact.extension;

import java.util.List;
import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;

public interface SimpifiedBasicEntry {

    BasicEntry getBasicEntry();

    String getType();
    List<String> getValues();

}
