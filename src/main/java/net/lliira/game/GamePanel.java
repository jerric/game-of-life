package net.lliira.game;

import net.lliira.game.gol.Game;
import net.lliira.game.gol.GameOption;
import net.lliira.game.gol.Plane;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final int UNIT = 3;
    private final Game game;

    public GamePanel(int width, int height) {
        this.game = new Game(width, height);
        setPreferredSize(new Dimension(width * UNIT, height * UNIT));
    }

    public void start() {
        game.registerIterationListener(unused -> this.repaint());
        game.start();
    }

    public void resetGame(GameOption option) {
        game.resetGame(option);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBackground(g2d);
        game.getLock().readLock().lock();
        drawPlane(g2d, game.getPlane());
        drawIteration(g2d, game.getIteration());
        game.getLock().readLock().unlock();
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    private  void drawIteration(Graphics2D g, long iteration) {
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.RED);
        g.drawString(Long.toString(game.getIteration()), 10 , 30);
    }

    private void drawPlane(Graphics2D g, Plane plane) {
        g.setColor(Color.WHITE);
        for (int y = 0; y < plane.getHeight(); y++) {
            for (int x = 0; x < plane.getWidth(); x++) {
                if (plane.isAlive(x, y)) {
                    g.fillRect(x * UNIT, y * UNIT, UNIT, UNIT);
                }
            }
        }
    }
}
