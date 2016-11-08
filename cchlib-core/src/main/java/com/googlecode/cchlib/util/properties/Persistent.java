package com.googlecode.cchlib.util.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field annotation for {@link PropertiesPopulator} that handle Swing input fields.
 *
 * @see PropertiesPopulator
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface Persistent
{
    /**
     * Default value if field is not define
     */
    public String defaultValue() default "";
}
