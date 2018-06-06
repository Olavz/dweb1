package com.olavz.demo.fxclient;

import javafx.scene.control.TextArea;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class SocketRunner implements Runnable {

    private final WebSocketClient client = new WebSocketClient();
    private final SimpleSocketManager socket;
    private URI echoUri = null;

    public SocketRunner(String text, TextArea textArea) {
        try {
            echoUri = new URI(text.trim());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket = new SimpleSocketManager(textArea);
    }

    public void sendMessage(String message) {
        socket.sendMessage(message);
    }

    @Override
    public void run() {
        try {
            client.start();
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);

            // wait for closed socket connection.
            socket.awaitClose(5, TimeUnit.HOURS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception te) {
                te.printStackTrace();
            }
        }
    }
}
