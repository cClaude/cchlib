package com.googlecode.cchlib.xutil.google.googlecontact.analyser;


public interface AnalyserCustomTypeMethodContener extends AnalyserMethodContener
{
    @SuppressWarnings({"squid:RedundantThrowsDeclarationCheck"})
    boolean isSuffixValid( String suffix ) throws GoogleContactCSVException;

    TypeInfo getTypeInfo();
}
