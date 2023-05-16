package com.pfe.control;

import jakarta.annotation.PostConstruct;
import org.jnetpcap.JCaptureHeader;
import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.pcap4j.packet.Packet;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pfe.repos","com.pfe.auth","com.pfe.config","com.pfe.Controller","com.pfe.entities","com.pfe.CrossConfig","com.pfe.socket","com.pfe.socket1"})
@EntityScan(basePackages = {"com.pfe.entities"})
@EnableJpaRepositories(basePackages={"com.pfe.repos"})
@EnableConfigurationProperties
@EnableWebSocket
public class ControlAccessApplication extends SpringBootServletInitializer {

	private static final String SWITCH_IP = "192.168.0.1";
	private static final String COMMUNITY = "public";
	public static void main(String[] args) {
		SpringApplication.run(ControlAccessApplication.class, args);
		try {
			InetAddress switchAddress = InetAddress.getByName(SWITCH_IP);
			Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
			snmp.listen();

			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(COMMUNITY));
			target.setAddress(GenericAddress.parse("udp:" + SWITCH_IP + "/161"));
			target.setRetries(2);
			target.setTimeout(1500);
			target.setVersion(SnmpConstants.version2c);

			Map<String, InetAddress> connectedDevices = new HashMap<>();

			while (true) {
				PDU requestPDU = new PDU();
				requestPDU.add(new VariableBinding(new OID("1.3.6.1.2.1.4.22.1.2"))); // OID for ARP table

				ResponseEvent responseEvent = snmp.send(requestPDU, target);

				if (responseEvent.getResponse() != null) {
					PDU responsePDU = responseEvent.getResponse();
					for (VariableBinding vb : responsePDU.getVariableBindings()) {
						if (vb.getVariable() instanceof Null) {
							// End of table
							break;
						}
						String ipAddress = vb.getVariable().toString();
						InetAddress deviceAddress = InetAddress.getByName(ipAddress);
						connectedDevices.put(ipAddress, deviceAddress);
						System.out.println("Device connected: " + deviceAddress);
					}
				}

				// Check for disconnected devices
				connectedDevices.forEach((ipAddress, deviceAddress) -> {
					if (!deviceIsConnected(ipAddress, responseEvent.getResponse())) {
						connectedDevices.remove(ipAddress);
						System.out.println("Device disconnected: " + deviceAddress);
					}
				});

				Thread.sleep(5000);
			}

		} catch (SocketException e) {
			throw new RuntimeException(e);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

	}
	private static boolean deviceIsConnected(String ipAddress, PDU responsePDU) {
		for (VariableBinding vb : responsePDU.getVariableBindings()) {
			String deviceIpAddress = vb.getVariable().toString();
			if (deviceIpAddress.equals(ipAddress)) {
				return true; // The device is still connected
			}
		}

		return false; // The device is not connected
	}
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}