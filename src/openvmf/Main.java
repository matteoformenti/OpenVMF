package openvmf;

import openvmf.com.*;
import openvmf.nav.EngineService;
import openvmf.nav.LidarService;
import openvmf.nav.LocationService;

public class Main {
    private static LidarService lidarService;
    private static EngineService engineService;
    private static LocationService locationService;
    private static ControlServer controlServer;

    public static void main(String args[]) {
        new DB("omega", "Task634Keep");
        Settings.init();
        DiscoveryService discoveryService = new DiscoveryService();
        ServerConnection serverConnection = new ServerConnection();
        new SerialDiscovery();
        controlServer = new ControlServer();
    }

    public static ControlServer getControlServer() {
        return controlServer;
    }

    public static void setLidarService(LidarService lidarService) {
        Main.lidarService = lidarService;
    }

    public static EngineService getEngineService() {
        return engineService;
    }

    public static void setEngineService(EngineService engineService) {
        Main.engineService = engineService;
    }

    public static void setLocationService(LocationService locationService) {
        Main.locationService = locationService;
    }
}
