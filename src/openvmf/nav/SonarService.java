package openvmf.nav;

import jssc.SerialPort;
import jssc.SerialPortException;
import openvmf.Logger;
import openvmf.com.DB;

public class SonarService {
    private SerialPort serialPort;
    private boolean stop = false;

    private int currentPosition;

    public SonarService(SerialPort serialPort) {
        this.serialPort = serialPort;
        try {
            serialPort.addEventListener(serialPortEvent -> {
                if (serialPortEvent.isRXCHAR()) {
                    try {
                        pareInput(serialPort.readString().replaceAll("^[\\r\\n]+|\\.|[\\r\\n]+$", ""));
                        if (!stop)
                            send((byte) 1);
                    } catch (SerialPortException e) {
                        Logger.log("Error while reading data from Sonar");
                    }
                }
            });
            Logger.log("SonarService started correctly");
        } catch (SerialPortException e) {
            Logger.log("Error while crating SonarService event listener");
        }
        send((byte) 1);
    }

    public void close() {
        try {
            stop = true;
            serialPort.closePort();
        } catch (SerialPortException e) {
            Logger.log("Error while closing SonarService");
        }
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    private void send(byte in) {
        try {
            serialPort.writeByte(in);
        } catch (SerialPortException e) {
            Logger.log("Error while sending data to Sonar");
        }
    }

    private void pareInput (String in) {
        try {
            String fields[] = in.split(":");
            int rotation = Integer.parseInt(fields[0]) * 15;
            int sensor1 = Integer.parseInt(fields[1]);
            int sensor2 = Integer.parseInt(fields[2]);
            int sensor3 = Integer.parseInt(fields[3]);
            int sensor4 = Integer.parseInt(fields[4]);
            DB.insertPoints(sensor1, sensor2, sensor3, sensor4, rotation);
        } catch (Exception ignored) {
        }
    }
}
