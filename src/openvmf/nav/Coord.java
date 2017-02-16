package openvmf.nav;

public class Coord {
    private double lat;
    private double lon;
    private int x;
    private int y;

    public Coord(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
