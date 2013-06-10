package com.googlecode.cchlib.i18n.logging;

//import java.util.EnumSet;
//import java.util.MissingResourceException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import com.googlecode.cchlib.i18n.core.I18nField;
//import com.googlecode.cchlib.i18n.logging.AbstractAutoI18nLoggingExceptionHandler;
//
///**
// * {@link com.googlecode.cchlib.i18n.AutoI18nExceptionHandler} using standard
// * java logging to trace Localization exceptions.
// */
//public class AutoI18nLoggingExceptionHandler
//    extends AbstractAutoI18nLoggingExceptionHandler
//{
//    private static final long serialVersionUID = 1L;
//    private transient static final Logger logger = Logger.getLogger(AutoI18nLoggingExceptionHandler.class.getName());
//    /** @serial */
//    private final Level  level;
//    /** @serial */
//    private EnumSet<Config> config;
//
//    /**
//     * Create object using Logger based on current class
//     * with a level define has {@link Level#WARNING}
//     */
//    public AutoI18nLoggingExceptionHandler()
//    {
//        this( Level.WARNING, null );
//    }
//
//    /**
//     * Create object using giving {@link Logger}
//     *
//     * @param level  Level to use for logging
//     * @param config Configuration
//     */
//    public AutoI18nLoggingExceptionHandler(
//            Level           level,
//            EnumSet<Config> config
//            )
//    {
//        this.level  = level;
//        this.config = config == null ? EnumSet.noneOf( Config.class ) : config;
//    }
//
//    @Override
//    protected void defaultHandle( Exception e, I18nField field )
//    {
//        if( config.contains( Config.SHOW_STACK_TRACE ) ) {
//            logger.log( level, "AutoI18n error", e );
//            }
//        else {
//            logger.log( level, "AutoI18n error : " + e.getMessage() );
//            }
//    }
//
//    private void _handleMissingResourceException_MissingMethodsResolution(
//        final MissingResourceException e,
//        final I18nField                i18nField
//        )
//    {
//        logger.log(
//                level,
//                String.format(
//                    "* MissingResourceException for: %s using [%s] - %s\n",
//                    i18nField.getKey(),
//                    i18nField.getMethods().getBaseName(),
//                    e.getLocalizedMessage()
//                    ),
//                e
//                );
//    }
//
//    private void _handleMissingResourceException(
//        final MissingResourceException e,
//        final I18nField                i18nField
//        )
//    {
//        logger.log(
//                level,
//                "* MissingResourceException for:"
//                    + i18nField.getKey()
//                    + " - "
//                    + e.getLocalizedMessage(),
//                e
//                );
//    }
//
//    @Override
//    public <T> void handleMissingResourceException(
//        final MissingResourceException e,
//        final I18nField                i18nField,
//        final T                        objectToI18n
//        )
//    {
////        final Field       field       = i18nField.getField();
////        final MissingInfo missingInfo = i18nField.getMissingInfo();
////
////        switch( missingInfo.getType() ) {
//        switch( i18nField.getFieldType() ) {
//            case SIMPLE_KEY :
//                _handleMissingResourceException( e, i18nField );
//                break;
//            case LATE_KEY :
//                _handleMissingResourceException( e, i18nField );
//                break;
//            case METHODS_RESOLUTION :
//                _handleMissingResourceException_MissingMethodsResolution( e, i18nField );
//                break;
//            case JCOMPONENT_TOOLTIPTEXT:
//                _handleMissingResourceException( e, i18nField );
//                break;
//            }
//    }
//
//}
