package tunafish2k.tunaextension.callback;

import com.sun.net.httpserver.HttpServer;
import org.lwjgl.Sys;
import tunafish2k.tunaextension.persistent.PersistentDatas;
import tunafish2k.tunaextension.puppet.CallbackMessageHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallbackServer {
    public static CallbackServer INSTANCE = new CallbackServer();

    ExecutorService pool;
    HttpServer server;
    public void start() {
        try {
            if (server != null) server.stop(0);
            if (pool != null && !pool.isShutdown()) pool.shutdown();

            System.out.println("starting server...");

            server = HttpServer.create(new InetSocketAddress(PersistentDatas.callback.port), 0);
            server.createContext("/message", new CallbackMessageHandler());
            server.setExecutor(pool = Executors.newFixedThreadPool(5));
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
