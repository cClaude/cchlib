package com.googlecode.cchlib.i18n.core;

import java.awt.Component;
import java.awt.Window;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.EventCause;
import com.googlecode.cchlib.i18n.I18nStringIsStaticException;
import com.googlecode.cchlib.i18n.I18nStringNotAStringException;
import com.googlecode.cchlib.i18n.I18nSyntaxeException;
import com.googlecode.cchlib.i18n.MethodProviderNoSuchMethodException;
import com.googlecode.cchlib.i18n.MethodProviderSecurityException;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactoryImpl;
import com.googlecode.cchlib.lang.DebugException;
import com.googlecode.cchlib.lang.StringHelper;

class I18nClassImpl<T> implements I18nClass<T>, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( I18nClassImpl.class );

    /** Array well know std API classes : do not use reflexion on theses classes */
    /*package*/ static final Class<?>[] NOT_HANDLED_CLASS_TYPES = {
        Object.class,
        JFrame.class,
        JDialog.class,
        JWindow.class,
        Window.class,
        JPanel.class,
        Component.class,
        JComponent.class,
        };
    private static final Class<?>[] NOT_HANDLED_FIELD_TYPES = {
        Number.class,
        EnumSet.class,
        Logger.class,
    };

    private final Class<? extends T> objectToI18nClass;
    private final List<I18nField> fieldList = new ArrayList<>();
    private final I18nDelegator i18nDelegator;
    private final I18nKeyFactory i18nKeyFactory;

    public I18nClassImpl(
        final Class<? extends T>    objectToI18nClass,
        final I18nDelegator         i18nDelegator
        )
    {
        this.objectToI18nClass = objectToI18nClass;
        this.i18nDelegator     = i18nDelegator;
        this.i18nKeyFactory    = new I18nKeyFactoryImpl( objectToI18nClass );

        Class<?> currentClass = objectToI18nClass;

        while( currentClass != null ) {
            boolean stop = false;

            for( final Class<?> c : NOT_HANDLED_CLASS_TYPES ) {
                if( currentClass.equals( c ) ) {
                    // Nothing to customize in default API classes
                    stop = true;
                    break;
                    }
                }

            if( stop ) {
                break;
                }

            handleFields( i18nDelegator, currentClass );

            if( i18nDelegator.getConfig().contains( AutoI18nConfig.DO_DEEP_SCAN )) {
                currentClass = currentClass.getSuperclass();
                }
            else {
                break;
                }
            }
        //?? TODO ?? eventHandle.ignoreSuperClass(?)
    }

    private Field[] getFields( final I18nDelegator i18nDelegator, final Class<?> currentClass )
    {
        final Field[] fields;

        if( i18nDelegator.getConfig().contains( AutoI18nConfig.ONLY_PUBLIC ) ) {
            fields = currentClass.getFields();
            }
        else {
            fields = currentClass.getDeclaredFields();
            }

        return fields;
    }

    private void handleFields( final I18nDelegator i18nDelegator, final Class<?> currentClass )
    {
        final Field[] fields = getFields( i18nDelegator, currentClass );

        for( final Field field : fields ) {
            if( field.isSynthetic() ) {
                continue; // ignore member that was introduced by the compiler.
                }
            final Class<?> ftype = field.getType();

            if( ftype.isAnnotation() ) {
                i18nDelegator.fireIgnoreField( field, null, EventCause.FIELD_TYPE_IS_ANNOTATION, null);
                continue; // ignore annotations
                }
            if( ftype.isPrimitive() ) {
                i18nDelegator.fireIgnoreField( field, null, EventCause.FIELD_TYPE_IS_PRIMITIVE, null );
                continue; // ignore primitive (numbers)
                }

            boolean skip = false;
            for( final Class<?> notHandleClass : NOT_HANDLED_FIELD_TYPES ) {
                if( notHandleClass.isAssignableFrom( ftype ) ) {
                    i18nDelegator.fireIgnoreField( field, null, EventCause.FIELD_TYPE_IS_NOT_HANDLE, null );
                    skip = true;
                    break;
                    }
                }
            if( skip ) {
                continue; // ignore numbers
                }

            // Special handle for tool tip text
            final I18nToolTipText toolTipText = field.getAnnotation( I18nToolTipText.class );

            if( toolTipText != null ) {
                // Customize tool tip text of this field (if possible)
                addValueToCustomizeForToolTipTextAndHandleErrors( i18nDelegator, field, toolTipText );
                 }
            // Field mark has ignore (except for tool tip text)
            final I18nIgnore ignoreIt = field.getAnnotation( I18nIgnore.class );

            if( ignoreIt != null ) {
                i18nDelegator.fireIgnoreField( field, null, EventCause.ANNOTATION_I18N_IGNORE_DEFINE, null );
                continue;
                }

            // Add field (if possible)
            addValueToCustomize( field );
            }
    }

    private void addValueToCustomizeForToolTipTextAndHandleErrors( final I18nDelegator i18nDelegator, final Field field, final I18nToolTipText toolTipText )
    {
        try {
            addValueToCustomizeForToolTipText( field, toolTipText.id(), toolTipText.method() );
            }
        catch( final MethodProviderSecurityException e ) {
            i18nDelegator.handleSecurityException( e, field );
            }
        catch( final MethodProviderNoSuchMethodException e ) {
            i18nDelegator.handleNoSuchMethodException( e, field );
            }
        catch( final I18nSyntaxeException e ) {
            i18nDelegator.handleI18nSyntaxeException( e, field );
            }
    }

    @Override
    public Class<? extends T> getObjectToI18nClass()
    {
        return objectToI18nClass;
    }

    private void addValueToCustomizeForToolTipText(
        final Field  field,
        final String id,
        final String method
        ) throws MethodProviderNoSuchMethodException, MethodProviderSecurityException, I18nSyntaxeException
    {
        final Class<?> fClass = field.getType();

        if( JComponent.class.isAssignableFrom( fClass ) ) {
            MethodContener methodContener;

            if( method.isEmpty() ) {
                methodContener = null;
                }
            else {
                methodContener = MethodProviderFactory.getMethodProvider( i18nDelegator ).getMethods( this.objectToI18nClass, field, method );
                }

            final I18nField i18nfield = I18nFieldFactory.createI18nFieldToolTipText( this.i18nDelegator, this.i18nKeyFactory, field, id, methodContener );

            this.fieldList.add( i18nfield );
            }
        else {
            i18nDelegator.fireIgnoreField( field, id, EventCause.ERR_TOOL_TIP_NOT_A_JCOMPONENT, null );
            }
    }

    private void addValueToCustomize( final Field field )
    {
        final I18nString i18nString = field.getAnnotation( I18nString.class );

        boolean alreadyHandle = false;

        if( i18nString != null ) {
            alreadyHandle = true;

            addValueToCustomizeForStringAndHandleErrors( field, i18nString );
            }

        final I18n i18n = field.getAnnotation( I18n.class );

        if( i18n != null ) {
            if( alreadyHandle ) {
                i18nDelegator.fireIgnoreAnnotation( field, i18n, EventCause.OTHER_ANNOTATION_ALREADY_EXIST );
                }
            else {
                alreadyHandle = true;

                addValueToCustomizeAndHandleErrors( field, i18n );
                }
            }

        if( ! alreadyHandle ) {
            // No annotation, use default process
            final AutoI18nType type = this.i18nDelegator.getDefaultAutoI18nTypes().lookup( field );

            if( type != null ) {
                addValueToCustomizeAndHandleErrors( field, type );
                }
            else {
                i18nDelegator.fireIgnoreField( field, null, EventCause.NOT_HANDLED, null );
                }
          }
    }

    private void addValueToCustomizeAndHandleErrors( final Field field, final AutoI18nType type )
    {
        try {
            addValueToCustomize( field, StringHelper.EMPTY, StringHelper.EMPTY, type );
            }
        catch( final MethodProviderSecurityException e ) {
            // Should not occur (no reflexion here)
            i18nDelegator.handleSecurityException( e, field );
            }
        catch( final MethodProviderNoSuchMethodException e ) {
            // Should not occur (no reflexion here)
            i18nDelegator.handleNoSuchMethodException( e, field );
            }
        catch( final I18nSyntaxeException e ) {
            i18nDelegator.handleI18nSyntaxeException( e, field );
            }
    }

    private void addValueToCustomizeAndHandleErrors( final Field field, final I18n i18n )
    {
        try {
            final AutoI18nType type = this.i18nDelegator.getAutoI18nTypes().lookup( field );
            addValueToCustomize( field, i18n.id(), i18n.method(), type );
            }
        catch( final MethodProviderSecurityException e ) {
            i18nDelegator.handleSecurityException( e, field );
            }
        catch( final MethodProviderNoSuchMethodException e ) {
            i18nDelegator.handleNoSuchMethodException( e, field );
            }
        catch( final I18nSyntaxeException e ) {
            i18nDelegator.handleI18nSyntaxeException( e, field );
            }
    }

    private void addValueToCustomizeForStringAndHandleErrors( final Field field, final I18nString i18nString )
    {
        try {
            addValueToCustomizeForString( field, i18nString.id(), i18nString.method() );
            }
        catch( final MethodProviderSecurityException e ) {
            i18nDelegator.handleSecurityException( e, field );
            }
        catch( final MethodProviderNoSuchMethodException e ) {
            i18nDelegator.handleNoSuchMethodException( e, field );
            }
        catch( final I18nSyntaxeException e ) {
            i18nDelegator.handleI18nSyntaxeException( e, field );
            }
    }

    private void addValueToCustomizeForString( final Field field, final String id, final String methodName ) //
            throws MethodProviderSecurityException, MethodProviderNoSuchMethodException, I18nSyntaxeException
    {
        // Check if field is a String
        if( !String.class.isAssignableFrom( field.getType() ) && !String[].class.isAssignableFrom( field.getType() ) ) {

            if( LOGGER.isTraceEnabled() ) {
                final boolean isAssignableFromString = String.class.isAssignableFrom( field.getType() );
                final boolean isAssignableFromStringArray = String[].class.isAssignableFrom( field.getType() );

                LOGGER.trace( new DebugException( "*** Syntaxe error " + field.getType() + " : isAssignableFromString = " + isAssignableFromString
                        + " * isAssignableFromStringArray = " + isAssignableFromStringArray ) );
            }

            throw new I18nStringNotAStringException( field );
        }

        if( String.class.isAssignableFrom( field.getType() ) ) {
            // A String field should not be static
            if( Modifier.isFinal( field.getModifiers() ) ) {
                throw new I18nStringIsStaticException( field );
            }
        }

        MethodContener methodContener;

        if( methodName.isEmpty() ) {
            methodContener = null;
        } else {
            methodContener = MethodProviderFactory.getMethodProvider( i18nDelegator ).getMethods( objectToI18nClass, field, methodName );
        }

        final I18nField i18nfield = I18nFieldFactory.createI18nStringField( this.i18nDelegator, this.i18nKeyFactory, field, id, methodContener );

        this.fieldList.add( i18nfield );
    }

    private void addValueToCustomize(
        final Field         field,
        final String        id,
        final String        method,
        final AutoI18nType  autoI18nType
        ) throws MethodProviderNoSuchMethodException, MethodProviderSecurityException, I18nSyntaxeException
    {
        MethodContener methodContener;

        if( method.isEmpty() ) {
            methodContener = null;
            }
        else {
            methodContener = MethodProviderFactory.getMethodProvider( i18nDelegator ).getMethods( objectToI18nClass, field, method );
            }

        this.fieldList.add(
                I18nFieldFactory.createI18nField( this.i18nDelegator, this.i18nKeyFactory, field, id, methodContener, autoI18nType )
                );
    }

    @Override
    public Iterator<I18nField> iterator()
    {
        return this.fieldList.iterator();
    }
}
