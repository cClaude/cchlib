package cx.ath.choisnet.lang.reflect;

import java.io.File;
import java.net.URL;
import java.util.Map;
import cx.ath.choisnet.ToDo;

/**
 * Default implementation for {@link Mappable} based on
 *
 * @author Claude CHOISNET
 */
@ToDo(action=ToDo.Action.DOCUMENTATION)
public abstract class AbstractMappable
    implements Mappable
{
    /**
     * Use default, to create Map. Call {@link #getObjectToMap()} to
     * identify object that should be mapped.
     * <p>
     * Use {@link #getObjectToMap()} to identify object to map, and
     * use {@link #getMappableHelperFactory()} to
     * <p>
     */
    @Override
    public Map<String,String> toMap()
    {
//        MappableHelper mappableHelper = new MappableHelper( 
//                (new MappableHelperDefaultFactory())
//                    .setMethodesNamePattern("(get|is).*")
//                    .addClasses(new Class<?>[] { String.class, File.class, URL.class })
//                    .addAttribute( MappableHelper.Attributes.ALL_PRIMITIVE_TYPE )
//                    );
//      return mappableHelper.toMap(this);
        MappableHelper mappableHelper = new MappableHelper( getMappableHelperFactory() );

        return mappableHelper.toMap( getObjectToMap() );
    }
    
    /**
     * Returns object to Map (typically: return this;) but could something else
     * to use delegated object.
     * 
     * @return object to Map
     * @see Mappable#toMap()
     */
    protected abstract Object getObjectToMap();
    
    /**
     * TODO: Doc!
     * 
     * @return
     */
    protected MappableHelperFactory getMappableHelperFactory()
    {
        return (new MappableHelperDefaultFactory())
                    .setMethodesNamePattern("(get|is).*")
                    .addClasses(
                        new Class<?>[] { String.class, File.class, URL.class }
                        )
                    .addAttribute( MappableHelper.Attributes.ALL_PRIMITIVE_TYPE );
    }
}
