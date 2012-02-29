package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.lang.reflect.DefaultMappableBuilderFactory;
import cx.ath.choisnet.lang.reflect.MappableBuilder;
import cx.ath.choisnet.lang.reflect.MappableBuilderFactory;
import cx.ath.choisnet.lang.reflect.MappableItem;
import java.util.HashMap;
import java.util.Map;

/**
 * TODOC
 *
 * @author Claude CHOISNET
 */
public class InfosServletDisplayImpl2 extends InfosServletDisplayImpl
{
    /**
     * TODO: Doc
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
     * TODO: Doc
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
