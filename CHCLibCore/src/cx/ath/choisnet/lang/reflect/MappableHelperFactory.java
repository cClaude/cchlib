package cx.ath.choisnet.lang.reflect;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * TODO: Doc!
 * 
 * @author Claude CHOISNET
 */
public interface MappableHelperFactory extends java.io.Serializable
{
    
    /** 
     * TODO: Doc! 
     */
    public String getStringNullValue();

    /** 
     * TODO: Doc! 
     */
    public String getMessageFormatIteratorEntry();

    /** 
     * TODO: Doc! 
     */
    public String getMessageFormatIterableEntry();

    /** 
     * TODO: Doc! 
     */
    public String getMessageFormatEnumerationEntry();

    /** 
     * TODO: Doc! 
     */
    public String getMessageFormatArrayEntry();

    /** 
     * TODO: Doc! 
     */
    public String getMessageFormatMethodName();

    /** 
     * TODO: Doc! 
     */
    public Set<Class<?>> getClasses();
    
    /** 
     * TODO: Doc! 
     */
    public EnumSet<MappableHelper.Attributes> getAttributes();

    /** 
     * TODO: Doc! 
     */
    public Pattern getMethodesNamePattern();

}
