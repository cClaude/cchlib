package cx.ath.choisnet.lang.reflect;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Factory for {@link MappableBuilder}
 *
 * @deprecated use {@link com.googlecode.cchlib.util.mappable.MappableBuilderFactory} instead 
 */
@Deprecated
public interface MappableBuilderFactory extends Serializable
{
    /**
     * Returns {@link String} representation of null
     * @return {@link String} representation of null
     */
    public String getStringNullValue();

    /**
     */
    public String getMessageFormatIteratorEntry();

    /**
     */
    public String getMessageFormatIterableEntry();

    /**
     */
    public String getMessageFormatEnumerationEntry();

    /**
     */
    public String getMessageFormatArrayEntry();

    /**
     */
    public String getMessageFormatMethodName();

    /**
     */
    public Set<Class<?>> getClasses();

    /**
     * Returns an unmodifiable {@link Set} of {@link MappableItem}
     * describe how to select item to add in the final map.
     * @return an unmodifiable {@link Set} of {@link MappableItem}
     */
    public Set<MappableItem> getMappableItemSet();

    /**
     * {@link Pattern} use to select method names to add into map result
     */
    public Pattern getMethodesNamePattern();
}
