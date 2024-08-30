package net.lliira.game.gol;

/**
 * A plane captures the state of one iteration
 */
public class Plane {
    private final boolean[][] area;
    private final int height;
    private final int width;

    public Plane(int width, int height) {
        this.height = height;
        this.width = width;
        this.area = new boolean[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive(int x, int y) {
        x = normalize(x, width);
        y = normalize(y, height);
        return area[x][y];
    }

    public void setState(int x, int y, boolean alive) {
        x = normalize(x, width);
        y = normalize(y, height);
        area[x][y] = alive;
    }

    private int normalize(int x, int size) {
        x %= size;
        if (x < 0) x += size;
        return x;
    }
}
