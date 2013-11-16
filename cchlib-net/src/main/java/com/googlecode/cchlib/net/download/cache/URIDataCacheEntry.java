package com.googlecode.cchlib.net.download.cache;

import java.util.Date;

public interface URIDataCacheEntry
{

    String getContentHashCode();

    Date getDate();

    String getRelativeFilename();
}
