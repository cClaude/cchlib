package com.googlecode.cchlib.i18n;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to specify how a Field should be localized
 *
 * @see AutoI18n
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nForce
{
    /**
     * Specify class name to use
     * @return TODOC
     */
    String className() default "";

//    /**
//     * TODOC
//     * @return TODOC
//     */
//    boolean isStatic() default false;
    
//    /**
//     * Must have two methods, with theses signatures:<br/>
//     * public void <b>set<i>methodName</b></i>(Object,String) { ... }
//     * <br/>
//     * public String get'methodName'() { ... }
//     *
//     * @return method name, if not define ("")
//     *         use default process.
//     */
//    String methodName() default "";
    
    /**
     * Returns key name for this field
     * @return key name for this field, if
     *         not define ("") use default process.
     */
    String keyName() default "";
}
