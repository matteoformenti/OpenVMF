package openvmf.com;

import com.sun.net.httpserver.HttpServer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import openvmf.Logger;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;

public class ServerConnection {

    private HttpServer httpServer;

    public ServerConnection() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(1235), 0);
            Logger.log("ServerConnection started successfully");
            httpServer.createContext("/", (e) -> {
                switch (e.getRequestURI().toString().replace("/", "")) {
                    case "getLIDAR":
                        try {
                            Logger.log("Building LIDAR png");
                            File file = new File("lidar.png");
                            WritableImage out = new WritableImage(800, 800);
                            PixelWriter pixelWriter = out.getPixelWriter();
                            for (int w = 0; w < out.getWidth(); w++)
                                for (int h = 0; h < out.getHeight(); h++)
                                    pixelWriter.setColor(w, h, Color.BLACK);
                            int center = (int) (out.getWidth() / 2);
                            ResultSet result = DB.getConnection().createStatement().executeQuery("SELECT * FROM Points WHERE Position = (SELECT MAX(Position) FROM Points) ORDER BY Id DESC LIMIT 20");
                            while (result.next()) {
                                int rotation = result.getInt(3);
                                for (int l = 0; l < 4; l++) {
                                    int distance = result.getInt(l + 4);
                                    for (double i = 0; i < 15; i += 0.2) {
                                        int nextY = (int) (Math.sin(Math.toRadians(rotation + (90 * l) + i)) * distance);
                                        int nextX = (int) (Math.cos(Math.toRadians(rotation + (90 * l) + i)) * distance);
                                        pixelWriter.setColor(center - nextX, center - nextY, Color.GREEN);
                                    }
                                }
                            }
                            pixelWriter.setColor(center, center, Color.RED);
                            result.close();
                            RenderedImage renderedImage = SwingFXUtils.fromFXImage(out, null);
                            ImageIO.write(renderedImage, "png", file);
                            e.sendResponseHeaders(201, file.length());
                            Files.copy(file.toPath(), e.getResponseBody());
                            break;
                        } catch (Exception ignored) {
                            Logger.log("Error while creating LIDAR image");
                            break;
                        }
                    case "getMap":
                        try {
                            Logger.log("Building Map png");
                            Connection db = DB.getConnection();
                            ResultSet res = db.createStatement().executeQuery("SELECT MAX(PosX)+800 FROM Positions");
                            res.next();
                            int width = res.getInt(1);
                            res = db.createStatement().executeQuery("SELECT MAX(PosY)+800 FROM Positions");
                            res.next();
                            int height = res.getInt(1);
                            File file = new File("map.png");
                            WritableImage out = new WritableImage(width, height);
                            PixelWriter pixelWriter = out.getPixelWriter();
                            for (int w = 0; w < out.getWidth(); w++)
                                for (int h = 0; h < out.getHeight(); h++)
                                    pixelWriter.setColor(w, h, Color.BLACK);
                            res.close();
                            ResultSet positions = db.createStatement().executeQuery("SELECT * FROM Positions ORDER BY Time ASC");
                            while (positions.next()) {
                                int heading = positions.getInt(5);
                                int X = positions.getInt(3) + 400;
                                int Y = positions.getInt(4) + 400;
                                ResultSet points = DB.getConnection().createStatement().executeQuery("SELECT * FROM Points WHERE Position =  '" + positions.getInt(1) + "';");
                                while (points.next()) {
                                    int rotation = points.getInt(3);
                                    for (int l = 0; l < 4; l++) {
                                        int distance = points.getInt(l + 4);
                                        for (double i = 0; i < 15; i += 0.2) {
                                            int nextY = (int) (Math.sin(Math.toRadians(rotation + (90 * l) + i + heading)) * distance);
                                            int nextX = (int) (Math.cos(Math.toRadians(rotation + (90 * l) + i + heading)) * distance);
                                            pixelWriter.setColor(X - nextX, Y - nextY, Color.GREEN);
                                        }
                                    }
                                }
                                points.close();
                            }
                            positions.close();
                            RenderedImage renderedImage = SwingFXUtils.fromFXImage(out, null);
                            ImageIO.write(renderedImage, "png", file);
                            e.sendResponseHeaders(201, file.length());
                            Files.copy(file.toPath(), e.getResponseBody());
                            break;
                        } catch (Exception e1) {
                            Logger.log("Error while creating map image");
                            e1.printStackTrace();
                            break;
                        }
                }
            });
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        httpServer.stop(0);
    }
}