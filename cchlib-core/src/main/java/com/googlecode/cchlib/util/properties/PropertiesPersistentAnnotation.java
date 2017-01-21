package com.googlecode.cchlib.util.properties;

import java.lang.reflect.Member;

@SuppressWarnings("squid:S00119")
//Not public
interface PropertiesPersistentAnnotation<E,METHOD_OR_FIELD extends Member>
    extends PropertiesPopulatorAnnotation<E,METHOD_OR_FIELD>
{
    // empty
}
