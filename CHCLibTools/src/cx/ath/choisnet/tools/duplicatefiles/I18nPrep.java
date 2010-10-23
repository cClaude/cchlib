/**
 * 
 */
package cx.ath.choisnet.tools.duplicatefiles;

import java.io.File;
import java.io.IOException;
import cx.ath.choisnet.i18n.builder.I18nPropertyResourceBundleAutoUpdate;

/**
 * 
 * @author Claude CHOISNET
 */
public class I18nPrep 
{
    public static void main( String[] args ) throws IOException
    {
//        // Default language !
//        I18nAutoUpdateInterface i18Builder = new I18nSimpleResourceBundle(
//                I18nBundle.getMessagesBundle()
//                );
        
        //
        DuplicateFilesFrame duplicateFilesFrame = new DuplicateFilesFrame();
        
//        // Build a quick list of them
//        ObjectToI18n<?>[] toI18n = {
//                new ObjectToI18n<DuplicateFilesFrame>(
//                        duplicateFilesFrame,
//                        duplicateFilesFrame.getClass()
//                        ),
//                };

        I18nPropertyResourceBundleAutoUpdate autoI18n = I18nBundle.getI18nPropertyResourceBundleAutoUpdate();
        File outputFile = new File( 
                new File(".").getAbsoluteFile(), 
                I18nBundle.getMessagesBundle()
                );
        autoI18n.setOutputFile( outputFile );
        
        duplicateFilesFrame.performeI18n( autoI18n );
//        for( ObjectToI18n<?> objectEntry : toI18n ) {
//            autoI18n.performeI18N( 
//                    objectEntry.getObjectToI18n(),
//                    objectEntry.getObjectToI18nClass() 
//                    );
//        }

        autoI18n.close();
        System.out.println( "Done." );
        System.exit( 0 );
    }
    
//    public static class ObjectToI18n<T>
//    {
//        private final T                   objectToI18n;
//        private final Class<? extends T>  objectToI18nClass;
//
//        public ObjectToI18n( T objectToI18n, Class<? extends T>  clazz )
//        {
//            this.objectToI18n       = objectToI18n;
//            this.objectToI18nClass  = clazz;
//        }
//        public T getObjectToI18n()
//        {
//            return objectToI18n;
//        }
//        public Class<? extends T> getObjectToI18nClass()
//        {
//            return objectToI18nClass;
//        }
//    }

}
