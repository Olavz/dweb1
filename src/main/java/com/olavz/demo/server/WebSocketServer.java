package com.olavz.demo.server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

@ServerEndpoint("/endpoint")
public class WebSocketServer {

    private static ArrayList<Session> peers = new ArrayList<>();

    private static WebSocketServer webSocketServer;

    public static WebSocketServer getGetInstance() {
        if(webSocketServer == null) {
            webSocketServer = new WebSocketServer();
        }
        return webSocketServer;
    }

    public void testAction(String message) {
        for(Session peer : peers) {
            try {
                peer.getBasicRemote().sendText(message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen::" + session.getId());
        session.setMaxIdleTimeout(86400000); // 24h
        peers.add(session);
    }
    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" +  session.getId());
        peers.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);

        try {
            for(Session s : peers) {
                s.getBasicRemote().sendText("Message from " + session.getId() + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }
}