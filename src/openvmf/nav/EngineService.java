package openvmf.nav;

import jssc.SerialPort;
import jssc.SerialPortException;
import openvmf.Logger;
import openvmf.Settings;

public class EngineService {
    private SerialPort serialPort;
    private boolean stop = false;
    private String speed;
    private String steer;
    private states state = states.BRAKE;

    private boolean manualControl = true;
    private long lastCommandTime;

    public EngineService(SerialPort serialPort) {
        this.serialPort = serialPort;
        Logger.log("EngineService ready");
        Thread safetyThread = new Thread(() -> {
            while (!stop) {
                if ((System.nanoTime() / 1000000) - lastCommandTime >= Settings.SAFETY_CONTROL_TIMEOUT && manualControl) {
                    state = states.BRAKE;
                    setSpeed(0);
                }
            }
        });
        safetyThread.start();
    }


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

    public void kill() {
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
            serialPort.writeString(data);
            lastCommandTime = (System.nanoTime() / 1000000);
        } catch (SerialPortException e) {
            Logger.log("Error while sending engine data '" + data + "';");
        }
    }

    public enum states {FORWARD, BACKWARD, BRAKE}
}
