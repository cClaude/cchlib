/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/system/impl/win32/EnvArcRegWin32.java
** Description   :
** Encodage      : ANSI
**
**  3.02.017 2006.06.28 Claude CHOISNET - Version initiale
**                      Bas� sur le code cx.ath.choisnet.system.win32.EnvArc
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.system.impl.win32.EnvArcRegWin32
**
*/
package cx.ath.choisnet.system.impl.win32;

//import cx.ath.choisnet.system.EnvArc;
//import com.ice.jni.registry.RegistryKey;
//import com.ice.jni.registry.Registry;
//import com.ice.jni.registry.RegistryValue;
//import com.ice.jni.registry.RegStringValue;
//import com.ice.jni.registry.RegDWordValue;
//import com.ice.jni.registry.RegistryValue;
//
///**
//**
//** @author Claude CHOISNET
//** @since   3.02.017
//** @version 3.02.017
//*/
//public class EnvArcRegWin32
//    implements EnvArc
//{
//
///** */
//private final static String SYSTEM_ENVIRONMENT_BASE = "HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment";
//
///**
//**
//*/
//public EnvArcRegWin32() // ------------------------------------------------
//{
// // empty
//}
//
///**
//**
//**
//** @param varname   Nom de la variable
//**
//** @return le contenu de la variable 'varname'
//**         ou null elle n'existe pas.
//*/
//public String getVar( final String varname ) // ---------------------------
//    throws cx.ath.choisnet.system.EnvArcException
//{
// try {
//    return getRegString( SYSTEM_ENVIRONMENT_BASE, varname );
//    }
// catch( Exception e ) {
//    throw new cx.ath.choisnet.system.EnvArcException( e );
//    }
//}
//
///**
//**
//**
//** @param varname   Nom de la variable
//** @param value     Valeur � affecter � la variable
//**
//**
//*/
//public void setVar( final String varname, final String value ) // ---------
//    throws
//        cx.ath.choisnet.system.EnvArcException,
//        UnsupportedOperationException
//{
// try {
//    setRegString( SYSTEM_ENVIRONMENT_BASE, varname, value );
//    }
// catch( Exception e ) {
//    throw new cx.ath.choisnet.system.EnvArcException( e );
//    }
//}
//
///**
//** M�thode sp�cifique Win32<br/>
//**
//** @param regKey
//** @param regValue
//**
//*/
//protected RegistryValue getRegistryValue( // ------------------------------
//    final RegKey    regKey,
//    final String    regValue
//    )
//{
// try {
//    return regKey.getRegistryKeyReadOnly().getValue( regValue );
//    }
// catch( com.ice.jni.registry.NoSuchKeyException e ) {
//    // logger.error( "getRegistryValue( \"" + regKey + "\", \"" + regValue + "\" ) : " + e );
//
//    return null;
//    }
// catch( com.ice.jni.registry.RegistryException e ) {
//    // logger.error( "getRegistryValue( \"" + regKey + "\", \"" + regValue + "\" )", e );
//
//    return null;
//    }
//}
//
///**
//** M�thode sp�cifique Win32<br/>
//**
//** @param regName
//** @param regValue
//**
//** @return le contenu de la cl� si elle existe et si c'est une cha�ne.
//**
//** @see #getRegInteger(String,String)
//** @see #setRegString(String,String,String)
//*/
//public String getRegString( // --------------------------------------------
//    final String regName,
//    final String regValue
//    )
//{
//
// try {
//    RegistryValue aValue = getRegistryValue( new RegKey( regName ), regValue );
//
//    if( aValue != null ) {
//        return ((RegStringValue)aValue).getData();
//        }
//     else {
//        // logger.warn( "getRegString( \"" + regName + "\", \"" + regValue + "\" ) : valeur non trouv�e" );
//        }
//    }
// catch( ClassCastException e ) {
//    // logger.error( "getRegString( \"" + regName + "\", \"" + regValue + "\" ) : ", e );
//    }
//
// return null;
//}
//
///**
//** M�thode sp�cifique Win32<br/>
//**
//**
//** @param regName
//** @param regValue
//** @param regData
//**
//**
//** @see #getRegString(String,String)
//*/
//public void setRegString( // ----------------------------------------------
//    final String    regName,
//    final String    regValue,
//    final String    regData
//    )
//{
// final RegKey regKey = new RegKey( regName );
//
// try {
//    final RegistryKey myKey = regKey.getRegistryKeyReadWrite();
//
//    myKey.setValue( new RegStringValue( myKey, regValue, regData ) );
//    }
// catch( com.ice.jni.registry.RegistryException e ) {
//    final String msg = "setRegString( \"" + regName + "\", \"" + regValue + "\", \"" + regData + "\" ) : ";
//
//    // logger.error( msg, e );
//
//    throw new RuntimeException( msg, e );
//    }
//
//}
//
///**
//** M�thode sp�cifique Win32<br/>
//**
//**
//** @param regName
//** @param regValue
//**
//** @see #getRegString(String,String)
//** @see #setRegInteger(String,String,int)
//*/
//public Integer getRegInteger( // ------------------------------------------
//    final String regName,
//    final String regValue
//    )
//{
// try {
//    RegistryValue aValue = getRegistryValue( new RegKey( regName ), regValue );
//
//    if( aValue != null ) {
//        return new Integer( ((RegDWordValue)aValue).getData() );
//        }
//    }
// catch( ClassCastException e ) {
//    // logger.error( "getRegInteger( \"" + regName + "\", \"" + regValue + "\" ) : ", e );
//    }
//
// return null;
//}
//
///**
//** M�thode sp�cifique Win32<br/>
//**
//**
//** @param regName
//** @param regValue
//** @param regData
//**
//**
//** @see #getRegInteger(String,String)
//** @see #setRegString(String,String,String)
//*/
//public void setRegInteger( // ---------------------------------------------
//    final String    regName,
//    final String    regValue,
//    final int       regData
//    )
//{
// final RegKey regKey = new RegKey( regName );
//
// try {
//    final RegistryKey myKey = regKey.getRegistryKeyReadWrite();
//
//    myKey.setValue( new RegDWordValue( myKey, regValue, RegDWordValue.REG_DWORD, regData ) );
//    }
// catch( com.ice.jni.registry.RegistryException e ) {
//    final String msg = "setRegString( \"" + regName + "\", \"" + regValue + "\", \"" + regData + "\" ) : ";
//
//    // logger.error( msg, e );
//
//    throw new RuntimeException( msg, e );
//    }
//}
//
//    /**
//    **
//    */
//    class RegKey
//    {
//        final String        regKeyName;
//        final RegistryKey   registryTop;
//        final String        regName;
//
//        /**
//        **
//        */
//        public RegKey( final String regKeyName ) // - - - - - - - - - - - -
//        {
//            this.regKeyName = regKeyName;
//
//            int separator = regKeyName.indexOf( "\\" );
//
//            if( separator <= 0 ) {
//                throw new RuntimeException( "Bad format : \"" + regKeyName + "\"" );
//                }
//
//            final String begin  = regKeyName.substring( 0, separator ).toUpperCase();
//            final String end    = regKeyName.substring( separator + 1 );
//
//            //System.out.println( "regKeyName = " + regKeyName );
//            //System.out.println( "begin = " + begin );
//            //System.out.println( "end = " + end );
//
//            this.regName = end;
//
//            if( begin.equals( "HKEY_LOCAL_MACHINE" ) ||  begin.equals( "HKLM" ) ) {
//                registryTop = Registry.HKEY_LOCAL_MACHINE;
//                }
//            else if( begin.equals( "HKEY_CLASSES_ROOT" ) ||  begin.equals( "HKCR" ) ) {
//                registryTop = Registry.HKEY_CLASSES_ROOT;
//                }
//            else if( begin.equals( "HKEY_CURRENT_CONFIG" ) ||  begin.equals( "HKCC" ) ) {
//                registryTop = Registry.HKEY_CURRENT_CONFIG;
//                }
//            else if( begin.equals( "HKEY_CURRENT_USER" ) ||  begin.equals( "HKCU" ) ) {
//                registryTop = Registry.HKEY_CURRENT_USER;
//                }
//            else if( begin.equals( "HKEY_DYN_DATA" ) ||  begin.equals( "HKDD" ) ) {
//                registryTop = Registry.HKEY_DYN_DATA;
//                }
//            else if( begin.equals( "HKEY_PERFORMANCE_DATA" ) ||  begin.equals( "HKPD" ) ) {
//                registryTop = Registry.HKEY_PERFORMANCE_DATA;
//                }
//            else if( begin.equals( "HKEY_USERS" ) ) {
//                registryTop = Registry.HKEY_USERS;
//                }
//            else {
//                throw new RuntimeException( "Unkwon TopRegistry : \"" + begin + "\"" );
//                }
//        }
//
//        /** */
//        public RegistryKey getParentRegistryKey() // - - - - - - - - - - -
//        {
//            return this.registryTop;
//        }
//
//        /** */
//        public String getRegistryName() // - - - - - - - - - - - - - - - -
//        {
//            return this.regName;
//        }
//
//        /**
//        **
//        */
//        public RegistryKey getRegistryKeyReadOnly() // - - - - - - - - - -
//            throws com.ice.jni.registry.RegistryException
//        {
//            return this.registryTop.openSubKey( this.regName );
//        }
//
//        /**
//        **
//        */
//        public RegistryKey getRegistryKeyReadWrite() // - - - - - - - - - -
//            throws com.ice.jni.registry.RegistryException
//        {
//            return this.registryTop.openSubKey( this.regName, RegistryKey.ACCESS_WRITE );
//        }
//
//        /**
//        **
//        */
//        public String toString() // - - - - - - - - - - - - - - - - - - - -
//        {
//            return regKeyName;
//        }
//
//    }
//
//} // class
