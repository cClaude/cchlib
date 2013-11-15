package cx.ath.choisnet.servlet;

public interface ParameterValue
{
    String[] toArray();
    String toString(String s);
    boolean booleanValue();
    boolean booleanValue(boolean flag);
    int intValue();
    int intValue(int i);
    long longValue();
    long longValue(long l);
    float floatValue();
    float floatValue(float f);
    double doubleValue();
    double doubleValue(double d);
}
