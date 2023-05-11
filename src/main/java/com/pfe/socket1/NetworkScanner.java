package com.pfe.socket1;

import java.util.ArrayList;
import java.util.List;

public class NetworkScanner {
    private static List<String> activeIpAddresses = new ArrayList<>();

    public static void main(String[] args) {

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Pcap.openLive(PcapIf.DATALINK_EN10MB, 65536, Pcap.MODE_PROMISCUOUS, 1000, errbuf);

        if (pcap == null) {
            System.err.println("Error while opening device for capture: " + errbuf.toString());
            return;
        }

        PcapPacketHandler<String> packetHandler = new PcapPacketHandler<String>() {
            @Override
            public void nextPacket(PcapPacket packet, String user) {
                String sourceIp = packet.getHeader().getSrcAddr().toString();
                String destinationIp = packet.getHeader().getDstAddr().toString();

                if (!activeIpAddresses.contains(sourceIp)) {
                    activeIpAddresses.add(sourceIp);
                    System.out.println(sourceIp + " connected");
                }

                if (!activeIpAddresses.contains(destinationIp)) {
                    activeIpAddresses.add(destinationIp);
                    System.out.println(destinationIp + " connected");
                }
            }
        };

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000); // Wait 5 seconds before scanning the network again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<String> disconnectedIpAddresses = new ArrayList<>();

                for (String ipAddress : activeIpAddresses) {
                    boolean isReachable = isIpAddressReachable(ipAddress);
                    if (!isReachable) {
                        System.out.println(ipAddress + " disconnected");
                        disconnectedIpAddresses.add(ipAddress);
                    }
                }

                activeIpAddresses.removeAll(disconnectedIpAddresses);
            }
        }).start();

        pcap.loop(Pcap.LOOP_INFINITE, packetHandler, "");
        pcap.close();
    }

    private static boolean isIpAddressReachable(String ipAddress) {
        // Send an ICMP echo request to the IP address and check if it responds
        // You can use Java's InetAddress class to do this.
        // Here's an example code:
        /*
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            return address.isReachable(1000);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        */
        return true; // For the sake of demonstration, always return true.
    }
}
