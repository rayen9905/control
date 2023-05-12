package com.pfe.socket1;

import org.apache.commons.net.util.SubnetUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NetworkMonitor {
    private Map<String, Boolean> deviceStatusMap = new HashMap<>();

    public void startMonitoring(String networkAddress, int interval) {
        SubnetUtils subnetUtils = new SubnetUtils(networkAddress);
        String[] addresses = subnetUtils.getInfo().getAllAddresses();

        for (String address : addresses) {
            deviceStatusMap.put(address, false); // initially mark all devices as disconnected
        }

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            List<String> connectedDevices = new ArrayList<>();
            for (String address : addresses) {
                try {
                    InetAddress inetAddress = InetAddress.getByName(address);
                    boolean isAlive = inetAddress.isReachable(5000); // ping the address with a timeout of 1 second

                    if (isAlive) {
                        connectedDevices.add(address);
                    }
                } catch (UnknownHostException e) {
                    // ignore errors
                } catch (Exception e) {
                    // ignore errors
                }
            }
            updateDeviceStatus(connectedDevices);
        }, 0, interval, TimeUnit.SECONDS);
    }

    private void updateDeviceStatus(List<String> connectedDevices) {
        for (String address : deviceStatusMap.keySet()) {
            boolean wasConnected = deviceStatusMap.get(address);
            boolean isConnected = connectedDevices.contains(address);

            if (isConnected && !wasConnected) {
                // device has connected
                deviceStatusMap.put(address, true);
                System.out.println("Device " + address + " has connected.");
            } else if (!isConnected && wasConnected) {
                // device has disconnected
                deviceStatusMap.put(address, false);
                System.out.println("Device " + address + " has disconnected.");
            }
        }
    }

    public static void main(String[] args) {
        NetworkMonitor networkMonitor = new NetworkMonitor();
        networkMonitor.startMonitoring("192.168.100.1/24", 1);
    }
}
