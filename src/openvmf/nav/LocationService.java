package openvmf.nav;

import jssc.SerialPort;
import jssc.SerialPortException;
import openvmf.Logger;
import openvmf.com.DB;

public class LocationService {
    private SerialPort serialPort;
    private boolean stop = false;
    private Integer lastX;
    private int lastY;
    private int lastH;

    public LocationService(SerialPort serialPort) {
        this.serialPort = serialPort;
        try {
            serialPort.addEventListener(serialPortEvent -> {
                if (serialPortEvent.isRXCHAR()) {
                    try {
                        parsePosition(serialPort.readString().replaceAll("^[\\r\\n]+|\\.|[\\r\\n]+$", ""));
                        if (!stop)
                            send((byte) 1);
                    } catch (SerialPortException e) {
                        Logger.log("Error while reading data from location");
                    }
                }
            });
            Logger.log("LocationService started correctly");
        } catch (SerialPortException e) {
            Logger.log("Error while crating LocationService event listener");
        }
        send((byte) 1);
    }

    public void close() {
        stop = true;
    }

    private void send(byte in) {
        try {
            serialPort.writeByte(in);
        } catch (SerialPortException e) {
            Logger.log("Error while sending data to Locator");
        }
    }

    private void parsePosition(String in) {
        int x = Integer.parseInt(in.split(":")[0]);
        int y = Integer.parseInt(in.split(":")[1]);
        int h = Integer.parseInt(in.split(":")[2]);
        if (lastX == null) {
            lastX = x;
            lastY = y;
            lastH = h;
            DB.insertPosition(x, y, h);
        } else if (lastX != x && lastY != y && lastH != h) {
            lastX = x;
            lastY = y;
            lastH = h;
            DB.insertPosition(x, y, h);
        }
    }
}
