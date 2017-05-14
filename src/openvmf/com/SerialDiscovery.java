package openvmf.com;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import openvmf.Logger;
import openvmf.Main;
import openvmf.Settings;
import openvmf.nav.EngineService;
import openvmf.nav.LidarService;
import openvmf.nav.LocationService;

import java.util.Arrays;

public class SerialDiscovery {

    public SerialDiscovery() {
        Logger.log("Serial discovery service: " + SerialPortList.getPortNames().length + " serial devices found");
        Arrays.stream(SerialPortList.getPortNames()).forEachOrdered(portName -> {
            try {
                SerialPort tempPort = new SerialPort(portName);
                tempPort.openPort();
                tempPort.setParams(Settings.SERIAL_BAUD_RATE, Settings.DATA_BITS, Settings.STOP_BITS, Settings.PARITY);
                tempPort.addEventListener(serialPortEvent -> {
                    try {
                        if (serialPortEvent.isRXCHAR()) {
                            String identifier = tempPort.readString();
                            tempPort.removeEventListener();
                            switch (identifier.replaceAll("\\W", "")) {
                                case "engine":
                                    Main.setEngineService(new EngineService(tempPort));
                                    Logger.log("Engine controller found on " + portName);
                                    break;
                                case "lidar":
                                    Main.setLidarService(new LidarService(tempPort));
                                    Logger.log("Lidar controller found on " + portName);
                                    break;
                                case "location":
                                    Main.setLocationService(new LocationService(tempPort));
                                    Logger.log("Location controller found on " + portName);
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        Logger.log("Error while mapping serial devices");
                        e.printStackTrace();
                    }
                });
            } catch (SerialPortException e) {
                Logger.log("Error while trying to processing port " + portName);
            }
        });
    }
}
