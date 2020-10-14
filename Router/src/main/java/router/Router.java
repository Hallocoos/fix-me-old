package router;

import java.io.*;

import router.broker.BrokerHandler;
import router.market.MarketHandler;

public class Router {
    public static void main(String[] args) throws IOException {
        new MarketHandler();
        new BrokerHandler();
    }
}
