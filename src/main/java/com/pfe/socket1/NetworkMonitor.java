package com.pfe.socket1;

import org.apache.commons.net.util.SubnetUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NetworkMonitor {

    public static void main(String[] args) {
        try {
            InetAddress localIpAddress = InetAddress.getByName("192.168.100.0");
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localIpAddress);

            System.out.println("Monitoring devices connected to the switch...");

            Set<String> connectedDevices = new HashSet<>();

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
                            String ipAddress = address.getHostAddress();

                            if (!connectedDevices.contains(ipAddress)) {
                                connectedDevices.add(ipAddress);
                                System.out.println("Device connected: " + ipAddress);
                            }
                        }
                    }

                    connectedDevices.removeIf(ipAddress -> {
                        try {
                            return !InetAddress.getByName(ipAddress).isReachable(1000);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return true;
                        }
                    });

                }
            }, 0, 5000);

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
        }
