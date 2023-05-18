package com.pfe.socket1;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.network.Ip4;
import org.pcap4j.core.*;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class SNMPDeviceMonitor {
    private static boolean isReachable(String ipAddress) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            return address.isReachable(1000); // Timeout of 1 second
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String baseIp = "192.168.100";
        boolean previousStatus = false;

        for (int i = 1; i <= 255; i++) {
            String ipAddress = baseIp + "." + i;
            boolean currentStatus = isReachable(ipAddress);

            if (previousStatus != currentStatus) {
                if (currentStatus) {
                    System.out.println("Address " + ipAddress + " changed status to reachable.");
                } else {
                    System.out.println("Address " + ipAddress + " changed status to unreachable.");
                }
            }

            previousStatus = currentStatus;
        }
    }
}
    /*    private static Set<String> connectedDevices = new HashSet<>();

    public static void main(String[] args) {
        scanNetwork();
    }

    private static void scanNetwork() {
        try {
            while (true) {
                Set<String> currentDevices = new HashSet<>();

                // Clear ARP cache
                Process clearCacheProcess = Runtime.getRuntime().exec("arp -d");
                clearCacheProcess.waitFor();

                Process process = Runtime.getRuntime().exec("arp -a");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Parse the output to extract the IP and MAC addresses
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        String ipAddress = parts[1].replaceAll("[()]", "");
                        String macAddress = parts[3];

                        if (ipAddress.startsWith("192.168.100")) {
                            currentDevices.add(ipAddress);

                            if (!connectedDevices.contains(ipAddress)) {
                                System.out.println("Device connected - IP: " + ipAddress + ", MAC: " + macAddress);
                                // Perform any desired operations with the newly connected device
                            }
                        }
                    }
                }
                reader.close();

                // Check for disconnected devices
                for (String ipAddress : connectedDevices) {
                    if (!currentDevices.contains(ipAddress) && !ping(ipAddress)) {
                        System.out.println("Device disconnected - IP: " + ipAddress);
                        // Perform any desired operations with the disconnected device
                    }
                }

                connectedDevices = currentDevices;

                // Sleep for a certain interval before scanning again
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean ping(String ipAddress) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            return address.isReachable(1000);
        } catch (Exception e) {
            return false;
        }
    }*/


