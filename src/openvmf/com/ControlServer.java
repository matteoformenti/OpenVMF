package openvmf.com;

import openvmf.Logger;
import openvmf.Main;
import openvmf.Settings;
import openvmf.nav.EngineService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ControlServer {

    private DatagramSocket socket;
    private boolean stop = false;

    public ControlServer() {
        try {
            socket = new DatagramSocket(Settings.CONTROL_PORT);
            Thread umpCommandListener = new Thread(() -> {
                while (!stop) {
                    try {
                        byte[] buffer = new byte[9];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String in = new String(packet.getData()).replaceAll("\u0000.*", "");
                        int speed = Integer.parseInt(in.split(":")[0]);
                        int steer = Integer.parseInt(in.split(":")[1]);
                        int status = Integer.parseInt(in.split(":")[2]);
                        Main.getEngineService().setSpeed(speed);
                        Main.getEngineService().setSteer(steer);
                        Main.getEngineService().setState((status == 0) ? EngineService.states.FORWARD : (status == 1) ? EngineService.states.BACKWARD : EngineService.states.BRAKE);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Logger.log("Error while receiving UDP Control packet");
                    }
                }
            });
            umpCommandListener.setName("UDP Command Listener");
            umpCommandListener.start();
            Logger.log("UPD Connection Listener started");
        } catch (SocketException e) {
            e.printStackTrace();
            Logger.log("Error while starting UDP Control socket");
        }
    }

    public void kill() {
        stop = true;
    }
}
