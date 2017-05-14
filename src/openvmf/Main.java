package openvmf;

import openvmf.com.*;
import openvmf.nav.EngineService;
import openvmf.nav.LidarService;
import openvmf.nav.LocationService;

public class Main {
    private static LidarService lidarService;
    private static EngineService engineService;
    private static LocationService locationService;
    private static DiscoveryService discoveryService;
    private static ServerConnection serverConnection;

    public static void main(String args[]) {
        DB databaseConnection = new DB("ciao", "ciao");
        Settings.init();
        discoveryService = new DiscoveryService();
        serverConnection = new ServerConnection();
        new SerialDiscovery();
        ControlServer controlServer = new ControlServer();
    }

    public static LidarService getLidarService() {
        return lidarService;
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

    public static LocationService getLocationService() {
        return locationService;
    }

    public static void setLocationService(LocationService locationService) {
        Main.locationService = locationService;
    }

    public static void stop() {
        serverConnection.close();
        discoveryService.close();
        engineService.close();
        locationService.close();
        lidarService.close();
    }
}
