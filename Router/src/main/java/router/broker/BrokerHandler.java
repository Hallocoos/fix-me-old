package router.broker;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import java.util.ArrayList;

public class BrokerHandler extends Thread {
    private static final int BROKER_PORT = 5000;
    private static ArrayList<BrokerThread> brokers = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public BrokerHandler() throws IOException {
        ServerSocket brokerSocket = new ServerSocket(BROKER_PORT);

        while (true) {
            System.out.println("[ROUTER] Router waiting for broker connections.");
            Socket broker = brokerSocket.accept();
            System.out.println("[ROUTER] Connected to broker!");
            BrokerThread brokerThread = new BrokerThread(broker);
            brokers.add(brokerThread);

            pool.execute(brokerThread);
        }
    }

    public static void toBroker(String message) {
        String id = message.split("\\s+")[0];
        System.out.println(id);
        for (BrokerThread brokerThread : brokers) {
            if (brokerThread.getBrokerID().equalsIgnoreCase(id)) {
                System.out.println("ID found.");
                brokerThread.toBroker(message);
            } else {
                System.out.println("ID not found.");
            }
        }
    }
}
