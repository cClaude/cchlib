package cx.ath.choisnet.servlet.debug.impl;

import cx.ath.choisnet.lang.reflect.MappableHelper;
import cx.ath.choisnet.lang.reflect.MappableHelperDefaultFactory;
import cx.ath.choisnet.lang.reflect.MappableHelperFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Doc
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
    public static MappableHelperFactory getMappableHelperFactory()
    {
        return (new MappableHelperDefaultFactory())
            .setMethodesNamePattern("(get|is).*")
            .addClasses(new Class[] { Object.class })
            .addAttributes(new MappableHelper.Attributes[] { 
                MappableHelper.Attributes.ALL_PRIMITIVE_TYPE, 
                MappableHelper.Attributes.DO_ARRAYS, 
                MappableHelper.Attributes.DO_ITERATOR, 
                MappableHelper.Attributes.DO_ITERABLE, 
                MappableHelper.Attributes.DO_ENUMERATION, 
                MappableHelper.Attributes.DO_RECURSIVE
                });
    }

    private static final Map<String,String> _toMap(Object object)
    {
        return InfosServletDisplayImpl2._toMap(
                InfosServletDisplayImpl2.getMappableHelperFactory(), 
                object
                );
    }

    private static final Map<String,String> _toMap(
                final MappableHelperFactory   factory, 
                final Object                  object
                )
    {
        return InfosServletDisplayImpl2._toMap(new MappableHelper(factory), object);
    }

    private static final Map<String,String> _toMap(
                MappableHelper  aMappableHelper, 
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
