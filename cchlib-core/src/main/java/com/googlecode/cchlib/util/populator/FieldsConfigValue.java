package com.googlecode.cchlib.util.populator;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Basics implementations for {@link FieldsConfig}
 *
 * @since 4.2
 */
public enum FieldsConfigValue implements FieldsConfig
{
    /**
     * No fields
     */
    NONE( c -> empty() ),

    /**
     * Use {@link Class#getFields()}
     */
    FIELDS( c -> c.getFields() ),

    /**
     * Use {@link Class#getDeclaredFields()}
     */
    DECLARED_FIELDS( c -> c.getDeclaredFields() ),

    /**
     * Use {@link Class#getDeclaredFields()} on current type and all
     * super classes
     */
    ALL_DECLARED_FIELDS( FieldsConfigValue::getRecursiveDeclaredFields ),
    ;
    private static final Field[] EMPTY = new Field[ 0 ];

    private Function<Class<?>,Field[]> function;

    private FieldsConfigValue( final Function<Class<?>,Field[]> function )
    {
        this.function = function;
    }

    @Override
    public Field[] getFields( final Class<?> type )
    {
        return this.function.apply( type );
    }

    private static Field[] empty()
    {
        return EMPTY;
    }

    private static Field[] getRecursiveDeclaredFields( final Class<?> type )
    {
        final Set<Field> allFields = new HashSet<>();

        Class<?> currentClass = type;

        while( currentClass != null ) {
            final Field[] fields = currentClass.getDeclaredFields();

            for( final Field field : fields ) {
                allFields.add( field );
            }

            currentClass = currentClass.getSuperclass();
        }

        return allFields.toArray( new Field[ allFields.size() ] );
    }
}
