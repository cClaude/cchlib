package cx.ath.choisnet.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Trying to offer a ping like operation for Java (Alpha)
 */
@Deprecated
public class PseudoPing
{

    private String ipAddress;

    public PseudoPing(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String sendRequest(byte[] bytes, int port)
        throws java.net.UnknownHostException, java.io.IOException
    {
        Socket socket;
        StringBuilder result;

        socket = new Socket(ipAddress, port);

        result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

        bos.write(bytes, 0, bytes.length);

        String line;
        while((line = br.readLine()) != null) {
            result.append(line);
        }

        socket.close();
    //   42   86:aload_3
    //   43   87:invokevirtual   #18  <Method void java.net.Socket.close()>
//        break MISSING_BLOCK_LABEL_102;
//    //   44   90:goto            102
//        Exception exception;
//        exception;
    //   45   93:astore          8
        socket.close();
    //   46   95:aload_3
    //   47   96:invokevirtual   #18  <Method void java.net.Socket.close()>
//        throw exception;
    //   48   99:aload           8
    //   49  101:athrow
        return result.toString();
    }

    public String sendRequestNoException(byte[] bytes, int port)
    {
        try {
            return sendRequest(bytes, port);
        }
        catch(Exception e) {
            e.printStackTrace(System.err);

            return (new StringBuilder()).append("* ").append(e.getMessage()).toString();
        }
    }

    public boolean helloPing()
    {
        try {
            String str = sendRequest("Hello\n".getBytes(), 7);

            if(str.equals("Hello")) {
            System.out.println("Alive!");

            return true;
        }

            System.out.println("Dead or echo port not responding");

            return false;
        }
        catch(java.io.IOException e)
        {
            System.out.println((new StringBuilder()).append("Dead or echo port not responding ").append(e).toString());
        }
        return false;
    }
}
