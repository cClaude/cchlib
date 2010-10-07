package alpha.cx.ath.choisnet.net.dhcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class DHCPOptionEntry
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    private transient byte[] value;

    public DHCPOptionEntry(byte[] b, int off, int len)
    {
        value = new byte[len];

        System.arraycopy(b, off, value, 0, len);
    }

    public DHCPOptionEntry(byte value[])
    {
        this(value, 0, value.length);
    }

    public DHCPOptionEntry(byte value)
    {
        this.value = new byte[1];
        this.value[0] = value;
    }

    public byte[] getOptionValue()
    {
        return value;
    }

    public String toString()
    {
        return (new StringBuilder())
                    .append('(')
                    .append(value.length)
                    .append('/')
                    .append(DHCPParameters.toHexString(value))
                    .append(')')
                    .toString();
    }

    public DHCPOptionEntry getClone()
    {
        return new DHCPOptionEntry(getOptionValue());
    }

    private void writeObject(ObjectOutputStream stream)
        throws java.io.IOException
    {
        stream.defaultWriteObject();
        stream.writeInt(value.length);
        stream.write(value);
    }

    private void readObject(ObjectInputStream stream)
        throws java.io.IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        value = new byte[stream.readInt()];
        stream.readFully(value);
    }
}
