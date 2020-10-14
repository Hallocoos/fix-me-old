package broker;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Broker {
    private static final String ROUTER_IP = "127.0.0.1";
    private static final int ROUTER_PORT = 5000;
    // private static int id; TODO

    public static void main(String[] args) throws IOException {
        Socket routerSocket = null;
        while (routerSocket == null) {
            try {
                routerSocket = new Socket(ROUTER_IP, ROUTER_PORT);
            } catch (ConnectException e) {
                System.out.println("Waiting to establish connection to router.");
            }
        }
        System.out.println("Connected to router!");
        PrintWriter routerOutput = new PrintWriter(routerSocket.getOutputStream(), true);
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("> ");
            String input = (stdin.readLine()).trim().replaceAll("\\s+", " ");
            if (input.equals("quit")) {
                routerOutput.println(input);
                break;
            } else if (validateInput(input))
                routerOutput.println(input);
        }
        routerSocket.close();
        System.exit(0);
    }

    private static boolean validateInput(String input) {
        if (input == null || input.equalsIgnoreCase(""))
            return false;
        String[] validArray = input.split("\\s+");
        if (validArray.length != 3)
            return false;
        if (!validArray[0].equalsIgnoreCase("buy") && !validArray[0].equalsIgnoreCase("sell"))
            return false;
        boolean isNumeric = validArray[2].chars().allMatch(x -> Character.isDigit(x));
        if (!isNumeric)
            return false;
        return true;
    }
}
