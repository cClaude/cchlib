package cx.ath.choisnet.lang.reflect;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Factory for {@link MappableBuilder}
 *
 * @see Mappable
 */
public interface MappableBuilderFactory extends Serializable
{
    /**
     * Returns {@link String} representation of null
     * @return {@link String} representation of null
     */
    public String getStringNullValue();

    /**
     * TODOC
     */
    public String getMessageFormatIteratorEntry();

    /**
     * TODOC
     */
    public String getMessageFormatIterableEntry();

    /**
     * TODOC
     */
    public String getMessageFormatEnumerationEntry();

    /**
     * TODOC
     */
    public String getMessageFormatArrayEntry();

    /**
     * TODOC
     */
    public String getMessageFormatMethodName();

    /**
     * TODOC
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
