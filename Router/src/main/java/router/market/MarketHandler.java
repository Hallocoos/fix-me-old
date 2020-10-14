package router.market;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MarketHandler extends Thread {
    private static final int MARKET_PORT = 5001;
    private static ExecutorService pool = Executors.newFixedThreadPool(1);
    public static MarketThread marketThread;

    public MarketHandler() throws IOException {
        ServerSocket marketSocket = new ServerSocket(MARKET_PORT);

        System.out.println("[ROUTER] Router waiting for market connections.");
        Socket market = marketSocket.accept();
        System.out.println("[ROUTER] Connected to market!");
        marketThread = new MarketThread(market);

        pool.execute(marketThread);
    }
}