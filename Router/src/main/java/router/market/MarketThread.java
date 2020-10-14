package router.market;

import java.io.*;
import java.net.*;

import router.broker.BrokerHandler;

public class MarketThread implements Runnable {
    private BufferedReader marketIn;
    private PrintWriter marketOut;
    // private int marketID; TODO

    public MarketThread(Socket marketSocket) throws IOException {
        marketIn = new BufferedReader(new InputStreamReader(marketSocket.getInputStream()));
        marketOut = new PrintWriter(marketSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = marketIn.readLine();
                if (request != null && !request.equals("quit"))
                    BrokerHandler.toBroker(request);
                if (request.equals("quit")) {
                    System.out.println("Connection to market has been closed.");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connection to market has been lost.");
        } finally {
            try {
                marketOut.close();
                marketIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void toMarket(String message) throws IOException {
        marketOut.println(message);
    }
}
