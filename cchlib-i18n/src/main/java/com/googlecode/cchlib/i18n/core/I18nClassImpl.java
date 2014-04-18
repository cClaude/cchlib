package com.googlecode.cchlib.i18n.core;

import com.googlecode.cchlib.i18n.AutoI18nConfig;
import com.googlecode.cchlib.i18n.AutoI18nType;
import com.googlecode.cchlib.i18n.EventCause;
import com.googlecode.cchlib.i18n.I18nStringNotAStringException;
import com.googlecode.cchlib.i18n.annotation.I18n;
import com.googlecode.cchlib.i18n.annotation.I18nIgnore;
import com.googlecode.cchlib.i18n.annotation.I18nName;
import com.googlecode.cchlib.i18n.annotation.I18nString;
import com.googlecode.cchlib.i18n.annotation.I18nToolTipText;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactory;
import com.googlecode.cchlib.i18n.core.resolve.I18nKeyFactoryImpl;
import com.googlecode.cchlib.lang.StringHelper;
import java.awt.Component;
import java.awt.Window;
import java.io.Serializable;
import java.lang.reflect.Field;
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

    /** @serial */
    private Class<? extends T> objectToI18nClass;
    private List<I18nField> fieldList = new ArrayList<>();
    private I18nDelegator i18nDelegator;
    private I18nKeyFactory i18nKeyFactory;

    public I18nClassImpl(
        final Class<? extends T>    objectToI18nClass,
        final I18nDelegator         i18nDelegator
        )
    {
        this.objectToI18nClass = objectToI18nClass;
        this.i18nDelegator     = i18nDelegator;
        this.i18nKeyFactory    = new I18nKeyFactoryImpl( objectToI18nClass.getAnnotation( I18nName.class ) );

        Class<?> currentClass = objectToI18nClass;

        while( currentClass != null ) {
            boolean stop = false;

            for( Class<?> c : NOT_HANDLED_CLASS_TYPES ) {
                if( currentClass.equals( c ) ) {
                    // Nothing to customize in default API classes
                    stop = true;
                    break;
                    }
                }

            if( stop ) {
                break;
                }

            //
            //
            //
            Field[] fields;

            if( i18nDelegator.getConfig().contains( AutoI18nConfig.ONLY_PUBLIC ) ) {
                fields = currentClass.getFields();
                }
            else {
                fields = currentClass.getDeclaredFields();
                }

            handleFields( i18nDelegator, fields );

            if( i18nDelegator.getConfig().contains( AutoI18nConfig.DO_DEEP_SCAN )) {
                currentClass = currentClass.getSuperclass();
                }
            else {
                break;
                }
            }
        //?? TODO ?? eventHandle.ignoreSuperClass(?)
    }

    private void handleFields( final I18nDelegator i18nDelegator, Field[] fields )
    {
        for( Field f : fields ) {
            if( f.isSynthetic() ) {
                continue; // ignore member that was introduced by the compiler.
                }
            Class<?> ftype = f.getType();

            if( ftype.isAnnotation() ) {
                i18nDelegator.fireIgnoreField( f, null, EventCause.FIELD_TYPE_IS_ANNOTATION, null);
                continue; // ignore annotations
                }
            if( ftype.isPrimitive() ) {
                i18nDelegator.fireIgnoreField( f, null, EventCause.FIELD_TYPE_IS_PRIMITIVE, null );
                continue; // ignore primitive (numbers)
                }

            boolean skip = false;
            for( Class<?> notHandleClass : NOT_HANDLED_FIELD_TYPES ) {
                if( notHandleClass.isAssignableFrom( ftype ) ) {
                    i18nDelegator.fireIgnoreField( f, null, EventCause.FIELD_TYPE_IS_NOT_HANDLE, null );
                    skip = true;
                    break;
                    }
                }
            if( skip ) {
                continue; // ignore numbers
                }

            // Special handle for tool tip text
            I18nToolTipText toolTipText = f.getAnnotation( I18nToolTipText.class );

            if( toolTipText != null ) {
                // Customize tool tip text of this field (if possible)
                try {
                    addValueToCustomizeForToolTipText( f, toolTipText.id(), toolTipText.method() );
                    }
                catch( MethodProviderSecurityException e ) {
                    i18nDelegator.handleSecurityException( e, f );
                    }
                catch( MethodProviderNoSuchMethodException e ) {
                    i18nDelegator.handleNoSuchMethodException( e, f );
                    }
                 }
            // Field mark has ignore (except for tool tip text)
            I18nIgnore ignoreIt = f.getAnnotation( I18nIgnore.class );

            if( ignoreIt != null ) {
                i18nDelegator.fireIgnoreField( f, null, EventCause.ANNOTATION_I18N_IGNORE_DEFINE, null );
                continue;
                }

            // Add field (if possible)
            addValueToCustomize( f );
            }
    }

    @Override
    public Class<? extends T> getObjectToI18nClass()
    {
        return objectToI18nClass;
    }

    private void addValueToCustomizeForToolTipText(
        final Field  f,
        final String id,
        final String method
        ) throws MethodProviderNoSuchMethodException, MethodProviderSecurityException
    {
        Class<?> fClass = f.getType();

        if( JComponent.class.isAssignableFrom( fClass ) ) {
            MethodContener methodContener;

            if( method.isEmpty() ) {
                methodContener = null;
                }
            else {
                methodContener = MethodProviderFactory.getMethodProvider( i18nDelegator ).getMethods( this.objectToI18nClass, f, method );
                }

            I18nField field = I18nFieldFactory.createI18nFieldToolTipText( this.i18nDelegator, this.i18nKeyFactory, f, id, methodContener );

            this.fieldList.add( field );
            }
        else {
            i18nDelegator.fireIgnoreField( f, id, EventCause.ERR_TOOL_TIP_NOT_A_JCOMPONENT, null );
            }
    }

    private void addValueToCustomize( Field f )
    {
        final I18nString i18nString = f.getAnnotation( I18nString.class );

        boolean alreadyHandle = false;

        if( i18nString != null ) {
            alreadyHandle = true;
            try {
                addValueToCustomizeForString( f, i18nString.id(), i18nString.method() );
                }
            catch( MethodProviderSecurityException e ) {
                i18nDelegator.handleSecurityException( e, f );
                }
            catch( MethodProviderNoSuchMethodException e ) {
                i18nDelegator.handleNoSuchMethodException( e, f );
                }
            catch( I18nStringNotAStringException e ) {
                i18nDelegator.handleI18nSyntaxeException( e, f );
                }
            }

        final I18n i18n = f.getAnnotation( I18n.class );

        if( i18n != null ) {
            if( alreadyHandle ) {
                i18nDelegator.fireIgnoreAnnotation( f, i18n, EventCause.OTHER_ANNOTATION_ALREADY_EXIST );
                }
            else {
                alreadyHandle = true;

                try {
                    AutoI18nType type = this.i18nDelegator.getAutoI18nTypes().lookup( f );
                    addValueToCustomize( f, i18n.id(), i18n.method(), type );
                    }
                catch( MethodProviderSecurityException e ) {
                    i18nDelegator.handleSecurityException( e, f );
                    }
                catch( MethodProviderNoSuchMethodException e ) {
                    i18nDelegator.handleNoSuchMethodException( e, f );
                    }
                }
            }

        if( ! alreadyHandle ) {
            // No annotation, use default process
            AutoI18nType type = this.i18nDelegator.getDefaultAutoI18nTypes().lookup( f );

            if( type != null ) {
                try {
                    addValueToCustomize( f, StringHelper.EMPTY, StringHelper.EMPTY, type );
                    }
                catch( MethodProviderSecurityException e ) {
                    // Should not occur (no reflexion here)
                    i18nDelegator.handleSecurityException( e, f );
                    }
                catch( MethodProviderNoSuchMethodException e ) {
                    // Should not occur (no reflexion here)
                    i18nDelegator.handleNoSuchMethodException( e, f );
                    }
                }
            else {
                i18nDelegator.fireIgnoreField( f, null, EventCause.NOT_HANDLED, null );
                }
          }
    }

    private void addValueToCustomizeForString( Field f, String id, String method )
        throws MethodProviderSecurityException, MethodProviderNoSuchMethodException, I18nStringNotAStringException
    {
        // Check if field is a String
        if( !String.class.isAssignableFrom( f.getType() ) && !String[].class.isAssignableFrom( f.getType() ) ) {

            if( LOGGER.isTraceEnabled() ) {
                boolean res1  = String.class.isAssignableFrom( f.getType() );
                boolean res2  = String[].class.isAssignableFrom( f.getType() );

                LOGGER.trace( "*** Syntaxe error " + f.getType()
                        + " : res1 = " + res1
                        + " * res2 = " + res2,
                        new Exception()
                        );
                }

            throw new I18nStringNotAStringException( f );
        }

        MethodContener methodContener;

        if( method.isEmpty() ) {
            methodContener = null;
            }
        else {
            methodContener = MethodProviderFactory.getMethodProvider( i18nDelegator ).getMethods( objectToI18nClass, f, method );
            }

        I18nField field = I18nFieldFactory.createI18nStringField( this.i18nDelegator, this.i18nKeyFactory, f, id, methodContener );
        this.fieldList.add( field );
    }

    private void addValueToCustomize(
        final Field         f,
        final String        id,
        final String        method,
        final AutoI18nType  autoI18nType
        ) throws MethodProviderNoSuchMethodException, MethodProviderSecurityException
    {
        MethodContener methodContener;

        if( method.isEmpty() ) {
            methodContener = null;
            }
        else {
            methodContener = MethodProviderFactory.getMethodProvider( i18nDelegator ).getMethods( objectToI18nClass, f, method );
            }

        this.fieldList.add(
                I18nFieldFactory.createI18nField( this.i18nDelegator, this.i18nKeyFactory, f, id, methodContener, autoI18nType )
                );
    }

    @Override
    public Iterator<I18nField> iterator()
    {
        return this.fieldList.iterator();
    }
}
