package openvmf.nav;

import jssc.SerialPort;
import jssc.SerialPortException;
import openvmf.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class EngineService {
    private SerialPort serialPort;
    private boolean stop = false;
    private String speed;
    private String steer;
    private states state = states.BRAKE;

    public EngineService(SerialPort serialPort) {
        this.serialPort = serialPort;
        Logger.log("EngineService ready");
        try {
            serialPort.addEventListener(serialPortEvent -> {
                if (serialPortEvent.isRXCHAR()) {
                    try {
                        System.out.println("Received:\t" + serialPort.readString());
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private static String fillZero(int in) {
        char out[] = new char[3];
        char inArray[] = (in + "").toCharArray();
        switch (inArray.length) {
            case 0:
                out[0] = '0';
                out[1] = '0';
                out[2] = '0';
                break;
            case 1:
                out[0] = '0';
                out[1] = '0';
                out[2] = inArray[0];
                break;
            case 2:
                out[0] = '0';
                out[1] = inArray[0];
                out[2] = inArray[1];
                break;
            case 3:
                out[0] = inArray[0];
                out[1] = inArray[1];
                out[2] = inArray[2];
                break;
        }
        return new String(out);
    }

    @Contract(pure = true)
    private static String getStateValue(states s) {
        switch (s) {
            case FORWARD:
                return "0";
            case BACKWARD:
                return "1";
            case BRAKE:
                return "2";
        }
        return "0";
    }

    public void close() {
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            Logger.log("Error while closing EngineService communication");
        }
    }

    public int getSpeed() {
        return Integer.parseInt(speed);
    }

    public void setSpeed(int speed) {
        this.speed = fillZero(speed);
        applyValues();
    }

    public states getState() {
        return state;
    }

    public void setState(states state) {
        this.state = state;
        applyValues();
    }

    public int getSteer() {
        return Integer.parseInt(steer);
    }

    public void setSteer(int steer) {
        int steerOffset = 150;
        this.steer = fillZero(steer + steerOffset);
        System.out.println("Steer value:\t" + steer);
        applyValues();
    }

    private void applyValues() {
        String data = speed + steer + getStateValue(state);
        try {
            System.out.println("Data out:\t" + data);
            serialPort.writeString(data);
        } catch (SerialPortException e) {
            Logger.log("Error while sending engine data '" + data + "';");
        }
    }

    public enum states {FORWARD, BACKWARD, BRAKE}
}
