package cx.ath.choisnet.servlet;

/**
 * TODOC
 *
 */
public interface SimpleServletContext
{
	/**
	 * TODOC
	 * 
	 * @param s
	 * @return TODOC
	 * @throws cx.ath.choisnet.servlet.ServletContextParamNotFoundException
	 */
    public abstract String getInitParameter(String s)
        throws cx.ath.choisnet.servlet.ServletContextParamNotFoundException;

    /**
	 * TODOC
     * 
     * @param s
     * @param s1
     * @return TODOC
     */
    public abstract String getInitParameter(String s, String s1);
}
