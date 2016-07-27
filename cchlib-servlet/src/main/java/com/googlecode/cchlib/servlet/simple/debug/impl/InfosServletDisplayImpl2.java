package com.googlecode.cchlib.servlet.simple.debug.impl;

import java.util.HashMap;
import java.util.Map;
import com.googlecode.cchlib.util.mappable.DefaultMappableBuilderFactory;
import com.googlecode.cchlib.util.mappable.MappableBuilder;
import com.googlecode.cchlib.util.mappable.MappableBuilderFactory;
import com.googlecode.cchlib.util.mappable.MappableItem;

/**
 * TODOC
 */
public class InfosServletDisplayImpl2 extends InfosServletDisplayImpl
{
    /**
     * TODOC
     *
     * @param title
     * @param anchorName
     * @param object
     */
    public InfosServletDisplayImpl2(
            final String title,
            final String anchorName,
            final Object object
            )
    {
        super(
            title,
            anchorName,
            InfosServletDisplayImpl2._toMap(object)
            );
    }

    /**
     *
     * TODOC
     */
    public static MappableBuilderFactory getMappableBuilderFactory()
    {
        return new DefaultMappableBuilderFactory()
            .setMethodesNamePattern("(get|is).*")
            .add( Object.class )
            .add(
                    MappableItem.ALL_PRIMITIVE_TYPE,
                    MappableItem.DO_ARRAYS,
                    MappableItem.DO_ITERATOR,
                    MappableItem.DO_ITERABLE,
                    MappableItem.DO_ENUMERATION,
                    MappableItem.DO_RECURSIVE
                    );
    }

    private static final Map<String,String> _toMap(Object object)
    {
        return InfosServletDisplayImpl2._toMap(
                InfosServletDisplayImpl2.getMappableBuilderFactory(),
                object
                );
    }

    private static final Map<String,String> _toMap(
                final MappableBuilderFactory   factory,
                final Object                  object
                )
    {
        return InfosServletDisplayImpl2._toMap(new MappableBuilder(factory), object);
    }

    private static final Map<String,String> _toMap(
            MappableBuilder aMappableHelper,
            Object          object
            )
    {
        if(object == null) {
            //throw new NullPointerException("'object' parameter is null");
            return new HashMap<String,String>();
            }
        else {
            return aMappableHelper.toMap(object);
            }
    }
}
