package com.googlecode.cchlib.xutil.google.googlecontact.analyser;


public interface AnalyserCustomTypeMethodContener extends AnalyserMethodContener {

    boolean checkSuffix( String suffix ) throws GoogleContactCSVException;
    TypeInfo getTypeInfo();
}
