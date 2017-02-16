package openvmf;

import openvmf.com.DB;
import openvmf.com.ServerConnection;
import openvmf.nav.Map;
import openvmf.phi.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Vehicle vehicle;
    private static ServerConnection server;
    private static DB db;

    private static List<Thread> threadList = new ArrayList<>();

    public static void main(String args[])
    {
//        db = new DB("localhost", "omega", "itmakerslogin");
//        server = new ServerConnection();
//        vehicle = new Vehicle();
//        db.init();
    }

    public static Vehicle getVehicle() {
        return vehicle;
    }

    public static ServerConnection getServer() {
        return server;
    }

    public static DB getDb() {
        return db;
    }

    public static void registerThread(Thread t)
    {
        threadList.add(t);
    }

    public static void killThreads()
    {
        threadList.forEach(Thread::stop);
    }
}
