package jdev.dto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSocketServer {

    private static final Logger log = LoggerFactory.getLogger(TestSocketServer.class);

    public static void main(String[] args) throws IOException {
        log.info("Запуск тестового сервера");
        ServerSocket ss = new ServerSocket(12345);
        while (true) {
            new ServerConnection(ss.accept()).start();
        }
    }
}

class ServerConnection extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ServerConnection.class);
    Socket client;
    ServerConnection ( Socket client) throws SocketException {
        this.client = client;
        setPriority(NORM_PRIORITY - 1);
    }

    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            log.info("Получены новые данные:");
            log.info(in.readObject().toString());
            log.info("");
            client.close();
        } catch (IOException e) {
            log.info("IOException: " + e);
        } catch (ClassNotFoundException e) {
            log.info("ClassNotFoundException: " + e);
        }
    }
}
