package com.googlecode.cchlib.cli.apachecli;

import org.apache.commons.cli.Option;

@SuppressWarnings("squid:S1609") // this not a FunctionalInterface
public interface IsOption
{
    /** @return an initialized cli Option */
    Option getOption();
}
