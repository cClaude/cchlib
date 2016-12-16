package oldies.tools.smstools.myphoneexplorer;

public class InconsistantSMSException extends Exception
{
    private static final long serialVersionUID = 1L;

    private final String  fieldName;
    private final SMS     previousSMS;
    private final SMS     currentSMS;

    public InconsistantSMSException(
        final String fieldName,
        final SMS previousSMS,
        final SMS currentSMS
        )
    {
        this.fieldName   = fieldName;
        this.previousSMS = previousSMS;
        this.currentSMS  = currentSMS;
    }

    @Override
    public String getMessage()
    {
        return "Error inconsistant fields ["
                + this.fieldName
                + "]=["
                + this.previousSMS
                + '/'
                + this.currentSMS
                + ']';
    }
}
