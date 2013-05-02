package cx.ath.choisnet.servlet;

public interface ParameterValue
{
    public abstract String[] toArray();
    @Override
    public abstract String toString();
    public abstract String toString(String s);
    public abstract boolean booleanValue();
    public abstract boolean booleanValue(boolean flag);
    public abstract int intValue();
    public abstract int intValue(int i);
    public abstract long longValue();
    public abstract long longValue(long l);
    public abstract float floatValue();
    public abstract float floatValue(float f);
    public abstract double doubleValue();
    public abstract double doubleValue(double d);
}
