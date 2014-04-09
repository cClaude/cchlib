/**
 *
 */
package cx.ath.choisnet.swing.introspection;

import java.awt.Component;
import java.io.Serializable;
import java.lang.reflect.Field;
import org.apache.log4j.Logger;

/**
 * <p>TODOC: Documentation, and some examples.</p>
 *
 * <p>
 * Field name must use following syntax:
 * <i>beanPrefix</i><b>_</b><i>beanName</i>[<b>$root</b>[<b>$</b>[<i>index</i>]]
 * </p>
 * Where:
 * <table class="TableCustomDescription">
 * <tr>
 * <th>field</th>
 * <th>Description</th>
 * <th>Method</th>
 * <th>Comment</th>
 * </tr>
 * <tr>
 * <td><i>beanPrefix</i></td>
 * <td>bean type</td>
 * <td>{@link #getBeanPrefix()}</td>
 * <td>ignored, so could be anything, just by
 * convention it's clearer to have field started by
 * it's type</td>
 * </tr>
 * <tr>
 * <td><b>_</b></td>
 * <td>separator</td>
 * <td></td>
 * <td></td>
 * </tr>
 * <tr>
 * <td><i>beanName</i></td>
 * <td>the bean name (this name is used for matching)</td>
 * <td>{@link #getBeanName()}</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td><b>$root</b></i></td>
 * <td>a tag to define main field, field where data
 * will be read (optional, but need ONE item for each beanName)</td>
 * <td>{@link #isRoot()}</td>
 * <td>if true {@link SwingIntrospector} create a
 * {@link SwingIntrospectorRootItem} otherwise it create a
 * {@link SwingIntrospectorItem}</td>
 * </tr>
 * <tr>
 * <td><i>index</i></td>
 * <td>is an index if a Object bean need to be define by
 * more than frame {@link Component}.</td>
 * <td>{@link #isIndexed()} {@link #getIndex()}</td>
 * <td></td>
 * </tr>
 * </table>
 */
public class Bean implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Bean.class);

    protected final static String SEPARATOR = "_";
    protected final static String ROOT_TAG = "$root";
    protected final static String INDEX_TAG = "$";

    /** @serial */
    private final String fieldName;
    /** @serial */
    private String namePrefix;
    /** @serial */
    private String name;
    /** @serial */
    private String nameSuffix;
    /** @serial */
    private int index = -1;

    /**
     * Build a Bean from a Field
     * @param f Field for contender (typically a Frame or a Dialog)
     * @throws IllegalArgumentException
     */
    public Bean( Field f ) throws IllegalArgumentException
    {
        this.fieldName = f.getName();

        final int begin = this.fieldName.indexOf( SEPARATOR );

        if( begin <= 0 ) {
            LOGGER.warn( "Not bean: " + f.getName()  );
            throw new IllegalArgumentException( f.getName() );
        }
        else { // if( begin > 0 )
            this.namePrefix = this.fieldName.substring( 0, begin );
            String endName  = this.fieldName.substring( begin + 1 );

            int endBeanName = endName.indexOf( ROOT_TAG );

            if( endBeanName < 0 ) {
                // No $root !
                this.name = endName;
            }
            else {
                // It's a root item !
                this.name       = endName.substring( 0, endBeanName );
                this.nameSuffix = endName.substring( endBeanName );

                String eos = endName.substring( endBeanName + ROOT_TAG.length() );

                if( eos.length() == 0 ) {
                    // OK, no more infos
                }
                else if( eos.startsWith( INDEX_TAG )) {
                    this.index = Integer.parseInt( eos.substring( 1 ) );
                }
                else {
                    LOGGER.warn( "Don't kown how to handle: eos(" + eos + ") for " + f );
                }
            }
        }
    }

    /**
     * @return the fieldName
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * @return the prefix
     */
    public String getBeanPrefix()
    {
        return namePrefix;
    }

    /**
     * @return the name
     */
    public String getBeanName()
    {
        return name;
    }

    /**
     * @return the suffix
     */
    public String getBeanSuffix()
    {
        return nameSuffix;
    }

    /**
     * @return the index
     */
    public int getIndex()
    {
        return index;
    }

    public boolean isIndexed()
    {
        return this.index >= 0;
    }

    public boolean isRoot()
    {
        return this.nameSuffix != null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append( "Bean [fieldName=" );
        builder.append( fieldName );
        builder.append( ", namePrefix=" );
        builder.append( namePrefix );
        builder.append( ", name=" );
        builder.append( name );
        builder.append( ", nameSuffix=" );
        builder.append( nameSuffix );
        builder.append( ", index=" );
        builder.append( index );
        builder.append( ']' );

        return builder.toString();
    }

}
