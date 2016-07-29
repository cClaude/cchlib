package com.googlecode.cchlib.servlet.simple.debug.impl;

import java.util.HashMap;
import java.util.Map;
import com.googlecode.cchlib.util.mappable.DefaultMappableBuilderFactory;
import com.googlecode.cchlib.util.mappable.MappableBuilder;
import com.googlecode.cchlib.util.mappable.MappableBuilderFactory;
import com.googlecode.cchlib.util.mappable.MappableItem;

//NOT public
class InfosServletDisplayImplForGetterAndSetter extends InfosServletDisplayImplForMap
{
    public InfosServletDisplayImplForGetterAndSetter(
            final String title,
            final String anchorName,
            final Object object
            )
    {
        super(
            title,
            anchorName,
            InfosServletDisplayImplForGetterAndSetter.internalToMap(object)
            );
    }

    static MappableBuilderFactory getMappableBuilderFactory()
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

    private static final Map<String,String> internalToMap(final Object object)
    {
        return InfosServletDisplayImplForGetterAndSetter.internalToMap(
                InfosServletDisplayImplForGetterAndSetter.getMappableBuilderFactory(),
                object
                );
    }

    private static final Map<String,String> internalToMap(
                final MappableBuilderFactory   factory,
                final Object                  object
                )
    {
        return InfosServletDisplayImplForGetterAndSetter.internalToMap(new MappableBuilder(factory), object);
    }

    private static final Map<String,String> internalToMap(
            final MappableBuilder aMappableHelper,
            final Object          object
            )
    {
        if(object == null) {
            return new HashMap<>();
            }
        else {
            return aMappableHelper.toMap(object);
            }
    }
}
