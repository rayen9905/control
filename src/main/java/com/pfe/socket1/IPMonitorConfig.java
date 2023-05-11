package com.pfe.socket1;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class IPMonitorConfig {
    private final IPMonitor ipMonitor;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final Set<String> connectedIPAddresses = new HashSet<>();

    private final Set<String> previouslyConnectedIPAddresses = new HashSet<>();

    public IPMonitorConfig(IPMonitor ipMonitor) {
        this.ipMonitor = ipMonitor;
    }

    @PostConstruct
    public void init() {
        executorService.scheduleAtFixedRate(() -> {
            Set<String> currentIPAddresses = new HashSet<>(ipMonitor.getConnectedIPAddresses());
            Set<String> disconnectedIPAddresses = new HashSet<>(connectedIPAddresses);
            disconnectedIPAddresses.removeAll(currentIPAddresses);
            Set<String> newIPAddresses = new HashSet<>(currentIPAddresses);
            newIPAddresses.removeAll(connectedIPAddresses);
            if (!disconnectedIPAddresses.isEmpty() || !newIPAddresses.isEmpty()) {
                connectedIPAddresses.clear();
                connectedIPAddresses.addAll(currentIPAddresses);
                if (!newIPAddresses.isEmpty()) {
                    Iterator<String> iterator = newIPAddresses.iterator();
                    if (iterator.hasNext()) {
                        String firstElement = iterator.next();
                    System.out.println("New IP addresses connected: " + firstElement);
                    }
                }
                if (!disconnectedIPAddresses.isEmpty()) {
                    System.out.println("IP addresses disconnected: " + disconnectedIPAddresses);
                }
            }
            previouslyConnectedIPAddresses.clear();
            previouslyConnectedIPAddresses.addAll(currentIPAddresses);
        }, 0, 5, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdownNow();
    }
}
