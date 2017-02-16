package openvmf;

import java.util.Date;

public class Logger {

    public static void log(String message)
    {
        send("["+new Date().toInstant().toString()+"] "+message);
    }

    private static void send(String message)
    {
        System.out.printf(message);
    }
}
