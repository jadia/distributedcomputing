// Arguments: <IP/Domain> <port> <num1> <num2> <operator>
// Default
import java.net.*;
import java.io.*;


public class Sender {

    public static void main(String[] args) {
        if (args.length != 5)
            System.out.println
                    ("This program requires three command line arguments -> <IP/Domain> <port> <num1> <num2> <operator> ");
        else {
            try {
                InetAddress receiverHost = InetAddress.getByName(args[0]);
                int receiverPort = Integer.parseInt(args[1]);
                shareData transfer = new shareData(Integer.parseInt(args[2]), Integer.parseInt(args[3]), args[4].charAt(0));

                DatagramSocket mySocket = new DatagramSocket(5555);
                ByteArrayOutputStream bais = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bais);
                oos.writeObject(transfer);
                oos.flush();
                byte[ ] buffer = bais.toByteArray();
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, receiverHost, receiverPort);
                System.out.println("\nSending a message to the Receiver ...\n");
                mySocket.send(datagram);
                final int MAX_LEN = 1000;
                byte[] result = new byte[MAX_LEN];
                DatagramPacket receiveResult = new DatagramPacket(result, MAX_LEN);
                mySocket.receive(receiveResult);
                String message = new String(result);
                System.out.println(message);
                mySocket.close( );
            } // end try
            catch (Exception ex) {
                ex.printStackTrace( );
            }
        } // end else
    } // end main
} // end class