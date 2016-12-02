package com.googlecode.cchlib.tools.downloader.gdai.tumblr;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

@SuppressWarnings({
    "squid:S1149",// Vector required for swing
    "squid:S1452" // No genetics API
    })
public interface Config extends Serializable
{
    Vector<Vector<?>> toVector();

    void setDataFromVector( final Vector<Vector<?>> dataVector );

    List<? extends Entry> getEntries();
}
