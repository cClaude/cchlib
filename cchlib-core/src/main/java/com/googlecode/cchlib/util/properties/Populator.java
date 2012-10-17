package com.googlecode.cchlib.util.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Field annotation for {@link PropertiesPopulator}
 *
 * @see PropertiesPopulator
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Populator
{
	/**
	 * TODOC
	 */
	public String defaultValue() default "";

	/**
	 * TODOC
	 */
	public boolean defaultValueIsNull() default false;
}
