package com.pfe.socket1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfe.entities.Historique;
import jakarta.websocket.*;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class WebSocketClient {

    private final CountDownLatch latch;
    private Session session;

    public WebSocketClient(CountDownLatch latch) {
        this.latch = latch;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        latch.countDown();
    }
    public void sendMessage(String message) throws Exception {
        session.getBasicRemote().sendText(message);
    }

    public void sendMessage1(Object his) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(his);
        session.getBasicRemote().sendText(json);
        //session.getBasicRemote().sendText(message);
    }

    public void connect(String uri) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, new URI(uri));
    }

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        WebSocketClient client = new WebSocketClient(latch);
        client.connect("ws://localhost:8080/websocket"); // replace with your endpoint URL
     //   while(true) {
            client.sendMessage("Hello, server!");
            latch.await(5, TimeUnit.SECONDS);
         // client.session.close();
       // }

    }
}
