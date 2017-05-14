package openvmf;

import openvmf.com.DB;

public class Settings {
    public static String VEHICLE_NAME;
    public static String VEHICLE_TYPE;
    public static int SERIAL_BAUD_RATE;
    public static String SERVER_CONNECTION_PORT;
    public static String CAMERA_UDP_PORT;
    public static int DATA_BITS;
    public static int STOP_BITS;
    public static int PARITY;
    public static int DISCOVERY_PORT;
    public static int CONTROL_PORT;
    private static String POSITION_DELAY;

    @SuppressWarnings("ConstantConditions")
    static void init() {
        VEHICLE_NAME = DB.getSettings("VehicleName");
        VEHICLE_TYPE = DB.getSettings("VehicleType");
        SERIAL_BAUD_RATE = Integer.parseInt(DB.getSettings("SerialBaudRate"));
        SERVER_CONNECTION_PORT = DB.getSettings("ServerConnectionPort");
        CAMERA_UDP_PORT = DB.getSettings("CameraUDPPort");
        POSITION_DELAY = DB.getSettings("PositionDelay");
        DATA_BITS = Integer.parseInt(DB.getSettings("DataBits"));
        STOP_BITS = Integer.parseInt(DB.getSettings("StopBits"));
        PARITY = Integer.parseInt(DB.getSettings("Parity"));
        DISCOVERY_PORT = Integer.parseInt(DB.getSettings("DiscoveryPort"));
        CONTROL_PORT = Integer.parseInt(DB.getSettings("ControlPort"));
    }
}
