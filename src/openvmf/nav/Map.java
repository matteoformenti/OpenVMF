package openvmf.nav;

public class Map {
    private static int width;
    private static int height;
    private static double mPerUnits;
    private static boolean[][] map;

    public Map(int width, int height, double mPerUnits) {
        Map.height = height;
        Map.width = width;
        Map.mPerUnits = mPerUnits;
        map = new boolean[width][height];
    }

    public static void clearMap() {
        for (int w = 0; w < width; w++)
            for (int h = 0; h < height; h++)
                map[w][h] = false;
    }

    public static boolean readPoint(int w, int h) {
        return map[w][h];
    }


}
