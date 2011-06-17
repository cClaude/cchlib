package cx.ath.choisnet.lang.reflect;

import java.util.Map;

/**
 * Basic implementation for {@link Mappable}
 */
public abstract class AbstractMappable
    implements Mappable
{
    /**
     * Use default, to create Map. Call {@link #getObjectToMap()} to
     * identify object that should be mapped.
     * <p>
     * Use {@link #getObjectToMap()} to identify object to map, and
     * use {@link #createMappableBuilderFactory()} to
     * <p>
     */
    @Override
    public Map<String,String> toMap()
    {
        MappableBuilder mappableHelper = new MappableBuilder( createMappableBuilderFactory() );

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
     * Create a default MappableBuilderFactory.
     * Default implementation is based on {@link MappableBuilder#createMappableBuilderFactory()}
     *
     * @return a {@link MappableHelperFactory}
     */
    protected MappableBuilderFactory createMappableBuilderFactory()
    {
//        return (new MappableBuilderDefaultFactory())
//                    .setMethodesNamePattern("(get|is).*")
//                    .add( String.class, File.class, URL.class )
//                    .add( MappableItem.ALL_PRIMITIVE_TYPE );
        return MappableBuilder.createMappableBuilderFactory();
    }
}
