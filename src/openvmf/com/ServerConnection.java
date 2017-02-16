package openvmf.com;

import openvmf.Logger;
import openvmf.Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection {

    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket socket;
    private int serverPoprt;

    public ServerConnection(){
        socket = new Socket();
        Thread serverListener = new Thread(() -> {
            try {
                new CommandParser(reader.readLine()).parse();
            } catch (IOException e) {
                Logger.log("Error while reading data from server\n");
            }
        });
        Main.registerThread(serverListener);
        serverListener.start();
    }
}
