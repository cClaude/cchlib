package cx.ath.choisnet.system.impl.win32;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Iterator;
import org.apache.log4j.Logger;
import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.RegDWordValue;
import com.ice.jni.registry.RegStringValue;
import com.ice.jni.registry.Registry;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;
import com.ice.jni.registry.RegistryValue;
import cx.ath.choisnet.util.enumeration.EnumerationHelper;

/**
 * @author Claude CHOISNET
 */
public class RegWin32
{
    final private static Logger slogger = Logger.getLogger(RegWin32.class);
    public final static String SYSTEM_ENVIRONMENT_BASE = "HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Control\\Session Manager\\Environment";
    private static boolean checkOnceDone = false;

    public RegWin32() throws EnvArcRegWin32EnvArcException
    {
        checkLib();
    }

    final 
    private void checkLib() throws EnvArcRegWin32EnvArcException
    {
        if( checkOnceDone ) {
            return;
        }

        try {
            checkLibOnce();
        }
        catch( Exception e ) {
            slogger.fatal( e );

            throw new EnvArcRegWin32EnvArcException( "init error", e );
        }

        checkOnceDone = true;
    }

    final 
    private void checkLibOnce()
        throws
            ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            ClassCastException,
            NoSuchKeyException,
            RegistryException,
            SecurityException,
            NoSuchFieldException,
            NoSuchMethodException,
            IllegalArgumentException,
            InvocationTargetException
    {
        Class<?> classRegistry = Class.forName( "com.ice.jni.registry.Registry" );

        // Check if it's work !
        Object      instance = classRegistry.newInstance();
        Registry    instanceRegistry = (Registry)instance;
        Field       fieldHKLM = classRegistry.getDeclaredField( "HKEY_LOCAL_MACHINE" );
        Object      fiedlHKLMValue = fieldHKLM.get( instanceRegistry );
        RegistryKey hklm = (RegistryKey)fiedlHKLMValue;
        Method      openSubKeyMethod = hklm.getClass().getMethod( "openSubKey", String.class );
        Object      hklmSystem = openSubKeyMethod.invoke( hklm, "SYSTEM" );
        RegistryKey hklmSystemRegistryKey = (RegistryKey)hklmSystem;

        // Remove warning
        hklmSystemRegistryKey.getClass();
    }

    final
    protected RegistryValue getRegistryValue(RegWin32Key regKey, String regValue)
        throws EnvArcRegWin32EnvArcException
    {
        try {
            RegistryKey registryKey = regKey.getRegistryKeyReadOnly();

            return registryKey.getValue(regValue);
        }
        catch(NoSuchKeyException e) {
            throw new EnvArcRegWin32EnvArcException(e);
        }
        catch(RegistryException e) {
            // REG_EXPAND_SZ :  Registry API Error 87, 'invalid parameter' - 'type is not REG_SZ'
            throw new EnvArcRegWin32EnvArcException(e);
            }
    }

    public String getRegString(String regName, String regValue)
        throws EnvArcRegWin32EnvArcException
    {
        try {
           RegistryValue aValue = getRegistryValue(new RegWin32Key(regName), regValue);

            if(aValue != null) {
                if( aValue instanceof RegStringValue ) {
                    return RegStringValue.class.cast( aValue ).getData();
                }
                else {
                    return null;
                }
            }
        }
        catch(ClassCastException e) {
            throw new EnvArcRegWin32EnvArcException(e);
        }

        return null;
    }

    final
    public void setRegString(String regName, String regValue, String regData)
        throws EnvArcRegWin32EnvArcException
    {
        RegWin32Key regKey = new RegWin32Key(regName);

        try{
            RegistryKey myKey = regKey.getRegistryKeyReadWrite();

            myKey.setValue(new RegStringValue(myKey, regValue, regData));
        }
        catch(com.ice.jni.registry.RegistryException e) {
            String msg = new StringBuilder()
                    .append("setRegString( \"")
                    .append(regName)
                    .append("\", \"")
                    .append(regValue)
                    .append("\", \"")
                    .append(regData)
                    .append("\" ) : ")
                    .toString();
            throw new EnvArcRegWin32EnvArcException(msg, e);
        }
    }

    final
    public Integer getRegInteger(String regName, String regValue)
        throws EnvArcRegWin32EnvArcException
    {
        try {
            RegistryValue aValue = getRegistryValue( new RegWin32Key(regName) , regValue);

            if(aValue != null) {
                return new Integer(((RegDWordValue)aValue).getData());
            }
        }
        catch(ClassCastException e) {
            throw new EnvArcRegWin32EnvArcException(e);
            }

        return null;
    }

    final
    public void setRegInteger(String regName, String regValue, int regData)
        throws EnvArcRegWin32EnvArcException
    {
        RegWin32Key regKey = new RegWin32Key(regName);

        try {
            RegistryKey myKey = regKey.getRegistryKeyReadWrite();

            myKey.setValue(new RegDWordValue(myKey, regValue, 4, regData));
        }
        catch(com.ice.jni.registry.RegistryException e) {
            String msg = "setRegString( \"" + regName + "\", \"" + regValue + "\", \"" + regData + "\" ) : ";

            throw new EnvArcRegWin32EnvArcException(msg, e);
        }
    }

    /**
     * Get an Iterator for the giving key, this Iterator
     * return sub-key (directories) names.
     * 
     * @param regKeyName key to inspect 
     * @return An Iterator
     * @throws EnvArcRegWin32EnvArcException
     */
    final
    protected Iterator<String> getSubKeyNamesIterator(String regKeyName)
        throws EnvArcRegWin32EnvArcException
    {
        RegWin32Key regKey = new RegWin32Key(regKeyName);
        
        try {
            RegistryKey registryKey = regKey.getRegistryKeyReadOnly();

            @SuppressWarnings("unchecked")
            Enumeration<String> enu = registryKey.keyElements();
        
            return EnumerationHelper.toIterator( enu );
        }
        catch( RegistryException e ) {
            throw new EnvArcRegWin32EnvArcException(e);
        }
    }
    
    /**
     * Get an Iterator for the giving key, this Iterator
     * return values (files) names.
     * 
     * @param regKeyName key to inspect 
     * @return An Iterator
     * @throws EnvArcRegWin32EnvArcException
     */
    final
    protected Iterator<String> getValuesIterator(String regKeyName)
        throws EnvArcRegWin32EnvArcException
    {
        RegWin32Key regKey = new RegWin32Key(regKeyName);

        try {
            RegistryKey registryKey = regKey.getRegistryKeyReadOnly();

            @SuppressWarnings("unchecked")
            Enumeration<String> enu = registryKey.valueElements();

            return EnumerationHelper.toIterator( enu );
             }
        catch( RegistryException e ) {
            throw new EnvArcRegWin32EnvArcException(e);
        }
   }
}
