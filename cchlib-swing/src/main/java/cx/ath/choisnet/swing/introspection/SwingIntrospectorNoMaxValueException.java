/**
 * 
 */
package cx.ath.choisnet.swing.introspection;

import cx.ath.choisnet.lang.introspection.method.IntrospectionItem;

/**
 * @author CC
 *
 */
public class SwingIntrospectorNoMaxValueException
    extends SwingIntrospectorException
{
    private static final long serialVersionUID = 1L;
    
    /** @serial */
    private IntrospectionItem<?> introspectionItem; // NOT SERIALIZABLE

//    /**
//     * @param message
//     * @param cause
//     */
//    public SwingIntrospectorNoMaxValueException( String message, Throwable cause )
//    {
//        super( message, cause );
//    }
//
//    /**
//     * @param message
//     */
//    public SwingIntrospectorNoMaxValueException( String message )
//    {
//        super( message );
//    }

    /**
     * @return the IntrospectionItem
     */
    protected IntrospectionItem<?> getIntrospectionItem()
    {
        return introspectionItem;
    }

    public SwingIntrospectorNoMaxValueException( 
            String               beanName,
            IntrospectionItem<?> iItem
            )
    {
        super( beanName );
        
        this.introspectionItem = iItem;
    }

    @Override
    public String getMessage()
    {
        return super.getMessage()
            + " - " 
            + this.introspectionItem;
    }
}
