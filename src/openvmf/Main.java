package openvmf;

import openvmf.com.*;
import openvmf.nav.EngineService;
import openvmf.nav.SonarService;
import openvmf.nav.LocationService;

public class Main {
    private static SonarService sonarService;
    private static EngineService engineService;
    private static LocationService locationService;
    private static ControlServer controlServer;

    public static void main(String args[]) {
        new DB("root", "Task634Keep");
        Settings.init();
        DiscoveryService discoveryService = new DiscoveryService();
        ServerConnection serverConnection = new ServerConnection();
        new SerialDiscovery();
        controlServer = new ControlServer();
    }

    public static void setSonarService(SonarService sonarService) {
        Main.sonarService = sonarService;
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
