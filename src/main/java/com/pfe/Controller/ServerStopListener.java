package com.pfe.Controller;

import com.pfe.entities.Porte;
import com.pfe.entities.WaveShare;
import com.pfe.repos.WaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServerStopListener implements ApplicationListener<ContextClosedEvent> {
@Autowired
WaveService ws;
    @Autowired
    WaveRepository wss;
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        List<WaveShare> wh=ws.getAllprfs();
        for (WaveShare w:wh
             ) {
w.setStatus("Disconnected");
wss.save(w);
        }
        // Perform your update logic here
        System.out.println("Server is stopping. Performing updates...");
        // Your update code goes here
    }
}