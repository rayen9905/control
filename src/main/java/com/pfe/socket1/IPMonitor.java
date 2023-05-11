package com.pfe.socket1;

import org.springframework.stereotype.Component;

import java.net.*;
import java.util.*;

@Component
public class IPMonitor {
    private Set<String> connectedIPAddresses = new HashSet<>();

    public void startMonitoring() throws SocketException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Set<String> newIPAddresses = new HashSet<>();
                try {
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface networkInterface = interfaces.nextElement();
                        if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                            while (addresses.hasMoreElements()) {
                                InetAddress address = addresses.nextElement();
                                if (address instanceof Inet4Address) {
                                    newIPAddresses.add(address.getHostAddress());
                                }
                            }
                        }
                    }
                    connectedIPAddresses.retainAll(newIPAddresses);
                    for (String ipAddress : newIPAddresses) {
                        if (!connectedIPAddresses.contains(ipAddress)) {
                            if (isReachable(ipAddress, 80, 1000)) {
                                connectedIPAddresses.add(ipAddress);
                                System.out.println(ipAddress + " connected");
                            }
                        }
                    }
                    for (String ipAddress : connectedIPAddresses) {
                        if (!newIPAddresses.contains(ipAddress)) {
                            if (!isReachable(ipAddress, 80, 1000)) {
                                connectedIPAddresses.remove(ipAddress);
                                System.out.println(ipAddress + " disconnected");
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    public void stopMonitoring() {
        // TODO: Implement this method to stop monitoring the IP addresses
    }

    public Set<String> getConnectedIPAddresses() {
        return connectedIPAddresses;
    }

    private boolean isReachable(String ipAddress, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ipAddress, port), timeout);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
