package openvmf.com;

import com.sun.net.httpserver.HttpServer;
import openvmf.Logger;
import openvmf.Settings;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class DiscoveryService {
    private HttpServer httpServer;
    private boolean stop = false;
    private DatagramSocket serverSocket;

    public DiscoveryService() {
        UDPDiscovery();
        //HTTPDiscovery();
    }

    public void close() {
        try {
            httpServer.stop(0);
            serverSocket.close();
            stop = true;
        }
        catch (Exception ignored) {
            stop = true;
        }
    }

    private void HTTPDiscovery() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(1234), 0);
            Logger.log("DiscoveryService started successfully");
            httpServer.createContext("/", (e) -> {
                Logger.log("Connection from " + e.getRemoteAddress());
                String composedMessage = composeMessage();
                e.sendResponseHeaders(201, composedMessage.length());
                e.getResponseBody().write(composedMessage.getBytes());
                e.close();
                if (e.getRequestURI().getPath().equals("/stop/"))
                    close();
            });
            httpServer.start();
        } catch (IOException e) {
            Logger.log(e.getMessage());
        }
    }

    private void UDPDiscovery() {
        try {
            serverSocket = new DatagramSocket(Settings.DISCOVERY_PORT);
            byte[] receiveData = new byte[9];
            byte[] sendData = composeMessage().getBytes();
            Thread UDPListenerThread = new Thread(() -> {
                while (!stop) {
                    try {
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        serverSocket.receive(receivePacket);
                        String in = new String(receivePacket.getData());
                        if (in.equals("discovery")) {
                            InetAddress IPAddress = receivePacket.getAddress();
                            int port = receivePacket.getPort();
                            Logger.log("Discovery requested from " + IPAddress + ":" + port);
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                            serverSocket.send(sendPacket);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            UDPListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String composeMessage() {
        String out = "OK \n";
        out += Settings.VEHICLE_NAME + ":" + Settings.VEHICLE_TYPE + "\n";
        out += "HTTP:" + Settings.SERVER_CONNECTION_PORT + "\n";
        out += "CTR:" + Settings.CONTROL_PORT + "\n";
        out += "CAM:" + Settings.CAMERA_UDP_PORT + "\n\r";
        return out;
    }
}