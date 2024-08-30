package net.lliira.game.gol;

public class GameOption {
    private float density = 0.5F;
    private int border = 100;
    private long interval = 50L;
    public float density() {
        return density;
    }
    public GameOption density(float density) {
        if (density < 0 || density > 1)
            throw new IllegalArgumentException("density must be in range [0,1]");
        this.density = density;
        return this;
    }
    public int border() {
        return border;
    }
    public GameOption border(int border) {
        if (border < 0)
            throw new IllegalArgumentException("border must be non-negative");
        this.border = border;
        return this;
    }
    public long interval() {
        return interval;
    }
    public GameOption interval(long interval) {
        if (interval <= 0)
            throw new IllegalArgumentException("Interval must be positive, in millie-seconds");
        this.interval = interval;
        return this;
    }
}
