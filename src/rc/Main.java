package rc;

import com.sun.net.httpserver.HttpServer;
import org.bbi.linuxjoy.JoyFactory;
import org.bbi.linuxjoy.LinuxJoystick;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Main {

    private static boolean send = false;
    private static int port;
    private static InetAddress ip;
    private static DatagramSocket destination;

    private static int speed = 0;
    private static int steer = 0;
    private static int status = 0;

    //Axis0 = horizontal, Axis1 = vertical
    public static void main(String args[]) {
        try {
            destination = new DatagramSocket();
            destination.setTrafficClass(0x10);
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8081), 0);
            httpServer.createContext("/", e -> {
                String in = e.getRequestURI().toString().replace("/", "");
                switch (in.charAt(0)) {
                    case 'd':
                        in = in.substring(1);
                        ip = InetAddress.getByName(in.split(":")[0]);
                        port = Integer.parseInt(in.split(":")[1]);
                        System.out.println("Ip: " + ip + "\tPort: " + port);
                        e.sendResponseHeaders(200, in.length());
                        e.getResponseBody().write(in.getBytes());
                        break;
                    case 's':
                        if (ip != null && port != 0)
                            send = true;
                        break;
                    case 'k':
                        send = false;
                }
            });
            httpServer.start();
            LinuxJoystick j = JoyFactory.getFirstUsableDevice();
            if (j != null) {
                j.setCallback((linuxJoystick, linuxJoystickEvent) -> {
                    speed = map(linuxJoystick.getAxisState(1), 0, 255, true);
                    steer = map(linuxJoystick.getAxisState(0), -20, 20, false);
                    status = (linuxJoystick.getButtonState(0)) ? 2 : status;
                    System.out.println("Speed: " + speed + "\tSteer: " + steer + "\tStatus: " + ((status == 0) ? "FORWARD" : (status == 1) ? "BACKWARD" : "BRAKE"));
                    send();
                });
                j.startPollingThread(5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int map(int in, int min, int max, boolean positive) {
        int delta = 32767;
        if (positive) {
            status = (in > 0) ? 1 : 0;
            return ((Math.abs(in) * 511 / (delta * 2)));
        } else {
            return (in * 40 / (delta * 2));
        }
    }

    private static void send() {
        if (!send)
            return;
        try {
            byte[] buffer = (speed + ":" + steer + ":" + status).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, port);
            destination.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}