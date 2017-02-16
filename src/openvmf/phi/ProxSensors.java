package openvmf.phi;

import jssc.SerialPort;
import jssc.SerialPortException;
import openvmf.Logger;

public class ProxSensors {

    private SerialPort serialPort;

    public ProxSensors(String port, int bRate) {
        try {
            serialPort.setParams(bRate, 8, 1, 0);
            serialPort = new SerialPort(port);
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public String readSensors() {
        try {
            return serialPort.readString();
        } catch (SerialPortException e) {
            Logger.log("Error while reading sensors from serial");
        }
        return null;
    }
}
