package openvmf;

import openvmf.com.DB;

import java.util.Date;

public class Logger {

    public static void log(String message)
    {
        send(message);
    }

    private static void send(String message)
    {
        DB.logDB(message);
        System.out.println("[" + new Date().toInstant().toString() + "] " + message);
    }
}
