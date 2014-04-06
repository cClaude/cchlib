package com.googlecode.cchlib.xutil.google.googlecontact.analyser;


public interface AnalyserCustomTypeMethodContener extends AnalyserMethodContener {

    boolean isSuffixValid( String suffix ) throws GoogleContactCSVException;

    TypeInfo getTypeInfo();
}
