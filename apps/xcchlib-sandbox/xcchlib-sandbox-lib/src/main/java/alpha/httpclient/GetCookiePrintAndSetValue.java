package alpha.httpclient;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Test App
 */
public class GetCookiePrintAndSetValue
{
    private GetCookiePrintAndSetValue()
    {
        //App
    }

    @SuppressWarnings("squid:S106")
    public static void main( final String[] args )
    {
        final HttpClient client = new HttpClient();

        client.getParams().setParameter("j_username", "abc");
        client.getParams().setParameter("j_password", "pqr");

        final GetMethod method = new GetMethod("http://localhost:8080/");

        try{
            client.executeMethod( method );

            final Cookie[] cookies = client.getState().getCookies();

            for( int i = 0; i < cookies.length; i++ ) {
                final Cookie cookie = cookies[i];

                System.err.println(
                        "Cookie: " + cookie.getName() +
                        ", Value: " + cookie.getValue() +
                        ", IsPersistent?: " + cookie.isPersistent() +
                        ", Expiry Date: " + cookie.getExpiryDate() +
                        ", Comment: " + cookie.getComment()
                        );
                }

            client.executeMethod( method );
            }
        catch(final Exception e) {
            System.err.println(e);
            }
        finally {
          method.releaseConnection();
            }
    }
}
