package net.lliira.game.gol;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class GameTest {

    @Test
    public void SurviveBy2() {
        Game game = new Game(10, 10);
        Plane plane = game.getPlane();
        plane.setState(5,5, true);
        plane.setState(4,4, true);
        plane.setState(6,6, true);
        game.iterate();
        assertTrue(game.getPlane().isAlive(5, 5));
    }

    @Test
    public void SurviveBy3() {
        Game game = new Game(10, 10);
        Plane plane = game.getPlane();
        plane.setState(5,5, true);
        plane.setState(4,5, true);
        plane.setState(4,4, true);
        plane.setState(6,6, true);
        game.iterate();
        assertTrue(game.getPlane().isAlive(5, 5));
    }
    @Test
    public void NewLifeBy3() {
        Game game = new Game(10, 10);
        Plane plane = game.getPlane();
        plane.setState(4,5, true);
        plane.setState(4,4, true);
        plane.setState(6,6, true);
        game.iterate();
        assertTrue(game.getPlane().isAlive(5, 5));
    }

    @Test
    public void DeadBy1() {
        Game game = new Game(10, 10);
        Plane plane = game.getPlane();
        plane.setState(4,5, true);
        plane.setState(5,5, true);
        game.iterate();
        assertFalse(game.getPlane().isAlive(5, 5));
    }

    @Test
    public void DeadBy4() {
        Game game = new Game(10, 10);
        Plane plane = game.getPlane();
        plane.setState(5,5, true);
        plane.setState(4,5, true);
        plane.setState(6,5, true);
        plane.setState(4,6, true);
        plane.setState(6,6, true);
        game.iterate();
        assertFalse(game.getPlane().isAlive(5, 5));
    }

    private void print(Game game) {
        System.out.println("------- #" + game.getIteration() + " --------");
        Plane plane = game.getPlane();
        for (int y = 0; y < plane.getHeight(); y++) {
            for (int x = 0; x < plane.getWidth(); x++) {
                System.out.print(plane.isAlive(x, y) ? "1 " : "0 ");
            }
            System.out.println();
        }
    }
}
