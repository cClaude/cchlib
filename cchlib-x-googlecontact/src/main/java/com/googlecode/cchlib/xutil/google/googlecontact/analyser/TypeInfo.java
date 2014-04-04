package com.googlecode.cchlib.xutil.google.googlecontact.analyser;

import java.util.Map;

public interface TypeInfo {

    Map<String, AnalyserMethodContener>           getMethodForStrings();
    Map<String, AnalyserCustomTypeMethodContener> getMethodForCustomType();

    int getParameterCount();
}
