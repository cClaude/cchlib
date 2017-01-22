package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Member;

@SuppressWarnings("squid:S00119") // naming convention
//Not public
interface PersistentAnnotation<E,METHOD_OR_FIELD extends Member>
    extends PopulatorAnnotation<E,METHOD_OR_FIELD>
{
    // empty
}
