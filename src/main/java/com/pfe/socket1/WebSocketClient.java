package com.pfe.socket1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfe.entities.Historique;
import jakarta.websocket.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
//@Component
@ClientEndpoint
public class WebSocketClient {
    private Session session;

    public WebSocketClient() throws URISyntaxException, IOException, DeploymentException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        URI uri = new URI("ws://localhost:8080/ControlAccess/websocket");
        container.connectToServer(this, uri);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);
    }

    public void sendMessage(Object message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(message);
        session.getBasicRemote().sendText(json);
        //session.getBasicRemote().sendText(message);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Connection to server closed: " + closeReason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
    }
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        WebSocketClient client = new WebSocketClient();
        //client.connect("ws://localhost:8080/websocket"); // replace with your endpoint URL
         while (true) {
        client.sendMessage("Hello, server!");
        latch.await(3, TimeUnit.SECONDS);
        //   client.session.close();

        }
    }
}
