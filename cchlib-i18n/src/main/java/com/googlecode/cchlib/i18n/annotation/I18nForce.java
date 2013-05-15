package com.googlecode.cchlib.i18n.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.googlecode.cchlib.Beta;

/**
 * Annotation to specify how a Field should be localized, default
 * implementation use this annotation to internationalize Swing
 * input components like {@link javax.swing.JTextArea},
 * {@link javax.swing.JTextField}, {@link javax.swing.JEditorPane}
 * or
 * {@link javax.swing.JProgressBar}
 *
 * @see com.googlecode.cchlib.i18n.AutoI18n
 * @see com.googlecode.cchlib.i18n.ForceAutoI18nTypes
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Beta
public @interface I18nForce
{
    /**
     * Specify class name to use
     * @return TODOC
     */
    String className() default "";
    
    /**
     * Returns key name for this field
     * @return key name for this field, if
     *         not define ("") use default process.
     */
    String id() default "";
}
