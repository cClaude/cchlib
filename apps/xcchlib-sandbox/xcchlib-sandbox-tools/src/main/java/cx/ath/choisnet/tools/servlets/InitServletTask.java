package cx.ath.choisnet.tools.servlets;

/**
 *
 * @since 1.00
 */
public abstract class InitServletTask implements InitServletTaskInterface
{
    private String name = null;

    public InitServletTask()
    {
        // empty
    }

    public InitServletTask( final String name )
    {
        this.name = name;
    }

    @Override
    public String getTaskName()
    {
        if( this.name == null ) {
            return this.getClass().getName();
        } else {
            return this.getClass().getName() + "(" + this.name + ")";
        }
    }

    @Override
    public boolean continueRunning()
    {
        return true;
    }
}
