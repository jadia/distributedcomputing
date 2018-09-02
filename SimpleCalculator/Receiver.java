// Arguments: <port>

import java.net.*;
import java.io.*;

public class Receiver {

    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println
                    ("This program requires a command line argument. -> <port>");
        else {
            int port = Integer.parseInt(args[0]);
            final int MAX_LEN = 1000;
            // This is the assumed maximum byte length of the
            //      datagram to be received.
            try {
                DatagramSocket mySocket = new DatagramSocket(port);
                //System.out.println("Socket created. \n");
                // instantiates a datagram socket for receiving the data
                byte[] buffer = new byte[MAX_LEN];
                DatagramPacket datagram =
                        new DatagramPacket(buffer, MAX_LEN);
                //System.out.println("Packet created. \n");

                System.out.println("\nThe receiver is up and waiting for a message .....\n");

                mySocket.receive(datagram);
                ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = new ObjectInputStream(bis);
                shareData store = new shareData();
                store = (shareData) ois.readObject();
                System.out.println("\nGot a message from a sender: " + store.number1 + " " + store.number2 + " " + store.operator + "\n");

                int calResult = 0;
                boolean invalidOperator = false;

                switch (store.operator) {
                    case '+':
                        calResult = store.number1 + store.number2;
                        break;

                    case '-':
                        calResult = store.number1 - store.number2;
                        break;

                    case '*':
                        calResult = store.number1 * store.number2;
                        break;

                    case '/':
                        if (store.number2 == 0)
                            invalidOperator = true;
                        else
                            calResult = store.number1 / store.number2;
                        break;

                    default:
                        invalidOperator = true;
                        break;
                }
                String returnToSender;
                if (invalidOperator)
                    returnToSender = "Invalid Input!";
                else {
                    returnToSender = "The answer is " + calResult;
                    //System.out.println(returnToSender + "\n");
                }
                InetAddress senderAddr = datagram.getAddress();
                buffer = returnToSender.getBytes();
                DatagramPacket sendResult = new DatagramPacket(buffer, buffer.length, senderAddr, 5555);
                mySocket.send(sendResult);
                mySocket.close();
            } // end try
            catch (Exception ex) {
                ex.printStackTrace();
            }
        } // end else
    } // end main
} // end class