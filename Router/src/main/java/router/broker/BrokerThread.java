package router.broker;

import java.io.*;
import java.net.*;
import java.util.Random;

import router.market.MarketHandler;
import router.market.MarketThread;

public class BrokerThread implements Runnable {
    private BufferedReader brokerIn;
    private PrintWriter brokerOut;
    private String brokerID;

    public BrokerThread(Socket brokerSocket) throws IOException {
        brokerIn = new BufferedReader(new InputStreamReader(brokerSocket.getInputStream()));
        brokerOut = new PrintWriter(brokerSocket.getOutputStream(), true);
        Random r = new Random();
        int i = r.nextInt((99999 - 10000) + 1) + 99999;
        brokerID = Integer.toString(i);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = brokerIn.readLine();
                if (request != null && !request.equals("quit")) {
                    // System.out.println(brokerID + " " + request);
                    MarketHandler.marketThread.toMarket(brokerID + " " + request);
                }
                if (request.equals("quit")) {
                    System.out.println("Connection to broker has been closed.");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connection to broker has been lost.");
        } finally {
            try {
                brokerOut.close();
                brokerIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBrokerID() {
        return brokerID;
    }

    public void toBroker(String message) {
        brokerOut.println(message);
    }
}
