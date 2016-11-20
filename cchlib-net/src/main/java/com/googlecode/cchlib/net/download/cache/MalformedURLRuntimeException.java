package com.googlecode.cchlib.net.download.cache;

import java.net.MalformedURLException;

class MalformedURLRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MalformedURLRuntimeException( final MalformedURLException cause )
    {
        super( cause );
    }

}
