package market;

import java.io.*;
import java.net.*;
import java.util.*;

public class Market {
    private static final String ROUTER_IP = "127.0.0.1";
    private static final int ROUTER_PORT = 5001;
    // private static int id; TODO

    public static void main(String[] args) throws IOException {
        Socket routerSocket = new Socket(ROUTER_IP, ROUTER_PORT);
        PrintWriter routerOutput = new PrintWriter(routerSocket.getOutputStream(), true);
        BufferedReader routerInput = new BufferedReader(new InputStreamReader(routerSocket.getInputStream()));

        routerOutput.print("Market connected to the router.");
        while (true) {
            try {
                while (true) {
                    String input = routerInput.readLine();
                    if (input.equals("quit"))
                        break;
                    System.out.println("B: " + input);
                    routerOutput.println(input);
                }
            } catch (IOException e) {
                System.out.println("Connection to the router has been lost.");
            } finally {
                try {
                    routerOutput.close();
                    routerInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }
    }
}
