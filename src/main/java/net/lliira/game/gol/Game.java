package net.lliira.game.gol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class Game {
  private final ReadWriteLock readWriteLock;
  private final List<Consumer<Long>> iterationListeners;
  private Plane nextPlane;
  private Plane plane;
  private long iteration;
  private boolean running;
  private boolean newGame;
  private GameOption gameOption;

  public Game(int width, int height) {
    this.nextPlane = new Plane(width, height);
    this.plane = new Plane(width, height);
    this.readWriteLock = new ReentrantReadWriteLock();
    this.gameOption = new GameOption();
    this.iterationListeners = new LinkedList<>();
  }

  public void registerIterationListener(Consumer<Long> listener) {
    iterationListeners.add(listener);
  }

  public void unregisterIterationListener(Consumer<Long> listener) {
    iterationListeners.remove(listener);
  }

  public void resetGame(GameOption option) {
    this.gameOption = option;
    newGame = true;
    System.out.println("Game reset");
  }

  public void start() {
    running = true;
    newGame = true;
    while (running) {
      readWriteLock.writeLock().lock();
      if (newGame) {
        this.iteration = 0;
        populateLife();
        newGame = false;
      }
      iterate();
      for (var listener : iterationListeners) {
        listener.accept(iteration);
      }
      readWriteLock.writeLock().unlock();
      try {
        Thread.sleep(gameOption.interval());
      } catch (InterruptedException ex) {
      }
    }
  }

  public void stop() {
    running = false;
  }

  private void populateLife() {
    for (int x = 0; x < plane.getWidth(); x++) {
      for (int y = 0; y < plane.getHeight(); y++) {
        plane.setState(x, y, false);
      }
    }

    Random random = new Random();
    int border = gameOption.border();
    for (int x = border; x < plane.getWidth() - border; x++) {
      for (int y = border; y < plane.getHeight() - border; y++) {
        if(random.nextFloat() < gameOption.density()){
          plane.setState(x, y, true);
        }
      }
    }
  }

  long iterate() {
    for (int x = 0; x < plane.getWidth(); x++) {
      for (int y = 0; y < plane.getHeight(); y++) {
        int neighbors = getNeighbors(plane, x, y);
        boolean state = neighbors == 3 || (neighbors == 2 && plane.isAlive(x, y));
        nextPlane.setState(x, y, state);
      }
    }
    Plane temp = plane;
    plane = nextPlane;
    nextPlane = temp;
    iteration++;
    return iteration;
  }

  private int getNeighbors(Plane plane, int x, int y) {
    int count = 0;
    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        if (dx == 0 && dy == 0) continue;
        if (plane.isAlive(x + dx, y + dy)) count++;
      }
    }
    return count;
  }

  public long getIteration() {
    return iteration;
  }

  public Plane getPlane() {
    return plane;
  }

  public ReadWriteLock getLock() {
    return readWriteLock;
  }
}
