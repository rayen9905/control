package com.pfe.socket1;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pfe.entities.Event;
import com.pfe.entities.Historique;
import com.pfe.entities.Type_Evt;
import jakarta.websocket.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
//@Component
@ClientEndpoint
public class WebSocketClient {
    private Session session;

    public WebSocketClient() throws URISyntaxException, IOException, DeploymentException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        URI uri = new URI("ws://localhost:8080/websocket");
        container.connectToServer(this, uri);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message: " + message);
    }

    public void sendMessage(String message) throws IOException, EncodeException {
        // Send the JsonNode over the WebSocket
        session.getBasicRemote().sendText(message);

        //session.getBasicRemote().sendObject(jsonObject);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server websocket");
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
        client.sendMessage("hi server");
        latch.await(3, TimeUnit.SECONDS);
        //   client.session.close();

        }
    }
}
